var AjaxService=(function(){
	
	
	let beforeSendHandler =function(xhr) {
	  
		xhr.setRequestHeader("Authorization", "Bearer "+localStorage.getItem('token'));
	}
	let invoke =function(options) {
		let self = this;
		return new Promise(function(resolve, reject) {
			let _options = {
		        url: options.url,
		        type: options.type || 'GET',
		        contentType: options.contentType || 'application/json; charset=utf-8'
		    };
		    if (options.data ) {
		       _options['data'] = options.data;
		    }
		    if(options.secure){
		    	_options['beforeSend'] = beforeSendHandler;
		    }
			$.ajax(_options)
		     .done(function(data, textStatus, jqXHR) {
		    	 let res = {data:data,jqXHR:jqXHR}
		          resolve(res);
		     })
		     .fail(err => {
		         reject(err);
		    });
		});	
	}
	
	return {
		invoke:invoke
	};
})();
