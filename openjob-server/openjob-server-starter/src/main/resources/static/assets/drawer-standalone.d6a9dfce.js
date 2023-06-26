import{_ as A}from"./preload-helper.101896b7.js";import{u as V}from"./vue-i18n.cjs.f888bd5c.js";import{u as B}from"./index.e3bd8398.js";import{e as b}from"./data.dd299cb7.js";import{ay as D}from"./index.070d5527.js";import{h as v,ap as E,K as y,a9 as p,j as L,v as z,x as s,D as o,u as n,B as d,C as m,l as h}from"./vue.543fafcc.js";import"./_commonjsHelpers.35101cd5.js";import"./request.c84c4c3e.js";import"./storage.b628b270.js";import"./index.a654b9cd.js";import"./index.18e5bded.js";import"./themeConfig.ab165958.js";import"./index.4ac74e37.js";import"./index.3b68140d.js";import"./index.3867ec69.js";import"./header.9390c1c5.js";const N={class:"system-role-dialog-container",style:{padding:"10px",height:"100%"}},P={style:{flex:"auto","text-align":"left","padding-left":"30px","padding-bottom":"10px"}},M=v({name:"jobDrawerName"}),se=v({...M,setup(J,{expose:w}){const I=E(()=>A(()=>import("./monaco.da9167a8.js").then(t=>t.a),["assets/monaco.da9167a8.js","assets/preload-helper.101896b7.js","assets/vue.543fafcc.js","assets/monaco.82344604.css"])),{t:l}=V(),T=B(),e=y({editor:{editorStyle:"width: 100%;height: 100%;",language:"shell",value:""},drawer:{isShow:!1},descriptions:{column:6,workerAddress:"",createTime:"",completeTime:"",statusTag:"",statusLabel:"Default"}}),a=y({timerId:0,time:0,counter:0}),x=async t=>{e.descriptions.workerAddress=t.workerAddress,e.descriptions.createTime=t.createTime,e.descriptions.completeTime=t.completeTime,e.descriptions.statusTag=b(t.status).tag,e.descriptions.statusLabel=b(t.status).label,e.drawer.isShow=!0,e.editor.value="",a.time=0,await f(t,!1,2)},f=async(t,r,c)=>{r&&(e.editor.value+=`
`);let _=30,i=await T.getProcessorList({jobId:t.jobId,jobInstanceId:t.id,executeType:t.executeType,status:t.status,time:a.time,loading:c,size:_});if(i.list.forEach(function(u){e.editor.value+=u+`
`}),i.time>0&&(a.time=i.time),i.complete!=1){if(i.list.length>0){setTimeout(async()=>{await f(t,!1,2)},500);return}a.timerId=setInterval(()=>{e.editor.value+=".",a.counter+=1,a.counter%6==0&&(clearInterval(a.timerId),f(t,!0,1))},500)}},S=()=>{clearInterval(a.timerId)},k=()=>{e.drawer.isShow=!1,clearInterval(a.timerId)},C=async()=>{D.success("更新成功")};return w({openDrawer:x}),(t,r)=>{const c=p("el-descriptions-item"),_=p("el-tag"),i=p("el-descriptions"),u=p("el-button"),j=p("el-drawer");return L(),z(j,{modelValue:e.drawer.isShow,"onUpdate:modelValue":r[0]||(r[0]=g=>e.drawer.isShow=g),direction:"rtl",size:"80%",onClose:r[1]||(r[1]=g=>S())},{header:s(()=>[o(i,{style:{"margin-top":"10px"},column:e.descriptions.column},{default:s(()=>[o(c,{label:n(l)("message.job.instance.workerAddress"),width:"260px",align:"left"},{default:s(()=>[d(m(e.descriptions.workerAddress),1)]),_:1},8,["label"]),o(c,{label:n(l)("message.job.instance.createTime"),width:"260px",align:"left"},{default:s(()=>[d(m(e.descriptions.createTime),1)]),_:1},8,["label"]),o(c,{label:n(l)("message.job.instance.completeTime"),width:"260px",align:"left"},{default:s(()=>[d(m(e.descriptions.completeTime),1)]),_:1},8,["label"]),o(c,{label:n(l)("message.job.instance.status"),width:"260px",align:"left"},{default:s(()=>[o(_,{class:"ml-2",type:e.descriptions.statusTag,size:"small"},{default:s(()=>[d(m(e.descriptions.statusLabel),1)]),_:1},8,["type"])]),_:1},8,["label"])]),_:1},8,["column"])]),default:s(()=>[h("div",N,[o(n(I),{ref:"JobParamsMonacoEditor",editorStyle:e.editor.editorStyle,language:e.editor.language,value:e.editor.value,readOnly:!0,scrollBottom:!0,syncValue:!0},null,8,["editorStyle","language","value"])])]),footer:s(()=>[h("div",P,[o(u,{type:"primary",onClick:C,size:"default"},{default:s(()=>[d(m(n(l)("message.commonBtn.confirm")),1)]),_:1}),o(u,{onClick:k,size:"default"},{default:s(()=>[d(m(n(l)("message.commonBtn.cancel")),1)]),_:1})])]),_:1},8,["modelValue"])}}});export{se as default};
