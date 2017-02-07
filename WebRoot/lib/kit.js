var kit = {
    call: function(arg) {
        return Object.prototype.toString.call(arg);
    },
    isNumber: function(arg) {
        return this.call.call(arg) === "[object Number]";
    },
    isArray: function(arg) {
        return this.call(arg) === "[object Array]";
    },
    isObject: function(arg) {
        return this.call(arg) === "[object Object]";
    },isString: function(arg) {
        return this.call(arg) === "[object String]";
    },isUndefined: function(arg) {
        return this.call(arg) === "[object Undefined]";
    },isBoolean: function(arg) {
        return this.call(arg) === "[object Boolean]";
    },isNull: function(arg) {
        if (this.isUndefined(arg))
            throw new Error("arg [" + arg + "] is undefined")
        return arg === null;
    },isFunction: function(arg) {
        return this.call(arg) === "[object Function]";
    },isDate: function(arg) {
        return this.call(arg) === "[object Date]";
    },toInt: function(arg) {
        if (this.isNumber(arg))
            return arg;
        if (this.isString(arg)) {
            try {
                var val = parseInt(arg);
				if(isNaN(val)) throw new Error("arg [" + arg + "] not a string number")
                return val;
            } catch (e) {
                throw e
            }
        } else {
            throw new Error("arg [" + arg + "] not a number")
        }
    },toFloat: function(arg) {
        if (this.isString(arg)) {
            try {
                return parseFloat(arg);
            } catch (e) {
                throw e
            }
        } else {
            throw new Error("arg [" + arg + "] not a number")
        }
    },toObject: function(arg) {
        try {
            return eval("(" + arg + ")");
        } catch (e) {
            throw e
        }
    },toArray: function(arg) {
        return this.toObject(arg);
    },equalsIgnoreCase: function(arg1, arg2) {
        if (!this.isString(arg1) || !this.isString(arg2))
            throw new Error("arguments not string");
        return arg1.toUpperCase() === arg2.toUpperCase();
    },isEmpty: function(arg) {
        return this.trim(arg).length === 0
    },trim: function(arg) {
        if (!this.isString(arg))
            throw new Error("arguments not string");
        return (arg || "").replace(/^\s+|\s+$/g, "");
    },equals: function(arg1, arg2) {
		if(this.isUndefined(arg1) || this.isUndefined(arg2))
			throw new Error("arguments no one can be undefined");
        return arg1 === arg2;
    },trimToEmpty: function(arg) {
        return this.trim(arg);
    },trimToNull: function(arg) {
        var val = this.trim(arg);
        return val.length === 0 ? null : val;
    },inArray: function(elem, array) {
        if (!this.isArray(array))
            throw new Error("arguments not a array:" + array);
        for (var i = 0, length = array.length; i < length; i++)
            // Use === because on IE, window == document
            if (array[i] === elem)
                return i;
        
        return -1;
    },map: function(objects) {
        var entry = new Object();
        var size = 0;
        
        this.putAll = function(kv) {
			if (!kit.isObject(kv)) {
				throw new Error("can not init map class for arguments :" + kit.call(kv));
			}
            for (var k in kv) {
                this.put(k, kv[k]);
            }
        }
        
        this.put = function(k, v) {
            if (kit.isObject(k)) {
                return this.putAll(k);
            }
            size++;
            return entry[k] = v;
        }
        
        if (!kit.isObject(objects)) {
            throw new Error("can not init map class for arguments :" + kit.call(objects));
        } else {
            this.putAll(objects);
        }
        
        this.remove = function(k) {
            delete entry[k];
            size--;
        }
        this.setToNull = function(k) {
            return entry[k] = null;
        }
        this.keySet = function() {
            var array = [];
            for (var k in entry) {
                array.push(k);
            }
            return array;
        }
        this.values = function() {
            var array = [];
            for (var k in entry) {
                array.push(entry[k]);
            }
            return array;
        }
        this.get = function(k) {
            return entry[k];
        }
        this.size = function() {
            return size;
        }
        this.toString = function() {
        
        }
        this.containsKey = function(k) {
            var index = kit.inArray(k, this.keySet());
            return index >= 0;
        }
        this.containsValue = function(v) {
            var index = kit.inArray(v, this.values());
            return index >= 0;
        }
        this.isEmpty = function() {
            return size === 0;
        }
        this.clear = function(v) {
            entry = {};
            size = 0;
        }
    },stringBuffer = function(objects){
		var buffer = [];
		//default invoke
		if(kit.isArray(objects)){
		   this.append(objects.join(""));
		}
		this.append = function(val){
		    this.buffer.push(val);
			return this.buffer;
		}
		this.toString = function(){
		   return this.buffer.join("");
		}
	}
}

