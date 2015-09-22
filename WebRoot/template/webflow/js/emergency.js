$(function(){
	initWin();
	$.ajax({
		type:'post',
		url:'../json/emergency.json',
		dataType:'json',
		success:function(msg){
			drawNode(msg.node);
			drawLine(msg.line);
			initEvent();
			if(r_obj_id != '' && r_x != '' && r_y != '' && r_z != '' ){
				$('.flow > div:eq(0)').addClass('active');
				labelPoint={'obj_id':r_obj_id,'x':r_x,'y':r_y,'z':r_z};
			}
		}
	});
});

function drawNode(data) {
	if(!data) return;
	for(var i=0;i<data.length;i++){
		var n=data[i];
		var div='<div class="node" id="node_'+n.id+'">'+n.text+'</div>';
		var $div=$(div);
		if(n.x){
			$div.css('left', n.x);
		}
		if(n.y){
			$div.css('top', n.y);
		}
		if(n.width){
			$div.css('width', n.width);
		}
		if(n.height){
			$div.css('height', n.height);
		}
		if(n.action){
			$div.data('action', n.action);
		}
		$div.appendTo('.flow');
	}	
}

function drawLine(data) {
	if(!data) return;
	for(var i=0;i<data.length;i++){
		var l=data[i];
		var div='<div class="line" from="'+l.from+'" to="'+l.to+'"><div class="end"></div><div class="arrow"></div></div>';
		var $div=$(div);
		var from=$('#node_'+l.from);
		var to=$('#node_'+l.to);
		var fp=from.position();
		var fw=from.width();
		var fh=from.height();
		var tp=to.position();
		var tw=to.width();
		var th=to.height();

		if(tp.left-fp.left-fw<tp.top-fp.top-fh){//从上往下
			var x=fp.left+fw/2;
			var y=fp.top+fh;
			var w=tp.top-fp.top-fh-15;

			$div.css({
				'left':x-w/2,
				'top':y+w/2+2,
				'width':w-3,
				'-webkit-transform':'rotate(90deg)',
				'-ms-transform':'rotate(90deg)'
			});
		}
		else if(tp.left-fp.left-fw>tp.top-fp.top-fh){//从左往右
			var x=fp.left+fw;
			var y=fp.top+fh/2;
			var w=tp.left-fp.left-fw;

			$div.css({
				'left':x+9,
				'top':y-10,
				'width':w-20
			});
		}

		$div.appendTo('.flow');
	}	
}

//注册节点点击事件
function initEvent () {
	document.onselectstart=function(){return false;};

	$('.node').dblclick(function(e) {
		if(!isturn(this)) return false;
		active(this, function(node){
			nodeAction(node, e);
		});
	});

	$('.node').bind('contextmenu', function(e) {
		e.preventDefault();
		if(!isturn(this)) return false;
		active(this, function(node){
			showContextMenu(node, e);
		});
	});

	$('.reset').click(function () {
		$('.menu').css('display', 'none');
		$('.active').removeClass('active');
	});

	$('.page-lite').click(function(){
		showWin(1);
	});

	$('.content').draggable({
		'handle' : '.title',
		'containment' : 'body'
	});

	$('.close').click(function(){
		NJCallC([
			{"service_id": "effect.destroy","effect_id": "1000000001"},
			{"service_id":233004},
			{service_id:1,title:document.title,position:'close'}
		]);
	});

	$('.minimized').click(function(){
		showWin(0);
	});
}

function nodeAction (node, e) {
	var action=$(node).data('action');
	if(!action || action.length==0) return;
	if(action.length==1){
		//执行单个service
		var service=action[0].service;
		var service_str=JSON.stringify(service);
		if(service_str.indexOf('$ref-title$')!=-1){
			service_str=service_str.replace('$ref-title$', document.title);
			service=eval('('+service_str+')');
		}
		if(service_str.indexOf('$ref-x$')!=-1
			||service_str.indexOf('$ref-y$')!=-1
			||service_str.indexOf('$ref-z$')!=-1
			||service_str.indexOf('$ref-obj_id$')!=-1){
			if(labelPoint){
				service_str=service_str.replace('$ref-x$', labelPoint.x);
				service_str=service_str.replace('$ref-y$', labelPoint.y);
				service_str=service_str.replace('$ref-z$', labelPoint.z);
				service_str=service_str.replace('$ref-obj_id$', labelPoint.obj_id);
				service=eval('('+service_str+')');

				NJCallC(service);
				//显示浮标
				showWin(0);
			}
			return;
		}
		if(service){
			NJCallC(service);
			//显示浮标
			showWin(0);
		}
	}
	else{
		//显示下拉菜单
		showContextMenu(node, e);
	}
}


