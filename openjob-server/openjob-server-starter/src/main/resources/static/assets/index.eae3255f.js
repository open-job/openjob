import{s as t,S as r}from"./request.b1530b7c.js";function o(){return{getServerList:e=>t({url:r.serverList,method:"get",params:e}),getSlotsList:e=>t({url:r.slotsList,method:"get",params:e}),getWorkerList:e=>t({url:r.workerList,method:"get",params:e}),getSystemInfo:e=>t({url:r.systemInfo,method:"get",params:e}),updateSystem:e=>t({url:r.systemUpdate,method:"post",data:e})}}export{o as u};
