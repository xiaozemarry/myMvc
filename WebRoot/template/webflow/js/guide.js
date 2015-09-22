var vc ;
var originalWidth = 747;
var originalHeight = 713; //用于操作自适应的问题

var originaWinWidth = 690;
var originaWinHeight = 750;
$(function(){
	$("body").data("prevW",originalWidth);//初始化大小
	$("body").data("prevH",originalHeight);
    $("#backImg").css({width:originalWidth+"px",height:originalHeight+"px"});
	initJSONData();
	vc = new VRClass();
	$(window).resize(function(){
		window.resizeTo(660,660);
	 })
})
function initJSONData(){
	$.ajax({
	type:'post',
	cache:true,
	url:'../json/guide.json',
	success:function(msg){
		try
		{
		   //var json = JSON.parse(msg);
		   drawLayer(msg);
			//$("#backImg").css({width:"100%",height:"100%"});
			//return;
			//autoFit($("body"));
		}
		catch (e)
		{
			alert("wrong..."+e);
		}
	}
  });
 }
 function scrollContainer(){
  // resizeTo(_w,750);

 }
function autoFit(container){
  	var newWidth = container.width();//当前存放图片容器的大小
	var newHeight = container.height();

	$("#backImg").css({width:newWidth+"px",height:newHeight+"px"});

    var oldWidth = $("body").data("prevW");//获取缓存的上一次图片的大小
    var oldHeight = $("body").data("prevH");

	var differWidth = newWidth-oldWidth;//增加或则减少的宽度
	var differHeight = newHeight-oldHeight;//增加或则减少的高度
    
	var baseWidth = differWidth/newWidth;
	var baseHeight = differHeight/newHeight;

	console.log(baseWidth+"______"+baseHeight);

	setNewWHForDiv(baseWidth,baseHeight,oldWidth,oldHeight);
	$("body").data("prevW",newWidth);
	$("body").data("prevH",newHeight);
}
//给div设置最新的高宽
function setNewWHForDiv(baseWidth,baseHeight,oldWidth,oldHeight){
	var all = $("div[layer='enable']");
	var k =0 ;
	for(;k<all.length;k++)
	{
		var each = $(all[k]);
		var offset = each.offset();

		var left = offset.left;
		var realLeft = Math.ceil(left+left*baseWidth);
		each.css("left",realLeft+"px")

		var top = offset.top;
		var realTop = Math.ceil(top+top*baseHeight);
		each.css("top",realTop+"px");

		continue;
		var width = each.width();
		var realWidth = Math.ceil(width+width*baseWidth);
		each.css("width",realWidth+"px");
	    return;
		var width = each.width();
		var realWidth = Math.ceil(width+width*baseWidth);
		each.css("width",realWidth+"px");
	}
}
function drawLayer(data){
    if(!data)return;
	var bWidth = $("body").width();
     var k =0 ;
	 for(;k<data.length;k++)
	 {
	    var each = data[k];
		var text = each.text;
		var div = "<div layer='enable' class='divCommon'>"+text+"</div>";
		$("body").append(div);
		var lastLayer = $("div[layer='enable']:last");
		lastLayer.css(each.css);
 		lastLayer.css("left",760*(each.css.left.replace("%","")/100));
		lastLayer.data("cacheData",each);
		lastLayer.bind("mousedown dblclick",function(e){
		   var temp = e.which==3 || e.type=="mousedown";//|| e.type=="click";
		   if(temp)_event(this,e.which);
		})
	 }
}

