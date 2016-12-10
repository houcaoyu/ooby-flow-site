var App=function(){
	//set blockUI default parameters
	$.blockUI.defaults=$.extend(true,$.blockUI.defaults,{
			css: {
				border: "0",
				padding: "0",
				backgroundColor: "none"
			},overlayCSS: {
				backgroundColor:  "#555",
				opacity:  .1 ,
				cursor: "wait"
			},message:'<div style="display:inline-block;border:1px solid #ddd;background-color:#eee;-webkit-border-radius:4px;-moz-border-radius:4px;-ms-border-radius:4px;-o-border-radius:4px;border-radius:4px;-webkit-box-shadow:0 1px 8px rgba(0,0,0,.1);-moz-box-shadow:0 1px 8px rgba(0,0,0,.1);box-shadow:0 1px 8px rgba(0,0,0,.1)"><i class="fa fa-circle-o-notch fa-spin fa-lg fa-fw" style="vertical-align:middle;"></i><span style="vertical-align:middle;">LOADING...</span></div>'
		})
	
	//set Input Mask default parameters
	Inputmask.extendAliases({
	  'numeric': {
	    autoGroup: !0,
	    groupSeparator: ',',
	    placeholder: "0",
	    clearMaskOnLostFocus: !1,
	    digitsOptional: !1,
	    allowPlus : !1
	    
	  },
	  'currency':{
		  prefix: ""
	  }
	});
	
	
	//give jQuery Validation Plug-in a default ability to show error/success messages
	//via the way of Bootstrap
	var displayMsg=function(label,element){
		//judge whether use form-group-addon
		if($(element).parent('div.input-group').length)
			element=$(element).parent('div.input-group')
		
		//clear formats
		$(element).closest('.form-group').removeClass('has-success has-error has-feedback')
		$(element).nextAll('.help-block._error').remove()
		$(element).nextAll('.form-control-feedback').remove()
		$(element).nextAll('.help-block').show()
		
		var errorText=label.text();
		var formGroupClass,feedBackClass
		if(errorText==''){
			//valid
			formGroupClass='has-success'
			feedBackClass='glyphicon-ok'
		}else{
			//invalid
			formGroupClass='has-error'
			feedBackClass='glyphicon-remove'
			$(element).nextAll('.help-block').hide()
		}
		
		
		$(element).after($('<span class="help-block _error"></span>').text($(label).text()))
		$(element).closest('.form-group').addClass(formGroupClass+' has-feedback')
		$(element).after('<span class="glyphicon '+feedBackClass+' form-control-feedback" aria-hidden="true"></span>')
	}
	$.validator.defaults=$.extend($.validator.defaults,{

		success:displayMsg,
		errorPlacement:displayMsg
	})
	
	//overwrite jQuery validation's elementValue function, 
	// to support input mask , get element value unmasked.
	var ev=$.validator.prototype.elementValue
	$.validator.prototype=$.extend($.validator.prototype,{
		elementValue:function(element){
			if($(element).data('_inputmask_opts'))
				return $(element).inputmask('unmaskedvalue')
			return ev(element)
		}
	})
	
	$.validator.addMethod('complete',function(value,element,param){
		return this.optional( element ) || $(element).inputmask('isComplete')
	},'Input value is not valid.')
	
	
	return {
		
	}
}();