# dev

## Kite

Generate entity,repo,dto,dao,data:

```shell
kite plug run 'gen java ser code' -- --cs @c --tpl openjob/entity,repo,dto,openjob/dao,openjob/dao-impl,openjob/data,openjob/data-impl --pkg io.openjob.server.repository -o @pkg
```

Generate req,vo,controller,service dto:

```shell
cd openjob-server/openjob-server-admin
kite plug run 'gen java ser code' -- --cs @c --tpl curd,openjob/controller,openjob/service,openjob/service-impl --pkg io.openjob.server.admin -o @pkg

kite plug run 'gen java ser code' -- --cs @c --tpl openjob/service,openjob/service-impl --pkg io.openjob.server.admin -o @pkg

kite plug run 'gen java ser code' -- --cs @c --tpl openjob/controller --pkg io.openjob.server.admin -o @pkg
```
