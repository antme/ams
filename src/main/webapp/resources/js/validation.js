$.extend($.fn.validatebox.defaults.rules,{   
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '请输入中文'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    },
    idcard: {// 验证身份证
        validator: function (value) {
            //return idCard(value);
        	return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    pwdEquals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '密码不匹配'
    },
    username : {// 验证用户名         
    	validator : function(value) {              
    		return /^[a-zA-Z0-9_]{6,16}$/i.test(value);           
    	},          
    	message : '请输入6~16字母数字下划线组成的密码'      //（字母开头，允许6-16字节，允许字母数字下划线）
    },
    unnormal:{// 验证是否包含空格和非法字符
        validator : function(value) {
        	return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
    isNumber: {
        validator: function (value, param) {
             return /^-?\d+\.?\d*$/.test(value);
        },
        message: '请输入正确的数字!'
    },
    worktime: {
        validator: function (value, param) {
             if(value.indexOf(";")==-1 && value.indexOf("；") && value.indexOf("，") && value.indexOf(",")){
            	 return false;
             }
             
             return true;
             
        },
        message: '作息时间段之间请用“分号”或者逗号隔开!'
    }
}); 
