var CMSStorage = (function() {

	var defaultExpriesTime= (1000 * 1 * 60 * 30); // default: 30 min
		
	var removeStorage=function (name) {
		try {
			localStorage.removeItem(name);
			localStorage.removeItem(name + '_expiresIn');
		} catch (e) {
			console.log('removeStorage: Error removing key [' + key+ '] from localStorage: ' + JSON.stringify(e));					
			return false;
		}
		return true;
	};
	/*
	 * getStorage: retrieves a key from localStorage previously set with
	 * setStorage(). params: key <string> : localStorage key returns: <string> :
	 * value of localStorage key null : in case of expired key or failure
	 */
/*	var getStorage=function(key) {

		var now = Date.now(); // epoch time, lets deal only with integer
		// set expiration for storage
		var expiresIn = localStorage.getItem(key + '_expiresIn');
		if (expiresIn === undefined || expiresIn === null) {
			expiresIn = 0;
		}

		if (expiresIn < now) {// Expired
			removeStorage(key);
			return null;
		} else {
			try {
				var value = localStorage.getItem(key);
				return value;
			} catch (e) {
				console.log('getStorage: Error reading key [' + key+ '] from localStorage: ' + JSON.stringify(e));						
				return null;
			}
		}
	};*/
	var getStorage=function(key) {
		return localStorage.getItem(key);
	};
	

	var updateStorageExpiryTime=function(key) {

		var now = Date.now(); // epoch time, lets deal only with integer
		// set expiration for storage
		var expiresIn = localStorage.getItem(key + '_expiresIn');
		if (expiresIn === undefined || expiresIn === null) {
			expiresIn = 0;
		}

		if (expiresIn < now) {// Expired
			removeStorage(key);
			return false;
		} else {
			try {
				var schedule = now + defaultExpriesTime*1;
				localStorage.setItem(key + '_expiresIn', schedule);		
				return true;
			} catch (e) {
				console.log('getStorage: Error reading key [' + key+ '] from localStorage: ' + JSON.stringify(e));						
				return false;
			}
		}
	};
	/*
	 * setStorage: writes a key into localStorage setting a expire time params:
	 * key <string> : localStorage key value <string> : localStorage value
	 * expires <number> : number of seconds from now to expire the key returns:
	 * <boolean> : telling if operation succeeded
	 */
	var setStorage = function(key, value, expires) {

		if (expires === undefined || expires === null) {
			//expires = (1000 * 24 * 60 * 60); // default: 1 day
			expires=defaultExpriesTime;
			
		} else {
			expires = Math.abs(expires); // make sure it's positive
		}

		var now = Date.now(); // epoch time, lets deal only with integer
		var schedule = now + expires*1;
		try {
			localStorage.setItem(key, value);
			localStorage.setItem(key + '_expiresIn', schedule);
		} catch (e) {
			console.log('setStorage: Error setting key [' + key+ '] in localStorage: ' + JSON.stringify(e));					
			return false;
		}
		return true;
	};
	
	var clearLocalStoragePrefix=function(preFix){
		var arr = [],
		preFixLen=preFix.length;
		
		for (var i = 0; i < localStorage.length; i++){
			var key=localStorage.key(i);
			if(key.length>=preFixLen){
			    if (key.substring(0,preFixLen) === preFix) {
			        arr.push(localStorage.key(i));
			    }
			}
		}

		// Iterate over arr and remove the items by key
		for (var i = 0; i < arr.length; i++) {
		    localStorage.removeItem(arr[i]);
		}

	};
	
	return {
		setStorage : setStorage,
		getStorage : getStorage,
		removeStorage: removeStorage,
		clearLocalStoragePrefix:clearLocalStoragePrefix,
		updateStorageExpiryTime:updateStorageExpiryTime
	}
})();