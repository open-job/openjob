import{_ as de}from"./preload-helper.101896b7.js";import{h as H,ap as ce,r as fe,K as be,o as ye,a9 as b,j as c,v as T,x as r,l as f,C as u,D as l,u as m,k as F,Z as _,F as j,y as x,E as g,B as n}from"./vue.543fafcc.js";import{u as xe}from"./vue-i18n.cjs.f888bd5c.js";import{u as ge}from"./index.3243f535.js";import{g as Te}from"./header.9390c1c5.js";import{a as Fe}from"./index.e3bd8398.js";import{b as N,g as _e}from"./formatTime.057ac5b9.js";import{d as je}from"./data.dd299cb7.js";import{ay as z}from"./index.070d5527.js";import{J as A}from"./index.4ac74e37.js";import"./_commonjsHelpers.35101cd5.js";import"./request.c84c4c3e.js";import"./storage.b628b270.js";import"./index.a654b9cd.js";import"./index.18e5bded.js";import"./themeConfig.ab165958.js";import"./index.3867ec69.js";import"./index.3b68140d.js";const Pe={class:"system-role-dialog-container",style:{padding:"20px"}},Se=f("br",null,null,-1),he=f("br",null,null,-1),ve=f("br",null,null,-1),Ee=f("br",null,null,-1),Ve=f("br",null,null,-1),we=f("br",null,null,-1),ke=f("br",null,null,-1),Ie=f("br",null,null,-1),Re=f("br",null,null,-1),Le=f("br",null,null,-1),Ce=f("br",null,null,-1),Ue=f("br",null,null,-1),$e=f("br",null,null,-1),De=f("br",null,null,-1),Me=f("br",null,null,-1),qe=f("br",null,null,-1),Be={style:{flex:"auto","text-align":"left","padding-left":"30px","padding-bottom":"10px"}},Je=H({name:"jobDrawerName"}),ml=H({...Je,emits:["refresh"],setup(Ne,{expose:K,emit:M}){const I=ce(()=>de(()=>import("./monaco.da9167a8.js").then(o=>o.a),["assets/monaco.da9167a8.js","assets/preload-helper.101896b7.js","assets/vue.543fafcc.js","assets/monaco.82344604.css"])),{t}=xe(),Q=ge(),R=Fe(),L=fe(),e=be({isUpdate:!1,dialogTitle:"",rowState:{inputProcessor:!0,kettleProcessor:!1,shellProcessor:!1,shardingParams:!1,processorParams:!0,timeExpression:!0,executeTime:!1,fixedDelay:!1,fixedRate:!1},syncEditor:!1,shellEditor:{editorStyle:"width: 95%;height: 220px;",language:"shell"},paramsEditor:{editorStyle:"width: 95%;height: 220px;"},paramsExtEditor:{editorStyle:"width: 95%;height: 150px;"},drawer:{type:"",isShow:!1},fromRules:{name:{required:!0,message:t("message.job.job.name"),trigger:"blur"},processorInfo:{required:!0,message:t("message.job.job.processorInfo"),trigger:"blur"},shardingParams:{required:!0,message:t("message.job.job.shardingParams"),trigger:"blur"},kettleProcessorInfo:{required:!0,message:t("message.job.job.kettleCommand"),trigger:"blur"},shellProcessorInfo:{required:!0,message:t("message.job.job.processorInfo"),trigger:"blur"},timeExpression:{required:!0,message:t("message.job.job.timeExpression"),trigger:"blur"},fixedDelay:{required:!0,message:t("message.job.job.validateName.fixed"),pattern:/^[0-9]*$/,trigger:"blur"},fixedRate:{required:!0,message:t("message.job.job.validateName.fixed"),pattern:/^[0-9]*$/,trigger:"blur"},executeTime:{required:!0,message:t("message.job.job.timeExpressionTypeList.oneTimeTitle"),trigger:"blur"}},contentType:[{value:"plaintext",label:"plaintext"},{value:"json",label:"json"},{value:"yaml",label:"yaml"},{value:"properties",label:"properties"}],shellType:[{value:"unix",label:"unix"},{value:"windows",label:"windows"}],processorType:[{value:"processor",label:"processor"},{value:"shell",label:"shell"},{value:"kettle",label:"kettle"}],executeType:[{value:"standalone",label:t("message.job.job.executeTypeList.standalone")},{value:"broadcast",label:t("message.job.job.executeTypeList.broadcast")},{value:"sharding",label:t("message.job.job.executeTypeList.sharding")}],executeStrategy:[{value:1,label:t("message.job.job.executeStrategyList.discard")},{value:2,label:t("message.job.job.executeStrategyList.overlay")},{value:3,label:t("message.job.job.executeStrategyList.concurrency")}],timeExpressionType:[{value:"cron",label:t("message.job.job.timeExpressionTypeList.cron")},{value:"oneTime",label:t("message.job.job.timeExpressionTypeList.oneTime")}],namespaceList:[],appList:[],ruleForm:{timesStep:1,intervalStep:1e3,description:"",paramsType:"plaintext",params:"",extendParamsType:"plaintext",extendParams:"",timeExpressionType:"cron",timeExpression:"",executeTime:"",fixedDelay:"",fixedRate:"",Time:0,executeType:"standalone",processorType:"processor",processorInfo:"",shellProcessorInfo:"",shellProcessorType:"unix",kettleProcessorInfo:"",kettleProcessorType:"unix",shardingParams:"",namespaceId:0,appId:1,id:0,name:"",status:!0,failRetryTimes:1,failRetryInterval:2e3,concurrency:1,executeStrategy:1}});ye(async()=>{await O()});const O=async()=>{let o=await Q.getList({page:1,size:128});e.namespaceList=[],o.list.forEach(function(a){e.namespaceList.push({id:a.id,name:a.name})})},Z=async(o,a,y)=>{if(await X(),e.drawer.type=o,e.drawer.isShow=!0,o==="add"){e.isUpdate=!1,e.dialogTitle=t("message.job.job.addJobTitle"),e.syncEditor=!0,await G(a),e.syncEditor=!1;return}e.isUpdate=!0,o=="update"?e.dialogTitle=t("message.job.job.updateJobTitle"):e.dialogTitle=t("message.job.job.copyJobTitle"),e.syncEditor=!0,await W(y),e.syncEditor=!1},G=async o=>{o==0&&(o=e.appList[0].id),U("cron"),C("processor"),e.ruleForm.namespaceId=Te(),e.ruleForm.appId=o,e.ruleForm.name="",e.ruleForm.description="",e.ruleForm.processorType="processor",e.ruleForm.processorInfo="",e.ruleForm.shellProcessorInfo="",e.ruleForm.params="",e.ruleForm.paramsType="plaintext",e.ruleForm.extendParamsType="plaintext",e.ruleForm.extendParams="",e.ruleForm.timeExpressionType="cron",e.ruleForm.timeExpression="",e.ruleForm.executeTime="",e.ruleForm.fixedDelay="",e.ruleForm.fixedRate="",e.ruleForm.status=!0,e.ruleForm.executeType="standalone",e.ruleForm.shellProcessorType="unix",e.ruleForm.shellProcessorInfo="",e.ruleForm.kettleProcessorType="unix",e.ruleForm.kettleProcessorInfo="",e.ruleForm.shardingParams="",e.ruleForm.executeStrategy=1,e.ruleForm.failRetryTimes=1,e.ruleForm.failRetryInterval=3e3,e.ruleForm.concurrency=1},W=async o=>{var a;(a=L.value)==null||a.clearValidate(),e.ruleForm.id=o.id,e.ruleForm.namespaceId=o.namespaceId,e.ruleForm.appId=o.appId,e.ruleForm.name=o.name,e.ruleForm.description=o.description,e.ruleForm.processorType=o.processorType,e.ruleForm.processorInfo=o.processorInfo,e.ruleForm.params=o.params,e.ruleForm.paramsType=o.paramsType,e.ruleForm.extendParamsType=o.extendParamsType,e.ruleForm.extendParams=o.extendParams,e.ruleForm.timeExpressionType=o.timeExpressionType,e.ruleForm.timeExpression=o.timeExpression,e.ruleForm.status=o.status,e.ruleForm.executeType=o.executeType,e.ruleForm.executeStrategy=o.executeStrategy,e.ruleForm.failRetryTimes=o.failRetryTimes,e.ruleForm.failRetryInterval=o.failRetryInterval,e.ruleForm.concurrency=o.concurrency,e.ruleForm.processorInfo=o.processorInfo,U(o.timeExpressionType),C(o.processorType),q(o.executeType),o.executeType=="sharding"&&(e.ruleForm.shardingParams=o.shardingParams),o.shellProcessorType!=null&&(B(o.shellProcessorType),e.ruleForm.shellProcessorInfo=o.shellProcessorInfo),o.kettleProcessorType!=null&&(J(o.kettleProcessorType),e.ruleForm.kettleProcessorInfo=o.kettleProcessorInfo),o.timeExpressionType=="secondDelay"?(e.ruleForm.fixedDelay=o.timeExpressionValue.toString(),e.ruleForm.fixedRate="",e.ruleForm.timeExpression="",e.ruleForm.executeTime=""):o.timeExpressionType=="fixedRate"?(e.ruleForm.fixedRate=o.timeExpressionValue.toString(),e.ruleForm.fixedDelay="",e.ruleForm.timeExpression="",e.ruleForm.executeTime=""):o.timeExpressionType=="oneTime"?(e.ruleForm.executeTime=N(o.timeExpressionValue),e.ruleForm.fixedRate="",e.ruleForm.timeExpression="",e.ruleForm.fixedDelay=""):(e.ruleForm.fixedRate="",e.ruleForm.fixedRate="",e.ruleForm.executeTime="")},X=async()=>{e.appList=await je()},Y=()=>{e.drawer.isShow=!1},ee=async o=>{if(!o)return;let a=["name"];e.ruleForm.processorType=="shell"?a.push("shellProcessorInfo"):e.ruleForm.processorType=="kettle"?a.push("kettleProcessorInfo"):a.push("processorInfo"),e.ruleForm.timeExpressionType=="secondDelay"?a.push("fixedDelay"):e.ruleForm.timeExpressionType=="fixedRate"?a.push("fixedRate"):e.ruleForm.timeExpressionType=="oneTime"?a.push("executeTime"):a.push("timeExpression"),await o.validateField(a,y=>{if(y)le();else return!1})},le=async()=>{let o;e.ruleForm.timeExpressionType=="secondDelay"?o=e.ruleForm.fixedDelay:e.ruleForm.timeExpressionType=="fixedRate"?o=e.ruleForm.fixedRate:e.ruleForm.timeExpressionType=="oneTime"&&(o=_e(e.ruleForm.executeTime));let a={id:e.ruleForm.id,namespaceId:e.ruleForm.namespaceId,appId:e.ruleForm.appId,name:e.ruleForm.name,description:e.ruleForm.description,processorType:e.ruleForm.processorType,processorInfo:e.ruleForm.processorInfo,executeType:e.ruleForm.executeType,paramsType:e.ruleForm.paramsType,params:e.ruleForm.params,extendParamsType:e.ruleForm.extendParamsType,extendParams:e.ruleForm.extendParams,timeExpressionType:e.ruleForm.timeExpressionType,timeExpression:e.ruleForm.timeExpression,timeExpressionValue:o,executeStrategy:e.ruleForm.executeStrategy,failRetryTimes:e.ruleForm.failRetryTimes,failRetryInterval:e.ruleForm.failRetryInterval,concurrency:e.ruleForm.concurrency,status:e.ruleForm.status?1:2,shellProcessorInfo:e.ruleForm.shellProcessorInfo,shellProcessorType:e.ruleForm.shellProcessorType,kettleProcessorType:e.ruleForm.kettleProcessorType,kettleProcessorInfo:e.ruleForm.kettleProcessorInfo,shardingParams:e.ruleForm.shardingParams};if(e.drawer.type==="add"||e.drawer.type==="copy"){await R.add(a),z.success("新增成功"),e.drawer.isShow=!1,M("refresh");return}await R.update(a),z.success("更新成功"),e.drawer.isShow=!1,M("refresh")},C=o=>{if(o=="processor"&&e.ruleForm.executeType=="sharding"){e.rowState.inputProcessor=!0,e.rowState.shellProcessor=!1,e.rowState.kettleProcessor=!1,e.rowState.processorParams=!1;return}if(o=="shell"){e.rowState.inputProcessor=!1,e.rowState.shellProcessor=!0,e.rowState.kettleProcessor=!1,e.rowState.processorParams=!1;return}if(o=="kettle"){e.rowState.inputProcessor=!1,e.rowState.shellProcessor=!1,e.rowState.kettleProcessor=!0,e.rowState.processorParams=!1;return}e.rowState.inputProcessor=!0,e.rowState.shellProcessor=!1,e.rowState.kettleProcessor=!1,e.rowState.processorParams=!0},q=o=>{if(e.ruleForm.processorType=="processor"&&o!="sharding"){e.rowState.processorParams=!0,e.rowState.shardingParams=!1;return}if(o=="sharding"){e.rowState.processorParams=!1,e.rowState.shardingParams=!0;return}e.rowState.processorParams=!1,e.rowState.shardingParams=!1},U=o=>{if(o=="cron"){e.rowState.timeExpression=!0,e.rowState.fixedDelay=!1,e.rowState.fixedRate=!1,e.rowState.executeTime=!1;return}if(o=="secondDelay"){e.rowState.timeExpression=!1,e.rowState.fixedDelay=!0,e.rowState.fixedRate=!1,e.rowState.executeTime=!1;return}if(o=="fixedRate"){e.rowState.timeExpression=!1,e.rowState.fixedDelay=!1,e.rowState.fixedRate=!0,e.rowState.executeTime=!1;return}if(o=="oneTime"){e.rowState.timeExpression=!1,e.rowState.fixedDelay=!1,e.rowState.fixedRate=!1,e.rowState.executeTime=!0;return}},re=async()=>{let o=await R.timeExpression({timeExpression:e.ruleForm.timeExpression});if(o.valid===2){await A.alert(t("message.job.job.timeExpressionValidMsg"),t("message.commonMsg.tip"),{type:"error"});return}let a="";o.list.forEach(function(y){a+=N(y)+"<br>"}),await A.alert(`<div style="text-align: center">${a}</div>`,t("message.job.job.timeExpressionTitle"),{dangerouslyUseHTMLString:!0})},oe=o=>{e.ruleForm.paramsType=o},se=o=>{e.ruleForm.extendParamsType=o},B=o=>{e.ruleForm.shellProcessorType=o},J=o=>{e.ruleForm.kettleProcessorType=o},ae=o=>{e.ruleForm.shellProcessorInfo=o},te=o=>{e.ruleForm.params=o},me=o=>{e.ruleForm.extendParams=o};return K({openDrawer:Z}),(o,a)=>{const y=b("el-option"),S=b("el-select"),p=b("el-form-item"),i=b("el-col"),d=b("el-row"),P=b("el-input"),v=b("ele-QuestionFilled"),E=b("el-icon"),V=b("el-tooltip"),w=b("el-radio"),k=b("el-radio-group"),$=b("el-button"),ue=b("el-date-picker"),ne=b("el-switch"),D=b("el-input-number"),ie=b("el-form"),pe=b("el-drawer");return c(),T(pe,{modelValue:e.drawer.isShow,"onUpdate:modelValue":a[25]||(a[25]=s=>e.drawer.isShow=s),direction:"rtl",size:"50%"},{header:r(()=>[f("h4",null,u(e.dialogTitle),1)]),default:r(()=>[f("div",Pe,[l(ie,{ref_key:"jobFormRef",ref:L,model:e.ruleForm,"label-width":"130px","label-position":"right",rules:e.fromRules,size:"default"},{default:r(()=>[l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.app.namespace"),prop:"namespaceId"},{default:r(()=>[l(S,{modelValue:e.ruleForm.namespaceId,"onUpdate:modelValue":a[0]||(a[0]=s=>e.ruleForm.namespaceId=s),class:"m-2",placeholder:m(t)("message.commonMsg.emptySelect"),style:{width:"100%"}},{default:r(()=>[(c(!0),F(j,null,_(e.namespaceList,s=>(c(),T(y,{key:s.id,label:s.name,value:s.id},null,8,["label","value"]))),128))]),_:1},8,["modelValue","placeholder"])]),_:1},8,["label"])]),_:1})]),_:1}),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.application"),prop:"appId"},{default:r(()=>[l(S,{modelValue:e.ruleForm.appId,"onUpdate:modelValue":a[1]||(a[1]=s=>e.ruleForm.appId=s),class:"m-2",filterable:"",placeholder:m(t)("message.commonMsg.emptySelect"),style:{width:"100%"}},{default:r(()=>[(c(!0),F(j,null,_(e.appList,s=>(c(),T(y,{key:s.id,label:s.label,value:s.id},null,8,["label","value"]))),128))]),_:1},8,["modelValue","placeholder"])]),_:1},8,["label"])]),_:1})]),_:1}),l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.name"),prop:"name"},{default:r(()=>[l(P,{modelValue:e.ruleForm.name,"onUpdate:modelValue":a[2]||(a[2]=s=>e.ruleForm.name=s),clearable:""},null,8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1}),l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.description"),prop:"description"},{default:r(()=>[l(P,{modelValue:e.ruleForm.description,"onUpdate:modelValue":a[3]||(a[3]=s=>e.ruleForm.description=s),clearable:"",type:"textarea",rows:"3"},null,8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1}),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.processorType"),prop:"processorType"},{default:r(()=>[l(S,{modelValue:e.ruleForm.processorType,"onUpdate:modelValue":a[4]||(a[4]=s=>e.ruleForm.processorType=s),disabled:e.isUpdate,class:"m-2",placeholder:m(t)("message.commonMsg.emptySelect"),style:{width:"100%"}},{default:r(()=>[(c(!0),F(j,null,_(e.processorType,s=>(c(),T(y,{key:s.value,label:s.label,value:s.value,onClick:h=>C(s.value)},null,8,["label","value","onClick"]))),128))]),_:1},8,["modelValue","disabled","placeholder"])]),_:1},8,["label"])]),_:1})]),_:1}),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{prop:"processorInfo"},{label:r(()=>[n(u(o.$t("message.job.job.processorInfo"))+" ",1),l(V,{class:"box-item",effect:"dark",placement:"top"},{content:r(()=>[n(u(o.$t("message.job.job.processorInfoLabel.one")),1),Se,n(" "+u(o.$t("message.job.job.processorInfoLabel.two")),1),he,n(" "+u(o.$t("message.job.job.processorInfoLabel.three")),1),ve]),default:r(()=>[l(E,{style:{"margin-top":"9px"}},{default:r(()=>[l(v)]),_:1})]),_:1})]),default:r(()=>[l(P,{modelValue:e.ruleForm.processorInfo,"onUpdate:modelValue":a[5]||(a[5]=s=>e.ruleForm.processorInfo=s),style:{width:"80%"}},null,8,["modelValue"])]),_:1})]),_:1})]),_:1},512),[[g,e.rowState.inputProcessor]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.kettleCommand"),prop:"kettleProcessorInfo"},{default:r(()=>[l(P,{modelValue:e.ruleForm.kettleProcessorInfo,"onUpdate:modelValue":a[6]||(a[6]=s=>e.ruleForm.kettleProcessorInfo=s)},null,8,["modelValue"])]),_:1},8,["label"]),l(k,{modelValue:e.ruleForm.kettleProcessorType,"onUpdate:modelValue":a[7]||(a[7]=s=>e.ruleForm.kettleProcessorType=s),style:{"margin-left":"120px"}},{default:r(()=>[(c(!0),F(j,null,_(e.shellType,s=>(c(),T(w,{key:s.value,label:s.label,onClick:h=>J(s.value)},{default:r(()=>[n(u(s.value),1)]),_:2},1032,["label","onClick"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1},512),[[g,e.rowState.kettleProcessor]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(m(I),{ref:"shellProcessorMonacoEditor",editorStyle:e.shellEditor.editorStyle,language:e.shellEditor.language,value:e.ruleForm.shellProcessorInfo,syncValue:e.syncEditor,onUpdateContent:ae,style:{"margin-left":"120px"}},null,8,["editorStyle","language","value","syncValue"]),l(k,{modelValue:e.ruleForm.shellProcessorType,"onUpdate:modelValue":a[8]||(a[8]=s=>e.ruleForm.shellProcessorType=s),style:{"margin-left":"120px"}},{default:r(()=>[(c(!0),F(j,null,_(e.shellType,s=>(c(),T(w,{key:s.value,label:s.label,onClick:h=>B(s.value)},{default:r(()=>[n(u(s.value),1)]),_:2},1032,["label","onClick"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1},512),[[g,e.rowState.shellProcessor]]),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{prop:""},{label:r(()=>[n(u(o.$t("message.job.job.executeType"))+" ",1),l(V,{class:"box-item",effect:"dark",placement:"top"},{content:r(()=>[n(u(o.$t("message.job.job.executeTypeLabel.one")),1),Ee,n(" "+u(o.$t("message.job.job.executeTypeLabel.two")),1),Ve,n(" "+u(o.$t("message.job.job.executeTypeLabel.two")),1),we,n(" "+u(o.$t("message.job.job.executeTypeLabel.four")),1),ke]),default:r(()=>[l(E,{style:{"margin-top":"9px"}},{default:r(()=>[l(v)]),_:1})]),_:1})]),default:r(()=>[l(S,{modelValue:e.ruleForm.executeType,"onUpdate:modelValue":a[9]||(a[9]=s=>e.ruleForm.executeType=s),class:"m-2",placeholder:m(t)("message.commonMsg.emptySelect"),style:{width:"100%"}},{default:r(()=>[(c(!0),F(j,null,_(e.executeType,s=>(c(),T(y,{key:s.value,label:s.label,value:s.value,onClick:h=>q(s.value)},null,8,["label","value","onClick"]))),128))]),_:1},8,["modelValue","placeholder"])]),_:1})]),_:1})]),_:1}),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{prop:"shardingParams"},{label:r(()=>[n(u(o.$t("message.job.job.shardingParams"))+" ",1),l(V,{class:"box-item",effect:"dark",placement:"top"},{content:r(()=>[n(u(o.$t("message.job.job.shardingParamsLabel.one")),1),Ie,n(" "+u(o.$t("message.job.job.shardingParamsLabel.two")),1)]),default:r(()=>[l(E,{style:{"margin-top":"9px"}},{default:r(()=>[l(v)]),_:1})]),_:1})]),default:r(()=>[l(P,{type:"textarea",rows:"3",modelValue:e.ruleForm.shardingParams,"onUpdate:modelValue":a[10]||(a[10]=s=>e.ruleForm.shardingParams=s)},null,8,["modelValue"])]),_:1})]),_:1})]),_:1},512),[[g,e.rowState.shardingParams]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.paramsType"),prop:"paramsType"},{default:r(()=>[l(k,{modelValue:e.ruleForm.paramsType,"onUpdate:modelValue":a[11]||(a[11]=s=>e.ruleForm.paramsType=s)},{default:r(()=>[(c(!0),F(j,null,_(e.contentType,s=>(c(),T(w,{key:s.value,label:s.label,onClick:h=>oe(s.value)},{default:r(()=>[n(u(s.value),1)]),_:2},1032,["label","onClick"]))),128))]),_:1},8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.processorParams]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.params"),prop:"params"},{default:r(()=>[l(m(I),{ref:"JobParamsMonacoEditor",editorStyle:e.paramsEditor.editorStyle,language:e.ruleForm.paramsType,value:e.ruleForm.params,syncValue:e.syncEditor,onUpdateContent:te},null,8,["editorStyle","language","value","syncValue"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.processorParams]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.extendParamsType"),prop:"extendParamsType"},{default:r(()=>[l(k,{modelValue:e.ruleForm.extendParamsType,"onUpdate:modelValue":a[12]||(a[12]=s=>e.ruleForm.extendParamsType=s)},{default:r(()=>[(c(!0),F(j,null,_(e.contentType,s=>(c(),T(w,{key:s.value,label:s.label,onClick:h=>se(s.value)},{default:r(()=>[n(u(s.value),1)]),_:2},1032,["label","onClick"]))),128))]),_:1},8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.processorParams]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.extendParams"),prop:"extendParams"},{default:r(()=>[l(m(I),{ref:"JobExtParamsMonacoEditor",editorStyle:e.paramsExtEditor.editorStyle,language:e.ruleForm.extendParamsType,value:e.ruleForm.extendParams,syncValue:e.syncEditor,onUpdateContent:me},null,8,["editorStyle","language","value","syncValue"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.processorParams]]),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.timeExpressionType"),prop:""},{label:r(()=>[n(u(o.$t("message.job.job.timeExpressionType"))+" ",1),l(V,{class:"box-item",effect:"dark",placement:"top"},{content:r(()=>[n(u(o.$t("message.job.job.timeExpressionTypeLabel.one")),1),Re,n(" "+u(o.$t("message.job.job.timeExpressionTypeLabel.two")),1),Le,n(" "+u(o.$t("message.job.job.timeExpressionTypeLabel.three")),1),Ce,n(" "+u(o.$t("message.job.job.timeExpressionTypeLabel.four")),1),Ue]),default:r(()=>[l(E,{style:{"margin-top":"9px"}},{default:r(()=>[l(v)]),_:1})]),_:1})]),default:r(()=>[l(S,{modelValue:e.ruleForm.timeExpressionType,"onUpdate:modelValue":a[13]||(a[13]=s=>e.ruleForm.timeExpressionType=s),class:"m-2",placeholder:m(t)("message.commonMsg.emptySelect"),style:{width:"100%"}},{default:r(()=>[(c(!0),F(j,null,_(e.timeExpressionType,s=>(c(),T(y,{key:s.value,label:s.label,value:s.value,onClick:h=>U(s.value)},null,8,["label","value","onClick"]))),128))]),_:1},8,["modelValue","placeholder"])]),_:1},8,["label"])]),_:1})]),_:1}),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.timeExpression"),prop:"timeExpression"},{default:r(()=>[l(P,{modelValue:e.ruleForm.timeExpression,"onUpdate:modelValue":a[14]||(a[14]=s=>e.ruleForm.timeExpression=s)},null,8,["modelValue"])]),_:1},8,["label"])]),_:1}),l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l($,{type:"success",plain:"",size:"default",onClick:a[15]||(a[15]=s=>re()),style:{"margin-left":"10px"}},{default:r(()=>[n(u(m(t)("message.job.instance.executeTime")),1)]),_:1})]),_:1})]),_:1},512),[[g,e.rowState.timeExpression]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.timeExpressionTypeList.secondDelayTitle"),prop:"fixedDelay"},{default:r(()=>[l(P,{modelValue:e.ruleForm.fixedDelay,"onUpdate:modelValue":a[16]||(a[16]=s=>e.ruleForm.fixedDelay=s)},null,8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.fixedDelay]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.timeExpressionTypeList.fixedRateTitle"),prop:"fixedRate"},{default:r(()=>[l(P,{modelValue:e.ruleForm.fixedRate,"onUpdate:modelValue":a[17]||(a[17]=s=>e.ruleForm.fixedRate=s)},null,8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.fixedRate]]),x(l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.timeExpressionTypeList.oneTimeTitle"),prop:"executeTime"},{default:r(()=>[l(ue,{modelValue:e.ruleForm.executeTime,"onUpdate:modelValue":a[18]||(a[18]=s=>e.ruleForm.executeTime=s),type:"datetime",placeholder:m(t)("message.commonMsg.emptySelect")},null,8,["modelValue","placeholder"])]),_:1},8,["label"])]),_:1})]),_:1},512),[[g,e.rowState.executeTime]]),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.status"),prop:"status"},{default:r(()=>[l(ne,{modelValue:e.ruleForm.status,"onUpdate:modelValue":a[19]||(a[19]=s=>e.ruleForm.status=s),class:"ml-2",name:"status",size:"default",style:{"--el-switch-on-color":"#13ce66","--el-switch-off-color":"#ff4949"}},null,8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1}),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{prop:"executeStrategy"},{label:r(()=>[n(u(o.$t("message.job.job.executeStrategy"))+" ",1),l(V,{class:"box-item",effect:"dark",placement:"top"},{content:r(()=>[n(u(o.$t("message.job.job.executeStrategyLabel.one")),1),$e,n(" "+u(o.$t("message.job.job.executeStrategyLabel.two")),1),De,n(" "+u(o.$t("message.job.job.executeStrategyLabel.three")),1),Me,n(" "+u(o.$t("message.job.job.executeStrategyLabel.four")),1),qe]),default:r(()=>[l(E,{style:{"margin-top":"9px"}},{default:r(()=>[l(v)]),_:1})]),_:1})]),default:r(()=>[l(S,{modelValue:e.ruleForm.executeStrategy,"onUpdate:modelValue":a[20]||(a[20]=s=>e.ruleForm.executeStrategy=s),class:"m-2",placeholder:m(t)("message.commonMsg.emptySelect"),style:{width:"100%"}},{default:r(()=>[(c(!0),F(j,null,_(e.executeStrategy,s=>(c(),T(y,{key:s.value,label:s.label,value:s.value},null,8,["label","value"]))),128))]),_:1},8,["modelValue","placeholder"])]),_:1})]),_:1}),l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.concurrency"),prop:"concurrency"},{default:r(()=>[l(D,{modelValue:e.ruleForm.concurrency,"onUpdate:modelValue":a[21]||(a[21]=s=>e.ruleForm.concurrency=s),min:1,max:128,style:{width:"100%"}},null,8,["modelValue"])]),_:1},8,["label"])]),_:1})]),_:1}),l(d),l(d,null,{default:r(()=>[l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.failRetryTimes"),prop:"failRetryTimes"},{default:r(()=>[l(D,{modelValue:e.ruleForm.failRetryTimes,"onUpdate:modelValue":a[22]||(a[22]=s=>e.ruleForm.failRetryTimes=s),min:1,max:6,step:e.ruleForm.timesStep,style:{width:"100%"}},null,8,["modelValue","step"])]),_:1},8,["label"])]),_:1}),l(i,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:r(()=>[l(p,{label:m(t)("message.job.job.failRetryInterval"),prop:"failRetryInterval"},{default:r(()=>[l(D,{modelValue:e.ruleForm.failRetryInterval,"onUpdate:modelValue":a[23]||(a[23]=s=>e.ruleForm.failRetryInterval=s),step:e.ruleForm.intervalStep,min:1,style:{width:"100%"}},null,8,["modelValue","step"])]),_:1},8,["label"])]),_:1})]),_:1})]),_:1},8,["model","rules"])])]),footer:r(()=>[f("div",Be,[l($,{type:"primary",onClick:a[24]||(a[24]=s=>ee(L.value)),size:"default"},{default:r(()=>[n(u(m(t)("message.commonBtn.confirm")),1)]),_:1}),l($,{onClick:Y,size:"default"},{default:r(()=>[n(u(m(t)("message.commonBtn.cancel")),1)]),_:1})])]),_:1},8,["modelValue"])}}});export{ml as default};
