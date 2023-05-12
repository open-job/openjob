import{_ as a}from"./preload-helper.101896b7.js";import{h as C,ap as i,ar as p,aq as A,K as P,a as f,o as V,a4 as O,j as d,k as F,u as r,v as h,A as L,D as v}from"./vue.543fafcc.js";import{u as M}from"./routesList.76e57b72.js";import{u as j}from"./themeConfig.084d7f79.js";import{e as _}from"./mitt.7f99bbc0.js";import{_ as w}from"./_plugin-vue_export-helper.c27b6911.js";const H={class:"layout-navbars-breadcrumb-index"},N=C({name:"layoutBreadcrumbIndex"}),U=C({...N,setup(q){const y=i(()=>a(()=>import("./breadcrumb.c27a7b33.js"),["assets/breadcrumb.c27a7b33.js","assets/vue.543fafcc.js","assets/storage.b628b270.js","assets/other.eb37633a.js","assets/preload-helper.101896b7.js","assets/index.3b68140d.js","assets/index.b8b1b364.js","assets/_commonjsHelpers.35101cd5.js","assets/index.18e5bded.js","assets/keepAliveNames.cf66da55.js","assets/routesList.76e57b72.js","assets/themeConfig.084d7f79.js","assets/userInfo.2fef1b52.js","assets/loading.7efee3a8.js","assets/loading.70fbfd85.css","assets/tagsViewRoutes.92636029.js","assets/request.b1530b7c.js","assets/index.4ac74e37.js","assets/index.070d5527.js","assets/index.8b89e2e0.css","assets/index.05309dac.js","assets/vue-i18n.cjs.f888bd5c.js","assets/toolsValidate.7cb139e2.js","assets/_plugin-vue_export-helper.c27b6911.js","assets/breadcrumb.9ac6d349.css"])),R=i(()=>a(()=>import("./user.a66fd11d.js"),["assets/user.a66fd11d.js","assets/preload-helper.101896b7.js","assets/vue.543fafcc.js","assets/vue-i18n.cjs.f888bd5c.js","assets/_commonjsHelpers.35101cd5.js","assets/userInfo.2fef1b52.js","assets/storage.b628b270.js","assets/themeConfig.084d7f79.js","assets/other.eb37633a.js","assets/index.3b68140d.js","assets/index.b8b1b364.js","assets/index.18e5bded.js","assets/keepAliveNames.cf66da55.js","assets/routesList.76e57b72.js","assets/loading.7efee3a8.js","assets/loading.70fbfd85.css","assets/tagsViewRoutes.92636029.js","assets/request.b1530b7c.js","assets/index.4ac74e37.js","assets/index.070d5527.js","assets/index.8b89e2e0.css","assets/index.05309dac.js","assets/toolsValidate.7cb139e2.js","assets/mitt.7f99bbc0.js","assets/index.5df4e03c.js","assets/index.3a59200e.js","assets/_plugin-vue_export-helper.c27b6911.js","assets/user.510b3a8a.css"])),b=i(()=>a(()=>import("./index.9f9e529f.js"),["assets/index.9f9e529f.js","assets/vue.543fafcc.js","assets/themeConfig.084d7f79.js","assets/logo-mini.323e783b.js","assets/_plugin-vue_export-helper.c27b6911.js","assets/index.880bac10.css"])),g=i(()=>a(()=>import("./horizontal.1c840497.js"),["assets/horizontal.1c840497.js","assets/preload-helper.101896b7.js","assets/vue.543fafcc.js","assets/routesList.76e57b72.js","assets/themeConfig.084d7f79.js","assets/other.eb37633a.js","assets/index.3b68140d.js","assets/index.b8b1b364.js","assets/_commonjsHelpers.35101cd5.js","assets/index.18e5bded.js","assets/keepAliveNames.cf66da55.js","assets/storage.b628b270.js","assets/userInfo.2fef1b52.js","assets/loading.7efee3a8.js","assets/loading.70fbfd85.css","assets/tagsViewRoutes.92636029.js","assets/request.b1530b7c.js","assets/index.4ac74e37.js","assets/index.070d5527.js","assets/index.8b89e2e0.css","assets/index.05309dac.js","assets/vue-i18n.cjs.f888bd5c.js","assets/toolsValidate.7cb139e2.js","assets/mitt.7f99bbc0.js","assets/_plugin-vue_export-helper.c27b6911.js","assets/horizontal.f075ceb8.css"])),x=M(),E=j(),{themeConfig:c}=p(E),{routesList:u}=p(x),I=A(),l=P({menuList:[]}),S=f(()=>{let{isShowLogo:t,layout:e}=c.value;return t&&e==="classic"||t&&e==="transverse"}),T=f(()=>{let{layout:t,isClassicSplitMenu:e}=c.value;return t==="transverse"||e&&t==="classic"}),m=()=>{let{layout:t,isClassicSplitMenu:e}=c.value;if(t==="classic"&&e){l.menuList=k(n(u.value));const s=B(I.path);_.emit("setSendClassicChildren",s)}else l.menuList=n(u.value)},k=t=>(t.map(e=>{e.children&&delete e.children}),t),n=t=>t.filter(e=>{var s;return!((s=e.meta)!=null&&s.isHide)}).map(e=>(e=Object.assign({},e),e.children&&(e.children=n(e.children)),e)),B=t=>{const e=t.split("/");let s={children:[]};return n(u.value).map((o,D)=>{o.path===`/${e[1]}`&&(o.k=D,s.item={...o},s.children=[{...o}],o.children&&(s.children=o.children))}),s};return V(()=>{m(),_.on("getBreadcrumbIndexSetFilterRoutes",()=>{m()})}),O(()=>{_.off("getBreadcrumbIndexSetFilterRoutes",()=>{})}),(t,e)=>(d(),F("div",H,[r(S)?(d(),h(r(b),{key:0})):L("",!0),v(r(y)),r(T)?(d(),h(r(g),{key:1,menuList:l.menuList},null,8,["menuList"])):L("",!0),v(r(R))]))}});const W=w(U,[["__scopeId","data-v-3b707f43"]]);export{W as default};