function showContextMenu (node, e) {
	var action=$(node).data('action');
	if(!action || action.length==0) return;
	$('.menu ul').html('');
	for(var i=0;i<action.length;i++){
		$('<li>'+action[i].name+'</li>').data('service', action[i].service).appendTo('.menu ul');
	}

	// var x=$(node).position().left+$(node).width()+5;
	// var y=$(node).position().top+50;
	var x=e.pageX;
	var y=e.pageY;
	y=y+$('.menu').height()>$('body').height()?$('body').height()-$('.menu').height()-10:y;
	x=$('body').width()/2>x?x:x-$('.menu').width();

	$('.menu').css({
		'left':x,
		'top':y,
		'display':'block'
	});

	$('.menu ul li').bind('click', function(event) {
		var service=$(this).data('service');
		if(service){
			var service_str=JSON.stringify(service);
			if(service_str.indexOf('$ref-title$')!=-1){
				service_str=service_str.replace('$ref-title$', document.title);
				service=eval('('+service_str+')');
			}
			if(service_str.indexOf('$ref-x$')!=-1
				||service_str.indexOf('$ref-y$')!=-1
				||service_str.indexOf('$ref-z$')!=-1
				||service_str.indexOf('$ref-obj_id$')!=-1){
				// alert(service_str);
				if(labelPoint){
					service_str=service_str.replace('$ref-x$', labelPoint.x);
					service_str=service_str.replace('$ref-y$', labelPoint.y);
					service_str=service_str.replace('$ref-z$', labelPoint.z);
					service_str=service_str.replace('$ref-obj_id$', labelPoint.obj_id);
					service=eval('('+service_str+')');

					NJCallC(service);
					//显示浮标
					showWin(0);
				}
			}
			else{
				NJCallC(service);
				//显示浮标
				showWin(0);
			}
			
		}

		$('.menu').css('display', 'none');
		$('body').unbind('click.menu');
		return false;
	});

	$('body').bind('click.menu', function () {
		$('.menu').css('display', 'none');
		$('body').unbind('click.menu');
	})

}

//是否能够激活
function isturn (node) {
	var id=node.id;
	id=id.substring(5)//node_;
	var line=$('.line[to='+id+']');
	if(line.length>0){
		var from=$('#node_'+line.attr('from'));
		if(from.hasClass('active')){
			return true;
		}
		else{
			return false;
		}
	}

	return true;

}

function active(node, callback) {
	var id=node.id;
	id=id.substring(5)//node_;
	var line=$('.line[to='+id+']');
	if(line.length>0){
		var from=$('#node_'+line.attr('from'));
		if(from.hasClass('active')){
			line.addClass('active');
		}
	}

	setTimeout(function() {
		$(node).addClass('active');
	}, 50);
	setTimeout(function() {
		$(node).removeClass('active');
	}, 150);
	setTimeout(function() {
		$(node).addClass('active');
	}, 250);
	setTimeout(function() {
		$(node).removeClass('active');
	}, 350);
	setTimeout(function() {
		$(node).addClass('active');
	}, 450);
	setTimeout(function() {
		$(node).removeClass('active');
	}, 500);
	setTimeout(function() {
		$(node).addClass('active');
		if(callback){
			callback(node);
		}
	}, 550);


}


function showWin(tag){
	if(tag){//显示窗口
		$('.background, .content').show();
		$('.page-lite').hide();
	}
	else{//隐藏窗口
		$('.background, .content').hide();
		$('.page-lite').show();
	}
}

function initWin(){
	var w=$('.content').width();
	var bw=$('body').width();
	var h=$('.content').height();
	var bh=$('body').height();
	$('.content').css({
		'left' : (bw-w)/2,
		'top' : (bh-h)/2
	});
}

var labelPoint;
//标记事故点
function labelAddr(point){
	labelPoint=point;
	var service={
	    "service_id": "effect.create",
	    "effect_id": "1000000001",
	    "name": "radar",
	    "config": {
	        "color": "0xff0080ff",
	        "pos": point.x+","+point.y+","+(parseFloat(point.z)+6),
	        "outer_radius": 50
	    }
	};
	NJCallC(service);
}


function onCloseWindow(){
	if(labelPoint){
		NJCallC({
            "service_id": "effect.destroy",
            "effect_id": "1000000001"
        }, false);
	}
}