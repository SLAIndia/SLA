
var adjustScreen=(function(){
	
	var contentHeight=function(){

		var leftmenuHeight=$("#cmsAdminLeftMenu").css('height'),
			env=CMSCom.deviceFn.findBootstrapEnv();
		leftmenuHeight = (parseInt(leftmenuHeight));
		
		/*$(".commonScroll").mCustomScrollbar({
			theme:"inset-2-dark",
			callbacks:{
				whileScrolling:function(a,b,c){	
					$.webshims.validityAlert.hide();					
				}
			}
		});*/
//		if(env==="lg"||env==="md"){
//			//leftmenuHeight-=70;
//			$(".commonScroll.mCustomScrollbar").css('height',(leftmenuHeight-140));
//			$("#page-wrapper").css('height',leftmenuHeight);
//			if(!CMSCom.deviceFn.isIE()){
//				$("#page-wrapper").css('min-height',leftmenuHeight);
//				$("#cmsAdminRightContent").css('height',leftmenuHeight);
//			}
//
//		}
		
		
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
	    var cntHeight=winHeight-headerHeight-footerheight;
	
	    $('.content-outer').css('min-height',cntHeight);

	    var centerH=$("#cmsAdminRightContent").outerHeight(),
	    	centertop=$("#cmsAdminRightContent").offset().top;

		if(centerH>100){
			if($(window).width()>=767){				
		
			    
				$("#cmsAdminLeftMenu").height(centerH);
				$("#cmsAdminLeftMenu").css('min-height',cntHeight);
				$("#page-wrapper").css('min-height',cntHeight);

				
			}else{
				//$("#cmsAdminLeftMenu").css('min-height',cntHeight);
			}
			
		}

	};
	
	var footer=function(window_H,window_W){
		

		
	};
	
	return {
		contentHeight:contentHeight,
		footer:footer
	}
	
})();


