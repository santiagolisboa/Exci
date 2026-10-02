import test from "node:test";
import assert from "node:assert/strict";
import { resolvePigeonsPath } from "../src/lib/site-routing.ts";
test("the public root is always French, independent of cookies",()=>assert.deepEqual(resolvePigeonsPath("/"),{ locale:"fr", pathname:"/pigeons/fr" }));
test("only real translated URLs resolve",()=>{ for(const locale of ["fr","en","es","de"]) assert.deepEqual(resolvePigeonsPath("/"+locale),{locale,pathname:"/pigeons/"+locale}); });
test("unknown, app and technical paths cannot become soft 404 homepages",()=>{ for(const pathname of ["/missing","/en/missing","/quiz","/auth/login","/api/test","/it"]) assert.equal(resolvePigeonsPath(pathname),null); });
