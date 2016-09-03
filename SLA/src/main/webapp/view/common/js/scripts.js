var adjustScreen=(function(){
	
	var contentHeight=function(){

		
		//console.log(" contentHeight ")
		if($(window).height()>400){
		    //Center Margin
		    var windowHalfHeight =$(window).height() / 2;
		    var centerheight = 180;
		    
		    var centermargin = (windowHalfHeight - centerheight-40);
	
		    $('.full-wrapper').css('margin-top', centermargin);
		}
		else{
			 $('.full-wrapper').css('margin-top', '0');
		}
		
		
	  //Footer Issue Fixes
	    var winHeight=$(window).outerHeight();
	   // var headerHeight=$('.navbar-static-top').outerHeight();
	    var footerheight=$('.footer').outerHeight();
	    var cntHeight=winHeight-footerheight;
	    console.log(cntHeight)
	    $('.content-outer').css('min-height',cntHeight);
		
	};
	
	var footer=function(window_H,window_W){

		
		
	};
	
	return {

		contentHeight:contentHeight

	}
	
})();



$(document).ready(function() {
	var colorBlockW=$('.color-blocks').outerWidth();
	var largeDiv=colorBlockW+colorBlockW;
	$('.color-blocks').css('height', colorBlockW);
	$('.auto-height').css('height', largeDiv);
	
	//left menu
	if($(window).width()>=767){
		var windowh=$(window).height();
		$('.nav-side-menu').css('height',windowh-150);
		$('.blurred-bg').css('height',windowh-150);
		}
		

}); 