
var adjustScreen=(function(){
	
	var contentHeight=function(){

	var blurHeight =  $('.blurred-bg').css('height');
		blurHeight = (parseInt(blurHeight)),
		env=CMSCom.deviceFn.findBootstrapEnv();
		
		/*if(env==="lg"||env==="md"){
			$('.content').css('height',(blurHeight-150));
		}
		
		$(".commonScroll").mCustomScrollbar({
			theme:"inset-2-dark",
			callbacks:{
				whileScrolling:function(){
					 
				}
			}
		});*/
	/*	var footerTop=0;
		
		if($("#cmsVetDashboardWrapper").hasClass("hidden")){
			footerTop=$("#cmsVetInnerContent").offset().top+$("#cmsVetInnerContent").height();
			
		}else{
			footerTop=$("#cmsVetDashboardWrapper").offset().top+$("#cmsVetDashboardWrapper").height();
			$(".footer").css({"top":footerTop});
		}
		*/
		
		
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
	    //$('.content-outer').css('min-height',cntHeight);
	    
	    
	    setTimeout(function(){
			var centerContHeight=$(".blurred-bg").outerHeight(),
			 	mininavSideMenuH=$('.brand').outerHeight()+$('.menu-list').outerHeight(),
			 	navSideMenuH=0;
			
			
			if(centerContHeight<504){
				 $(".blurred-bg").css('min-height',504);	
				 $(".nav-side-menu").css('min-height',504);				  			
			}else{
				if(mininavSideMenuH>centerContHeight){
				//	console.log("IF  ######### ");
					//$(".blurred-bg").css('height',mininavSideMenuH*1);
					$(".inner-cont-sec").not(".hidden").css({ 'height': mininavSideMenuH*1-56});
					$(".nav-side-menu").css('min-height',mininavSideMenuH);
				}else{
				//	console.log("ELSE   ######### ");
				//	$(".blurred-bg").css('height',centerContHeight*1);
					$(".nav-side-menu").css('min-height',centerContHeight);
				}	
			}			
		},0);	
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
	};
	
	return {
		mCustomScrollbar_FixDataTablePagin:mCustomScrollbar_FixDataTablePagin,
		contentHeight:contentHeight,
		mCustomScrollbarTop:mCustomScrollbarTop,
		footer:footer
	}
	
})();


//toggler
/*	$('.toggle_arrow').click(function() {
	if ($(this).parent().hasClass('show')) {
	$(".right_slide").animate({
	right: "+=280"
	}, 700, function() {
	// Animation complete.
	});
	$(this).parent().removeClass('show').addClass('wipe');
	} else {
	$(".right_slide").animate({
	right: "-=280"
	}, 700, function() {
	// Animation complete.
	});
	$(this).parent().removeClass('wipe').addClass('show');
	}
	$(this).toggleClass('open');
	});*/
	
/*	//left menu
	if($(window).width()>=767){
	var windowh=$(window).height();
	$('.nav-side-menu').css('height',windowh-150);
	$('.blurred-bg').css('height',windowh-150);
	}*/