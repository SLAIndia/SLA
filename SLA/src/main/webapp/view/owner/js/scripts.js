
var adjustScreen=(function(){
	
	var contentHeight=function(){

		var blurHeight =  $('.blurred-bg').css('height'),
		env=CMSCom.deviceFn.findBootstrapEnv();
		
		blurHeight = (parseInt(blurHeight));

		if(env==="lg"||env==="md"){
			//$('.content').css('height',(blurHeight-150));
		}
	
		
		
//		$(".commonScroll").mCustomScrollbar({
//			theme:"inset-2-dark",
//			callbacks:{
//				whileScrolling:function(){
//					$.webshims.validityAlert.hide();
//				}
//			}
//		});
		
		if($(window).height()>768){
		    //Center Margin
		    var windowHalfHeight =$(window).height() / 2;
		    var centerheight = 225;
		    
		    var centermargin = (windowHalfHeight - centerheight - 50);
	
		    $('.full-wrapper').css('margin-top', centermargin);
		}
		else{
			 $('.full-wrapper').css('margin-top', '0');
		}

		
		
	  //Footer Issue Fixes
	  var winHeight=$(window).outerHeight();
	  var headerHeight=$('.navbar-static-top').outerHeight();
	  var footerheight=$('.footer').outerHeight();
	  var cntHeight=winHeight-headerHeight-footerheight-25;
	  
		if(env==="lg"||env==="md"){
			 setTimeout(function(){
					var centerContHeight=$(".blurred-bg").outerHeight(),
					 	mininavSideMenuH=$('.brand').outerHeight()+$('.menu-list').outerHeight(),
					 	navSideMenuH=0;
					
					if(centerContHeight<504){
						 $(".blurred-bg").css('min-height',504);	
						 $(".nav-side-menu").css('min-height',504);				  			
					}else{
						if(mininavSideMenuH>centerContHeight){

							$(".subPageContent").not(".hidden").css({ 'height': mininavSideMenuH*1-56});
							$(".nav-side-menu").css('min-height',mininavSideMenuH);
						}else{

							$(".nav-side-menu").css('min-height',centerContHeight);
						}	
					}			
				},0);
		}
		
	};
	var mCustomScrollbarTop=function(){
		$(".mCSB_container").css({"top":0});	
	};
	var mCustomScrollbar_FixDataTablePagin=function(el){
		
		$("#"+el+"_paginate").on("mouseenter", "li.paginate_button", function(e) { 				
			$(".commonScroll").mCustomScrollbar("disable");  				
		}).mouseleave(function(){
			$(".commonScroll").mCustomScrollbar("update");
		});
	
		$("#"+el+"_filter").on("mouseenter", "input", function(e) { 				
			$(".commonScroll").mCustomScrollbar("disable");  				
		}).mouseleave(function(){
			$(".commonScroll").mCustomScrollbar("update");
		});
		
	};
	var footer=function(window_H,window_W){
		try{
			var centerBox=$(".center-box"),
			centerBoxHeight=centerBox.height(),
			centerBoxTop=centerBox.offset().top;

		if(centerBoxHeight>400){
			footerTop=centerBoxTop+centerBoxHeight+20;			
			$(".footer").css({"top":footerTop});
		}
		}catch(e){}
		
		
	};
	
	return {
		mCustomScrollbar_FixDataTablePagin:mCustomScrollbar_FixDataTablePagin,
		contentHeight:contentHeight,
		mCustomScrollbarTop:mCustomScrollbarTop,
		footer:footer
	}
	
})();




$(document).ready(function() {
	
/*	var window_H=$(window).height(),
		window_W=$(window).width();
	adjustScreen.footer(window_H,window_W);*/
	
	
	var colorBlockW=$('.color-blocks').outerWidth()*0.85;
	var largeDiv=colorBlockW+colorBlockW;
	$('.color-blocks').css('height', colorBlockW);
	$('.auto-height').css('height', largeDiv);
	
/*	//left menu
	if($(window).width()>=767){
		var windowh=$(window).height();
		//$('.nav-side-menu').css('height',windowh-150);
		//$('.blurred-bg').css('height',windowh-150);

		}
	$('[data-toggle="tooltip"]').tooltip();*/
	
	
	
	

}); 