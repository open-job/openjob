import{J as t}from"./vue.543fafcc.js";const n={start:()=>{const d=document.body,i=document.createElement("div");i.setAttribute("class","loading-next");const e=`
			<div class="loading-next-box">
				<div class="loading-next-box-warp">
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
				</div>
			</div>
		`;i.innerHTML=e,d.insertBefore(i,d.childNodes[0]),window.nextLoading=!0},done:(d=0)=>{t(()=>{setTimeout(()=>{var e;window.nextLoading=!1;const i=document.querySelector(".loading-next");(e=i==null?void 0:i.parentNode)==null||e.removeChild(i)},d)})}};export{n as N};
