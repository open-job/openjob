function p(t,e){let g=t.getDay(),n=o(t),r=Math.floor((t.getMonth()+3)/3).toString();const l={"Y+":t.getFullYear().toString(),"m+":(t.getMonth()+1).toString(),"d+":t.getDate().toString(),"H+":t.getHours().toString(),"M+":t.getMinutes().toString(),"S+":t.getSeconds().toString(),"q+":r},i={0:"日",1:"一",2:"二",3:"三",4:"四",5:"五",6:"六"},u={1:"一",2:"二",3:"三",4:"四"};/(W+)/.test(e)&&(e=e.replace(RegExp.$1,RegExp.$1.length>1?RegExp.$1.length>2?"星期"+i[g]:"周"+i[g]:i[g])),/(Q+)/.test(e)&&(e=e.replace(RegExp.$1,RegExp.$1.length==4?"第"+u[r]+"季度":u[r])),/(Z+)/.test(e)&&(e=e.replace(RegExp.$1,RegExp.$1.length==3?"第"+n+"周":n+""));for(let s in l){let a=new RegExp("("+s+")").exec(e);a&&(e=e.replace(a[1],RegExp.$1.length==1?l[s]:l[s].padStart(RegExp.$1.length,"0")))}return e}function c(t){return t==null||t==0?"-":p(new Date(t*1e3),"YYYY-mm-dd HH:MM:SS")}function f(t){return Date.parse(t)/1e3}function o(t){let e=new Date(t.getTime()),g=e.getDay()||7;e.setDate(e.getDate()-g+1+5);let n=new Date(e.getFullYear(),0,1),r=n.getDay(),l=1;r!=0&&(l=7-r+1),n=new Date(e.getFullYear(),0,1+l);let i=Math.ceil((e.valueOf()-n.valueOf())/864e5);return Math.ceil(i/7)}function D(t){let e=new Date(t).getHours();return e<6?"凌晨好":e<9?"早上好":e<12?"上午好":e<14?"中午好":e<17?"下午好":e<19?"傍晚好":e<22?"晚上好":"夜里好"}export{D as a,c as b,p as f,f as g};
