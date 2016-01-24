var grid_data = 
[ 
	{id:1,userName:"张三1",birthday:"2007-12-03",phone:"18600445147",sex:"男",address:"北京市中南海1号",email:"562113226@qq.com",mark:"备注"},
	{id:2,userName:"张三2",birthday:"2007-12-03",phone:"18600445147",sex:"男",address:"北京市中南海1号",email:"562113226@qq.com",mark:"备注"}
];
	
	jQuery(function($) {
		var grid_selector = "#grid-table";
		var pager_selector = "#grid-pager";
	
		jQuery(grid_selector).jqGrid({
			//direction: "rtl",
			url:basePath+"getUser",
			data: grid_data,
			//datatype: "local",
			height: 400,
			colNames:['客户编号',"姓名",'出生日期','电话','性别','邮箱','居住地址','备注'],
			colModel:[
				/*{name:'myac',index:'', width:80,fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{
						keys:true,
						delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
						editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
					}
				},*/
				{name:'id',index:'id',width:60, sorttype:"string"},
				{name:'userName',index:'userName',width:90, editable:true},
				{name:'birthday',index:'birthday',width:90, editable:true, sorttype:"date",unformat: pickDate},
				{name:'phone',index:'phone', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
				{name:'sex',index:'sex', width:70, editable: true,edittype:"select",editoptions:{value:"man:男;woman:女"}},
				{name:'email',index:'email', width:150, sortable:false,editable: true,edittype:"textarea",editoptions:{rows:"2",cols:"10"}},
				{name:'address',index:'address',width:90, editable:true,edittype:"textarea"},
				{name:'mark',index:'mark', width:60, edittype:"textarea", editable: true}
			], 
			viewrecords : true,
			rowNum:20,
			rowList:[10,20,30,50],
			pager : pager_selector,
			altRows: true,
			//toppager: true,
			multiselect: true,
			//multikey: "ctrlKey",
			//multiboxonly: true,
			loadComplete : function() {
				var table = this;
			},
			editurl: basePath+"treeInsert.do",//nothing is saved
			caption: "客户详细信息列表",
			autowidth: true
		});
		//enable datepicker
		function pickDate( cellvalue, options, cell ) {
			setTimeout(function(){
				$(cell).find('input[type=text]').datepicker({format:'yyyy-mm-dd' ,autoclose:true}); 
			}, 0);
		}
		
		//菜单按钮
		jQuery(grid_selector).jqGrid('navGrid',pager_selector,
			{ 	//navbar options
				edit: true,editicon :'icon-pencil blue',
				add: true,addicon : 'icon-plus-sign purple',
				del: true,delicon : 'icon-trash red',
				search: true,searchicon : 'icon-search orange',
				refresh: true,refreshicon : 'icon-refresh green',
				view: true,viewicon : 'icon-zoom-in grey',
			},
			{
				//edit record form
				//closeAfterEdit: true,
				recreateForm: true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			},
			{
				//new record form
				closeAfterAdd: true,
				recreateForm: true,
				viewPagerButtons: false,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			},
			{
				//delete record form
				recreateForm: true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_delete_form(form);
					
					form.data('styled', true);
				},
				onClick : function(e) {
					alert(1);
				}
			},
			{
				//search form
				recreateForm: true,
				afterShowSearch: function(e){
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
					style_search_form(form);
				},
				afterRedraw: function(){
					style_search_filters($(this));
				}
				,
				multipleSearch: true,
				/**
				multipleGroup:true,
				showQuery: true
				*/
			},
			{
				//view record form
				recreateForm: true,
				beforeShowForm: function(e){
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
				}
			}
		)
			function style_edit_form(form) {
				form.find('input[name=birthday]').datepicker({format:'yyyy-mm-dd' , autoclose:true}).end().find('input[name=stock]').addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
				//update buttons classes
				var buttons = form.next().find('.EditButton .fm-button');
				buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
				buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
				buttons.eq(1).prepend('<i class="icon-remove"></i>')
				
				buttons = form.next().find('.navButton a');
				buttons.find('.ui-icon').remove();
				buttons.eq(0).append('<i class="icon-chevron-left"></i>');
				buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
			}
		
			function style_delete_form(form) {
				var buttons = form.next().find('.EditButton .fm-button');
				buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
				buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
				buttons.eq(1).prepend('<i class="icon-remove"></i>')
			}
			
			function style_search_filters(form) {
				form.find('.delete-rule').val('X');
				form.find('.add-rule').addClass('btn btn-xs btn-primary');
				form.find('.add-group').addClass('btn btn-xs btn-success');
				form.find('.delete-group').addClass('btn btn-xs btn-danger');
			}
			function style_search_form(form) {
				var dialog = form.closest('.ui-jqdialog');
				var buttons = dialog.find('.EditTable')
				buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
				buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
				buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
			}
			
			function beforeDeleteCallback(e) {
				var form = $(e[0]);
				if(form.data('styled')) return false;

				//设置居中

				var width = form.parent().width();
				var height = form.parent().height();

				var winWidth = $(window).width();
				var winHeight = $(window).height();
				
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header"/>')
				style_delete_form(form);
				
				form.data('styled', true);
			}
			
			function beforeEditCallback(e) {
				//return;
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
			});