import{_ as o}from"./preload-helper.101896b7.js";import{ay as _}from"./index.070d5527.js";import{h as p,ap as r,r as b,K as f,o as g,j as y,k as D,l as k,D as s,u as i,R as v}from"./vue.543fafcc.js";import{_ as x}from"./_plugin-vue_export-helper.c27b6911.js";import"./index.3b68140d.js";const S={class:"table-demo-container layout-padding"},C={class:"table-demo-padding layout-padding-view layout-padding-auto"},T=p({name:"makeTableDemo"}),O=p({...T,setup(R){const n=r(()=>o(()=>import("./index.5b4f2e87.js"),["assets/index.5b4f2e87.js","assets/vue.543fafcc.js","assets/_plugin-vue_export-helper.c27b6911.js","assets/index.f6367224.css"])),d=r(()=>o(()=>import("./search.33e9216c.js"),["assets/search.33e9216c.js","assets/vue.543fafcc.js","assets/_plugin-vue_export-helper.c27b6911.js","assets/search.3e5cb508.css"])),l=b(),a=f({tableData:{data:[],header:[{key:"name",colWidth:"",title:"应检尽检核酸采样点名称",type:"text",isCheck:!0},{key:"address",colWidth:"",title:"详细地址",type:"text",isCheck:!0},{key:"phone",colWidth:"",title:"采样点联系电话",type:"text",isCheck:!0},{key:"time",colWidth:"",title:"开放时间",type:"text",isCheck:!0},{key:"isSupport",colWidth:"",title:"是否支持24小时核酸检测",type:"text",isCheck:!0},{key:"image",colWidth:"",width:"70",height:"40",title:"图片描述",type:"image",isCheck:!0}],config:{total:0,loading:!0,isBorder:!1,isSerialNo:!0,isSelection:!0,isOperate:!0,isOpView:!1,isOpUpdate:!1,isOpDelete:!0,isOpMore:!1},search:[{label:"采样点名称",prop:"name",placeholder:"请输入应检尽检核酸采样点名称",required:!0,type:"input"},{label:"详细地址",prop:"address",placeholder:"请输入详细地址",required:!1,type:"input"},{label:"联系电话",prop:"phone",placeholder:"请输入采样点联系电话",required:!1,type:"input"},{label:"开放时间",prop:"time",placeholder:"请选择",required:!1,type:"date"},{label:"支持24小时",prop:"isSupport",placeholder:"请选择",required:!1,type:"select",options:[{label:"是",value:1},{label:"否",value:0}]},{label:"图片描述",prop:"image",placeholder:"请输入图片描述",required:!1,type:"input"},{label:"核酸机构",prop:"mechanism",placeholder:"请输入核酸机构",required:!1,type:"input"}],param:{pageNum:1,pageSize:10}}}),t=()=>{a.tableData.config.loading=!0,a.tableData.data=[];for(let e=0;e<20;e++)a.tableData.data.push({id:`123456789${e+1}`,name:`莲塘别墅广场${e+1}`,address:`中沧公寓中庭榕树下${e+1}`,phone:`0592-6081259${e+1}`,time:"6:00 ~ 24:00",isSupport:`${e%2===0?"是":"否"}`,image:"https://img2.baidu.com/it/u=417454395,2713356475&fm=253&fmt=auto?w=200&h=200"});a.tableData.config.total=a.tableData.data.length,setTimeout(()=>{a.tableData.config.loading=!1},500)},c=e=>{a.tableData.param=Object.assign({},a.tableData.param,{...e}),l.value.pageReset()},u=e=>{_.success(`删除${e.name}成功！`),t()},m=e=>{a.tableData.param.pageNum=e.pageNum,a.tableData.param.pageSize=e.pageSize,t()},h=e=>{a.tableData.header=e};return g(()=>{t()}),(e,q)=>(y(),D("div",S,[k("div",C,[s(i(d),{search:a.tableData.search,onSearch:c},null,8,["search"]),s(i(n),v({ref_key:"tableRef",ref:l},a.tableData,{class:"table-demo",onDelRow:u,onPageChange:m,onSortHeader:h}),null,16)])]))}});const $=x(O,[["__scopeId","data-v-872c9190"]]);export{$ as default};