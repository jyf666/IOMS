hojo.provide("hojo.io.script");;;(function(){var a=hojo.isIE?"onreadystatechange":"load",b=/complete|loaded/;hojo.io.script={get:function(c){var d=this._makeScriptDeferred(c);var f=d.ioArgs;hojo._ioAddQueryToUrl(f);hojo._ioNotifyStart(d);if(this._canAttach(f)){var g=this.attach(f.id,f.url,c.frameDoc);if(!f.jsonp&&!f.args.checkString){var e=hojo.connect(g,a,function(h){if(h.type=="load"||b.test(g.readyState)){hojo.disconnect(e);f.scriptLoaded=h}})}};hojo._ioWatch(d,this._validCheck,this._ioCheck,this._resHandle);return d},attach:function(l,m,k){var i=(k||hojo.doc);var j=i.createElement("script");j.type="text/javascript";j.src=m;j.id=l;j.charset="utf-8";return i.getElementsByTagName("head")[0].appendChild(j)},remove:function(l,k){hojo.destroy(hojo.byId(l,k));if(this["jsonp_"+l]){delete this["jsonp_"+l]}},_makeScriptDeferred:function(c){var d=hojo._ioSetArgs(c,this._deferredCancel,this._deferredOk,this._deferredError);var f=d.ioArgs;f.id=hojo._scopeName+"IoScript"+(this._counter++);f.canDelete=false;f.jsonp=c.callbackParamName||c.jsonp;if(f.jsonp){f.query=f.query||"";if(f.query.length>0){f.query+="&"};f.query+=f.jsonp+"="+(c.frameDoc?"parent.":"")+hojo._scopeName+".io.script.jsonp_"+f.id+"._jsonpCallback";f.frameDoc=c.frameDoc;f.canDelete=true;d._jsonpCallback=this._jsonpCallback;this["jsonp_"+f.id]=d};return d},_deferredCancel:function(d){d.canceled=true;if(d.ioArgs.canDelete){hojo.io.script._addDeadScript(d.ioArgs)}},_deferredOk:function(d){var f=d.ioArgs;if(f.canDelete){hojo.io.script._addDeadScript(f)};return f.json||f.scriptLoaded||f},_deferredError:function(n,d){if(d.ioArgs.canDelete){if(n.dojoType=="timeout"){hojo.io.script.remove(d.ioArgs.id,d.ioArgs.frameDoc)}else {hojo.io.script._addDeadScript(d.ioArgs)}};console.log("hojo.io.script error",n);return n},_deadScripts:[],_counter:1,_addDeadScript:function(f){hojo.io.script._deadScripts.push({id:f.id,frameDoc:f.frameDoc});f.frameDoc=null},_validCheck:function(d){var o=hojo.io.script;var p=o._deadScripts;if(p&&p.length>0){for(var q=0;q<p.length;q++){o.remove(p[q].id,p[q].frameDoc);p[q].frameDoc=null};hojo.io.script._deadScripts=[]};return true},_ioCheck:function(d){var f=d.ioArgs;if(f.json||(f.scriptLoaded&&!f.args.checkString)){return true};var r=f.args.checkString;if(r&&eval("typeof("+r+") != 'undefined'")){return true};return false},_resHandle:function(d){if(hojo.io.script._ioCheck(d)){d.callback(d)}else {d.errback( new Error("inconceivable hojo.io.script._resHandle error"))}},_canAttach:function(f){return true},_jsonpCallback:function(s){this.ioArgs.json=s}}})()