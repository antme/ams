<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eweblib.cfg.ConfigManager" %>
<%@ page import="com.ams.bean.Menu" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>

<div>点击或者拖动进行排序</div>

<ul style="margin: 0; padding: 0; margin-left: 10px; margin-top:10px;">

   <%
   	List<Menu> menuList = (List<Menu>) ConfigManager.getPropertyObject("menu");
   
    for(Menu m: menuList){
    	out.println("<li class=\"drag-item\">" + m.getTitle() +"</li>");
    }
   
   %>
	<button onclick="saveMenu();">保存</button>
</ul>

<script>
	function saveMenu(){
		
		var list = $(".drag-item");
		var i = 0;
		var items = Array();
		for(i=0; i<list.length; i++){			
			items.push(list[i].innerText)
		}
		
		postAjaxRequest("/ams/sys/menu/create.do", {items:items}, function(data){
			
		});
	}
</script>

<style type="text/css">
.drag-item {
	list-style-type: none;
	display: block;
	padding: 5px;
	border: 1px solid #ccc;
	margin: 2px;
	width: 300px;
	background: #fafafa;
	color: #444;
}

.indicator {
	position: absolute;
	font-size: 9px;
	width: 10px;
	height: 10px;
	display: none;
	color: red;
}
</style>
<script>
        $(function(){
            var indicator = $('<div class="indicator">>></div>').appendTo('body');
            $('.drag-item').draggable({
                revert:true,
                deltaX:0,
                deltaY:0
            }).droppable({
                onDragOver:function(e,source){
                    indicator.css({
                        display:'block',
                        left:$(this).offset().left-10,
                        top:$(this).offset().top+$(this).outerHeight()-5
                    });
                },
                onDragLeave:function(e,source){
                    indicator.hide();
                },
                onDrop:function(e,source){
                    $(source).insertAfter(this);
                    indicator.hide();
                }
            });
        });
    </script>
