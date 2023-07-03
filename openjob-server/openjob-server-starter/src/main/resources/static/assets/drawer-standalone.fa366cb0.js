import{_ as V}from"./preload-helper.101896b7.js";import{h as S,ap as D,K as h,a9 as r,j as L,v as k,x as s,D as a,u as d,B as p,C as u,l as w,y as C,E}from"./vue.bd0669ce.js";import{u as B}from"./vue-i18n.cjs.2a00fe88.js";import{u as z}from"./index.7c5dfd4e.js";import{e as b}from"./data.a7368d0d.js";import"./_commonjsHelpers.35101cd5.js";import"./request.386cf962.js";import"./storage.b628b270.js";import"./index.c3b4fd97.js";import"./index.aa7cec2e.js";import"./themeConfig.cb88f033.js";import"./index.f64fca3a.js";import"./index.e8f4f9cf.js";import"./index.ee56f1f7.js";import"./index.3f2ce315.js";import"./header.9390c1c5.js";const N={class:"system-role-dialog-container",style:{padding:"10px",height:"100%"}},P={style:{"text-align":"center",height:"30px"}},J=S({name:"jobDrawerName"}),se=S({...J,setup(M,{expose:y}){const v=D(()=>V(()=>import("./monaco.c8c1dd6a.js").then(t=>t.a),["assets/monaco.c8c1dd6a.js","assets/preload-helper.101896b7.js","assets/vue.bd0669ce.js","assets/monaco.82344604.css"])),{t:c}=B(),T=z(),e=h({loadingShow:!1,closeStatus:!1,editor:{editorStyle:"width: 100%;height: 100%;",language:"shell",value:""},drawer:{isShow:!1},descriptions:{column:6,workerAddress:"",createTime:"",completeTime:"",statusTag:"",statusLabel:"Default"}}),i=h({timerId:0,time:0,counter:0}),I=async t=>{e.descriptions.workerAddress=t.workerAddress,e.descriptions.createTime=t.createTime,e.descriptions.completeTime=t.completeTime,e.descriptions.statusTag=b(t.status).tag,e.descriptions.statusLabel=b(t.status).label,e.drawer.isShow=!0,e.editor.value="",i.time=0,e.closeStatus=!1,e.loadingShow=!1,await g(t,2)},g=async(t,l)=>{if(e.closeStatus)return;let n=50,o=await T.getProcessorList({jobId:t.jobId,jobInstanceId:t.id,executeType:t.executeType,status:t.status,time:i.time,loading:l,size:n}),m="";if(o.list.forEach(function(_){m+=_+`
`}),e.editor.value+=m,e.loadingShow=!1,o.time>0&&(i.time=o.time),o.complete!=1){if(o.list.length>0){e.loadingShow=!0,setTimeout(async()=>{await g(t,2)},1e3);return}e.loadingShow=!0,i.timerId=setInterval(async()=>{clearInterval(i.timerId),await g(t,1)},3e3)}},x=()=>{e.closeStatus=!0,clearInterval(i.timerId)};return y({openDrawer:I}),(t,l)=>{const n=r("el-descriptions-item"),o=r("el-tag"),m=r("el-descriptions"),_=r("Loading"),j=r("el-icon"),A=r("el-drawer");return L(),k(A,{modelValue:e.drawer.isShow,"onUpdate:modelValue":l[0]||(l[0]=f=>e.drawer.isShow=f),direction:"rtl",size:"80%",onClose:l[1]||(l[1]=f=>x())},{header:s(()=>[a(m,{style:{"margin-top":"10px"},column:e.descriptions.column},{default:s(()=>[a(n,{label:d(c)("message.job.instance.workerAddress"),width:"260px",align:"left"},{default:s(()=>[p(u(e.descriptions.workerAddress),1)]),_:1},8,["label"]),a(n,{label:d(c)("message.job.instance.createTime"),width:"260px",align:"left"},{default:s(()=>[p(u(e.descriptions.createTime),1)]),_:1},8,["label"]),a(n,{label:d(c)("message.job.instance.completeTime"),width:"260px",align:"left"},{default:s(()=>[p(u(e.descriptions.completeTime),1)]),_:1},8,["label"]),a(n,{label:d(c)("message.job.instance.status"),width:"260px",align:"left"},{default:s(()=>[a(o,{class:"ml-2",type:e.descriptions.statusTag,size:"small"},{default:s(()=>[p(u(e.descriptions.statusLabel),1)]),_:1},8,["type"])]),_:1},8,["label"])]),_:1},8,["column"])]),default:s(()=>[w("div",N,[a(d(v),{ref:"JobParamsMonacoEditor",editorStyle:e.editor.editorStyle,language:e.editor.language,value:e.editor.value,readOnly:!0,scrollBottom:!0,syncValue:!0},null,8,["editorStyle","language","value"])])]),footer:s(()=>[w("div",P,[C(a(j,{class:"is-loading",size:"30px"},{default:s(()=>[a(_)]),_:1},512),[[E,e.loadingShow]])])]),_:1},8,["modelValue"])}}});export{se as default};