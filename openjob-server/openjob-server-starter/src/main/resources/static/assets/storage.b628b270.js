function f(e){for(var o=1;o<arguments.length;o++){var c=arguments[o];for(var a in c)e[a]=c[a]}return e}var u={read:function(e){return e[0]==='"'&&(e=e.slice(1,-1)),e.replace(/(%[\dA-F]{2})+/gi,decodeURIComponent)},write:function(e){return encodeURIComponent(e).replace(/%(2[346BF]|3[AC-F]|40|5[BDE]|60|7[BCD])/g,decodeURIComponent)}};function l(e,o){function c(r,s,n){if(!(typeof document>"u")){n=f({},o,n),typeof n.expires=="number"&&(n.expires=new Date(Date.now()+n.expires*864e5)),n.expires&&(n.expires=n.expires.toUTCString()),r=encodeURIComponent(r).replace(/%(2[346B]|5E|60|7C)/g,decodeURIComponent).replace(/[()]/g,escape);var t="";for(var i in n)!n[i]||(t+="; "+i,n[i]!==!0&&(t+="="+n[i].split(";")[0]));return document.cookie=r+"="+e.write(s,r)+t}}function a(r){if(!(typeof document>"u"||arguments.length&&!r)){for(var s=document.cookie?document.cookie.split("; "):[],n={},t=0;t<s.length;t++){var i=s[t].split("="),m=i.slice(1).join("=");try{var p=decodeURIComponent(i[0]);if(n[p]=e.read(m,p),r===p)break}catch{}}return r?n[r]:n}}return Object.create({set:c,get:a,remove:function(r,s){c(r,"",f({},s,{expires:-1}))},withAttributes:function(r){return l(this.converter,f({},this.attributes,r))},withConverter:function(r){return l(f({},this.converter,r),this.attributes)}},{attributes:{value:Object.freeze(o)},converter:{value:Object.freeze(e)}})}var g=l(u,{path:"/"});const d="token",v="userInfo",S="/login",I={set(e,o){window.localStorage.setItem(e,JSON.stringify(o))},get(e){let o=window.localStorage.getItem(e);return JSON.parse(o)},remove(e){window.localStorage.removeItem(e)},clear(){window.localStorage.clear()}},C={set(e,o){if(e===d)return g.set(e,o);window.sessionStorage.setItem(e,JSON.stringify(o))},get(e){if(e===d)return g.get(e);let o=window.sessionStorage.getItem(e);return JSON.parse(o)},remove(e){if(e===d)return g.remove(e);window.sessionStorage.removeItem(e)},clear(){g.remove(d),window.sessionStorage.clear()}};export{I as L,S as R,C as S,d as T,v as U,g as a};
