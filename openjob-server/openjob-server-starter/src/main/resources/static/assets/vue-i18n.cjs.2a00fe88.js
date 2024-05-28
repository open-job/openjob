import { c as Xt, a as fr } from "./_commonjsHelpers.35101cd5.js";
import { ax as mr } from "./vue.bd0669ce.js";
var fe = {}, st = { exports: {} }, $ = {};
Object.defineProperty($, "__esModule", { value: !0 });
const _r = typeof window < "u";
let gr, dr;
const hr = /\{([0-9a-zA-Z]+)\}/g;
function pr(e, ...t) {
  return t.length === 1 && Mt(t[0]) && (t = t[0]), (!t || !t.hasOwnProperty) && (t = {}), e.replace(hr, (n, a) => t.hasOwnProperty(a) ? t[a] : "")
}
const Er = typeof Symbol == "function" && typeof Symbol.toStringTag == "symbol", vr = e => Er ? Symbol(e) : e, br = (e, t, n) => _n({ l: e, k: t, s: n }), _n = e => JSON.stringify(e).replace(/\u2028/g, "\\u2028").replace(/\u2029/g, "\\u2029").replace(/\u0027/g, "\\u0027"), Lr = e => typeof e == "number" && isFinite(e), Or = e => it(e) === "[object Date]", Sr = e => it(e) === "[object RegExp]", Cr = e => Pt(e) && Object.keys(e).length === 0;
function Nr(e, t) {
  typeof console < "u" && (console.warn("[intlify] " + e), t && console.warn(t.stack))
}
const Tr = Object.assign;
let Yt;
const yr = () => Yt || (Yt = typeof globalThis < "u" ? globalThis : typeof self < "u" ? self : typeof window < "u" ? window : typeof Xt < "u" ? Xt : {});
function Ar(e) {
  return e.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&apos;")
}
const Ir = Object.prototype.hasOwnProperty;
function Mr(e, t) {
  return Ir.call(e, t)
}
const gn = Array.isArray, Et = e => typeof e == "function", Rr = e => typeof e == "string", Pr = e => typeof e == "boolean", kr = e => typeof e == "symbol", Mt = e => e !== null && typeof e == "object", Fr = e => Mt(e) && Et(e.then) && Et(e.catch), Rt = Object.prototype.toString, it = e => Rt.call(e), Pt = e => it(e) === "[object Object]", Dr = e => e == null ? "" : gn(e) || Pt(e) && e.toString === Rt ? JSON.stringify(e, null, 2) : String(e), Qt = 2;
function wr(e, t = 0, n = e.length) {
  const a = e.split(/\r?\n/);
  let s = 0;
  const r = [];
  for (let i = 0; i < a.length; i++)
    if (s += a[i].length + 1, s >= t) {
      for (let u = i - Qt; u <= i + Qt || n > s; u++) {
        if (u < 0 || u >= a.length)
          continue;
        const o = u + 1;
        r.push(`${o}${" ".repeat(3 - String(o).length)}|  ${a[u]}`);
        const _ = a[u].length;
        if (u === i) {
          const h = t - (s - _) + 1, O = Math.max(1, n > s ? _ - h : n - t);
          r.push("   |  " + " ".repeat(h) + "^".repeat(O))
        } else if (u > i) {
          if (n > s) {
            const h = Math.max(Math.min(n - s, _), 1);
            r.push("   |  " + "^".repeat(h))
          }
          s += _ + 1
        }
      }
      break
    }
  return r.join(`
`)
}
function Ur() {
  const e = new Map;
  return { events: e, on(n, a) { const s = e.get(n); s && s.push(a) || e.set(n, [a]) }, off(n, a) { const s = e.get(n); s && s.splice(s.indexOf(a) >>> 0, 1) }, emit(n, a) { (e.get(n) || []).slice().map(s => s(a)), (e.get("*") || []).slice().map(s => s(n, a)) } }
}
$.assign = Tr;
$.createEmitter = Ur;
$.escapeHtml = Ar;
$.format = pr;
$.friendlyJSONstringify = _n;
$.generateCodeFrame = wr;
$.generateFormatCacheKey = br;
$.getGlobalThis = yr;
$.hasOwn = Mr;
$.inBrowser = _r;
$.isArray = gn;
$.isBoolean = Pr;
$.isDate = Or;
$.isEmptyObject = Cr;
$.isFunction = Et;
$.isNumber = Lr;
$.isObject = Mt;
$.isPlainObject = Pt;
$.isPromise = Fr;
$.isRegExp = Sr;
$.isString = Rr;
$.isSymbol = kr;
$.makeSymbol = vr;
$.mark = gr;
$.measure = dr;
$.objectToString = Rt;
$.toDisplayString = Dr;
$.toTypeString = it;
$.warn = Nr;
(function (e) { e.exports = $ })(st);
var dn = { exports: {} }, F = {}, hn = { exports: {} }, re = {}, lt = {}, kt = {}, ot = {}, Ft = {}, Jt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".split("");
Ft.encode = function (e) { if (0 <= e && e < Jt.length) return Jt[e]; throw new TypeError("Must be between 0 and 63: " + e) };
Ft.decode = function (e) {
  var t = 65, n = 90, a = 97, s = 122, r = 48, i = 57, u = 43, o = 47, _ = 26, h = 52;
  return t <= e && e <= n ? e - t : a <= e && e <= s ? e - a + _ : r <= e && e <= i ? e - r + h : e == u ? 62 : e == o ? 63 : -1
};
var pn = Ft, Dt = 5, En = 1 << Dt, vn = En - 1, bn = En;
function Wr(e) { return e < 0 ? (-e << 1) + 1 : (e << 1) + 0 }
function jr(e) { var t = (e & 1) === 1, n = e >> 1; return t ? -n : n }
ot.encode = function (t) {
  var n = "", a, s = Wr(t);
  do a = s & vn, s >>>= Dt, s > 0 && (a |= bn), n += pn.encode(a); while (