function _event(e,eWhich){
	  console.log("Go!!!");
	   $(".smart_menu_box").show();
	   var cacheData =  $(e).data("cacheData");
	   var ment = cacheData.contextMenu;
	   if(!ment)
	   {
		 $(".smart_menu_box").hide();
	     e.oncontextmenu = function(){return false} ; return;
	   }
	   $(".smart_menu_box").remove();//必须要移除 否则有问题!!!!!!
	   var bodyHeight = $("body").height();//获取鼠标的当前位置 如果在底部的话 需要改动右键菜单的位置 
	   var text = $(e).text();
	   var offsetY = 2;
	   var offsetX = 2 ;
	   if(text=="应急状态解除")offsetY=-50;
	   if(text=="现场指挥" || text=="工程抢险")offsetX=-160;
	   else if(text=="专家支持")offsetX=-170;
	   var right = contextMenu(ment);
	   $(e).smartMenu([right],{offsetY:offsetY,offsetX:offsetX});
	   if(eWhich==1)$(e).click();  //防止多次激发
}
//右键菜单属性
function contextMenu(data){
     if(!data)return;
	 var array = new Array();
     var k =0 ;
	 for(;k<data.length;k++)
	 {
		 var text = data[k].text;
		 var object = {};
		 object.text = text;
		 object.func = function(){
				var currentClick =  $(this).data("cacheData");//获取到当前点击的元素的数据
				var checkNode = $(".smart_menu_ul").find("li").find("a");
				var index =  $(currentClickElement).attr("index");//根据这个下标从currentClick找到对应的数据
				var actionTarget = currentClick.contextMenu[index].flag;
				var action = findObjectByFlag(actionTarget);
				if(action==null)return;
				if(action["url"])openIeBy3D(action["url"]);
				else if(action["service"]){vc.CallC(action["service"]);}
				else alert("Wrong !");
//				this.PlayScript=function(ScriptID,ScriptType,CanBeChange,Show){
				vc.simplePlayScript(399514,1);
		 }
		 array.push(object);
	 }
	 return array;
}
var currentClickElement = null;
function checkMe(e){
  currentClickElement = e;
}

function findObjectByFlag(name,rValFlag){
	if(!name)return null;
	var k =0 ;
	for(;k<urlOrServicesObjectArray.length;k++)
	{
        if(name==urlOrServicesObjectArray[k].name)return urlOrServicesObjectArray[k]
	}
	return null;
}

function openIeBy3D(url){
  if(!vc)alert("Vrinterface Not Init!");
  if(!vc || !url || url==null)return;
  vc.openIE(url);
}

var urlOrServicesObjectArray = 
[
  {mark:"短信平台",name:"messagePlatform",url:"[WebServer]/Redirect.jsp?r_url=R_Views/innerMessage/page/sendMessage_moxi.jsp"},
  {mark:"SCADA页面",name:"SCADA",url:"[WebServer]/Redirect.jsp?r_url=R_Views/mx_RTImg/page/main4_scada.jsp&r_width=1880&r_height=1000&r_name=监测控制和数据采集&r_pos= &r_type=0&r_resizable=yes"},
  {mark:"工业视频 ",name:"industryVideo",url:"[WebServer]/Redirect.jsp?r_url=R_Views/mx_video/page/main_tree.jsp&r_width=1885&r_height=975&r_name=工业视频监控&r_pos=bottom&r_type=0&r_resizable=yes"},
  {mark:"应急物资",name:"YJWZ",url:"[WebServer]/Redirect.jsp?r_url=R_Views/mx_emergency_resource/page/yingjizhuangbeiwuzi.jsp"},
  {mark:"气象查询与设置",name:"weatherSearch_setting",url:"[WebServer]/Redirect.jsp?r_url=R_Views/weather/page/weather.jsp&r_width=328&r_height=230&r_name=气象设置&r_pos=righttop&r_type=0&r_resizable=yes"},
  {mark:"应急辅助决策",name:"yingjiHelperDecision",service:'<Event EventType="资源通用功能" ResType = "701" ResFuncCode = "8019" />'},
  {mark:"综合查询",name:"multipleSearch",url:"[WebServer]/Redirect.jsp?r_url=R_Views/integratedQuery/page/search.jsp&r_width=800&r_height=520&r_name=综合查询&r_pos= &r_type=0&r_resizable=yes"},
  {mark:"道路图层",name:"roadLayer",url:"[WebServer]/Redirect.jsp?r_url=R_Views/layerconfig/page/index_http.jsp&r_width=600&r_height=500&r_name=图层管理&r_pos= &r_type=0&r_resizable=yes"},
  {mark:"医疗机构通讯录",name:"hospitalDeparentmentBooklist",url:"[WebServer]/Redirect.jsp?r_url=R_action/ui/changeUI.action&r_width=1000&r_height=630&r_name=应急资源管理&r_pos=leftbottom&r_type=0&r_resizable=yes&style=b&seatid=&funid=1278569003087"},
  {mark:"路由查询",name:"outeSearch",url:"[WebServer]/Redirect.jsp?r_url=R_Views/route/page/route.jsp&r_width=700&r_height=430&r_name=路由查询&r_pos=leftbottom&r_type=0&r_resizable=yes"},
  {mark:"应急资源管理",name:"yingjiSourceManager",url:"[WebServer]/Redirect.jsp?r_url=R_action/ui/changeUI.action&r_width=1000&r_height=630&r_name=应急资源管理&r_pos=leftbottom&r_type=0&r_resizable=yes&style=b&seatid=&funid=1278569003087"},
  {mark:"应急推演",name:"yingjituiyan",url:"[WebServer]/Redirect.jsp?r_url=R_action/ui/changeUI.action&r_width=850&r_height=500&r_name=应急协同&r_pos= &r_type=0&r_resizable=yes&seatid=&style=c&funid=1280806389175"},
  {mark:"应急领导小组",name:"YJLDXZ",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14355450829680"},
  {mark:"应急办公室人员",name:"YJBGSRY",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14352003581080"},
  {mark:"应急抢险人员",name:"YJQXRY",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14354616668590"},
  {mark:"事故协调组人员",name:"SGXTZRY",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14354617931400"},
  {mark:"资源后勤保障成员",name:"ZYHQBZCY",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.actionVformid=14354621052340"},
  {mark:"应急专家",name:"YJZJ",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14354625991090"},
  {mark:"上级应急机构及地方政府部门",name:"SJYJJGJDFZFBM",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14354628320620"},
  {mark:"专业应急队伍",name:"ZYYJDW",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14354629594060"},
  {mark:"地方医疗机构",name:"DFYLJG",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=14354630728430"},
  {mark:"应急消防设备",name:"YJXFSB",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=1278496386993 &funid=1280546355262&type=type&root=1280544647630"},
  {mark:"案例库管理",name:"ALKGL",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=1287212352843"},
  {mark:"MSDS知识库",name:"MSDSZSK",url:"[WebServer]/Redirect.jsp?r_url=R_Views/urgentres/MSDS/page/index.htm&1=1"},
  {mark:"应急预案查询",name:"YJYACX",url:"[WebServer]/Redirect.jsp?r_url=R_action/formtailor/toFormVm.action&formid=1279084981269&enterid=1098&plancat=1285397430164&plantype="},
  {mark:"集合点查询",name:"JHDCX",service:'<Event EventType="脚本播放精简模式" ScriptID="399514" PlayControl="1"/>'},
  {mark:"危险区域标绘",name:"WXQYBH",service:'<Event EventType="调用脚本效果资源" ResType = "201" ResName = "name" ResDesc = "desc" SetCursor="1" />'},
  {mark:"灾害资源添加",name:"ZHTJ",service:'<Event EventType="调用脚本效果资源" ResType = "701" ResName = "气体泄漏" ResDesc = "气体泄漏" SetCursor="1" />'},
  {mark:"警戒线标绘",name:"JJXBH",service:'<Event EventType="调用脚本效果资源" ResType = "204" ResName = "name" ResDesc = "desc" SetCursor="1" />'},
  {mark:"",name:"",url:""},
  {mark:"",name:"",url:""},
  {mark:"",name:"",url:""}
]

var _container = 500;
function getOperID(){
   return _container++;
}
