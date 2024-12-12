window=this;navigator = {};

var start_time = (new Date).getTime(),
    _jdfp_canvas_md5 = "",
    _jdfp_webgl_md5 = "",
    _fingerprint_step = 1,
    _JdEid = "",
    _eidFlag = !1,
    risk_jd_local_fingerprint = "",
    _jd_e_joint_;
(function (m, l, t) {
    "undefined" !== typeof module && module.exports ? module.exports = t() : l[m] = t()
})("JdJrTdRiskFinger", this, function () {
    function m(b) {
        if (null == b || void 0 == b || "" == b) return "NA";
        if (null == b || void 0 == b || "" == b) var c = "";
        else {
            c = [];
            for (var e = 0; e < 8 * b.length; e += 8) c[e >> 5] |= (b.charCodeAt(e / 8) & 255) << e % 32
        }
        b = 8 * b.length;
        c[b >> 5] |= 128 << b % 32;
        c[(b + 64 >>> 9 << 4) + 14] = b;
        b = 1732584193;
        e = -271733879;
        for (var a = -1732584194, q = 271733878, g = 0; g < c.length; g += 16) {
            var r = b,
                f = e,
                k = a,
                h = q;
            b = t(b, e, a, q, c[g + 0], 7, -680876936);
            q = t(q, b, e, a, c[g + 1], 12, -389564586);
            a = t(a, q, b, e, c[g + 2], 17, 606105819);
            e = t(e, a, q, b, c[g + 3], 22, -1044525330);
            b = t(b, e, a, q, c[g + 4], 7, -176418897);
            q = t(q, b, e, a, c[g + 5], 12, 1200080426);
            a = t(a, q, b, e, c[g + 6], 17, -1473231341);
            e = t(e, a, q, b, c[g + 7], 22, -45705983);
            b = t(b, e, a, q, c[g + 8], 7, 1770035416);
            q = t(q, b, e, a, c[g + 9], 12, -1958414417);
            a = t(a, q, b, e, c[g + 10], 17, -42063);
            e = t(e, a, q, b, c[g + 11], 22, -1990404162);
            b = t(b, e, a, q, c[g + 12], 7, 1804603682);
            q = t(q, b, e, a, c[g + 13], 12, -40341101);
            a = t(a, q, b, e, c[g + 14], 17, -1502002290);
            e = t(e, a, q, b, c[g + 15], 22, 1236535329);
            b = u(b, e, a, q, c[g + 1], 5, -165796510);
            q = u(q, b, e, a, c[g + 6], 9, -1069501632);
            a = u(a, q, b, e, c[g + 11], 14, 643717713);
            e = u(e, a, q, b, c[g + 0], 20, -373897302);
            b = u(b, e, a, q, c[g + 5], 5, -701558691);
            q = u(q, b, e, a, c[g + 10], 9, 38016083);
            a = u(a, q, b, e, c[g + 15], 14, -660478335);
            e = u(e, a, q, b, c[g + 4], 20, -405537848);
            b = u(b, e, a, q, c[g + 9], 5, 568446438);
            q = u(q, b, e, a, c[g + 14], 9, -1019803690);
            a = u(a, q, b, e, c[g + 3], 14, -187363961);
            e = u(e, a, q, b, c[g + 8], 20, 1163531501);
            b = u(b, e, a, q, c[g + 13], 5, -1444681467);
            q = u(q, b, e, a, c[g + 2], 9, -51403784);
            a = u(a, q, b, e, c[g + 7], 14, 1735328473);
            e = u(e, a, q, b, c[g + 12], 20, -1926607734);
            b = l(e ^ a ^ q, b, e, c[g + 5], 4, -378558);
            q = l(b ^ e ^ a, q, b, c[g + 8], 11, -2022574463);
            a = l(q ^ b ^ e, a, q, c[g + 11], 16, 1839030562);
            e = l(a ^ q ^ b, e, a, c[g + 14], 23, -35309556);
            b = l(e ^ a ^ q, b, e, c[g + 1], 4, -1530992060);
            q = l(b ^ e ^ a, q, b, c[g + 4], 11, 1272893353);
            a = l(q ^ b ^ e, a, q, c[g + 7], 16, -155497632);
            e = l(a ^ q ^ b, e, a, c[g + 10], 23, -1094730640);
            b = l(e ^ a ^ q, b, e, c[g + 13], 4, 681279174);
            q = l(b ^ e ^ a, q, b, c[g + 0], 11, -358537222);
            a = l(q ^ b ^ e, a, q, c[g + 3], 16, -722521979);
            e = l(a ^ q ^ b, e, a, c[g + 6], 23, 76029189);
            b = l(e ^ a ^ q, b, e, c[g + 9], 4, -640364487);
            q = l(b ^ e ^ a, q, b, c[g + 12], 11, -421815835);
            a = l(q ^ b ^ e, a, q, c[g + 15], 16, 530742520);
            e = l(a ^ q ^ b, e, a, c[g + 2], 23, -995338651);
            b = v(b, e, a, q, c[g + 0], 6, -198630844);
            q = v(q, b, e, a, c[g + 7], 10, 1126891415);
            a = v(a, q, b, e, c[g + 14], 15, -1416354905);
            e = v(e, a, q, b, c[g + 5], 21, -57434055);
            b = v(b, e, a, q, c[g + 12], 6, 1700485571);
            q = v(q, b, e, a, c[g + 3], 10, -1894986606);
            a = v(a, q, b, e, c[g + 10], 15, -1051523);
            e = v(e, a, q, b, c[g + 1], 21, -2054922799);
            b = v(b, e, a, q, c[g + 8], 6, 1873313359);
            q = v(q, b, e, a, c[g + 15], 10, -30611744);
            a = v(a, q, b, e, c[g + 6], 15, -1560198380);
            e = v(e, a, q, b, c[g + 13], 21, 1309151649);
            b = v(b, e, a, q, c[g + 4], 6, -145523070);
            q = v(q, b, e, a, c[g + 11], 10, -1120210379);
            a = v(a, q, b, e, c[g + 2], 15, 718787259);
            e = v(e, a, q, b, c[g + 9], 21, -343485551);
            b = x(b, r);
            e = x(e, f);
            a = x(a, k);
            q = x(q, h)
        }
        c = [b, e, a, q];
        b = "";
        for (e = 0; e < 4 * c.length; e++) b += "0123456789abcdef".charAt(c[e >> 2] >> e % 4 * 8 + 4 & 15) + "0123456789abcdef".charAt(c[e >> 2] >> e % 4 * 8 & 15);
        return b
    }

    function l(b, c, e, a, g, r) {
        b = x(x(c, b), x(a, r));
        return x(b << g | b >>> 32 - g, e)
    }

    function t(b, c, e, a, g, r, f) {
        return l(c & e | ~c & a, b, c, g, r, f)
    }

    function u(b, c, e, a, g, r, f) {
        return l(c & a | e & ~a, b, c, g, r, f)
    }

    function v(b, c, e, a, g, r, f) {
        return l(e ^ (c | ~a), b, c, g, r, f)
    }

    function x(b, c) {
        var e = (b & 65535) + (c & 65535);
        return (b >> 16) + (c >> 16) + (e >> 16) << 16 | e & 65535
    }
    _fingerprint_step = 2;
    var w = "",
        h = navigator.userAgent.toLowerCase();
    h.indexOf("jdapp") && (h = h.substring(0, 90));
    var a = navigator.language,
        f = h; - 1 != f.indexOf("ipad") || -1 != f.indexOf("iphone os") || -1 != f.indexOf("midp") || -1 != f.indexOf("rv:1.2.3.4") || -1 != f.indexOf("ucweb") || -1 != f.indexOf("android") || -1 != f.indexOf("windows ce") || f.indexOf("windows mobile");
    var n = "NA",
        k = "NA";
    try {
        -1 != f.indexOf("win") && -1 != f.indexOf("95") && (n = "windows", k = "95"), -1 != f.indexOf("win") && -1 != f.indexOf("98") && (n = "windows", k = "98"), -1 != f.indexOf("win 9x") && -1 != f.indexOf("4.90") && (n = "windows", k = "me"), -1 != f.indexOf("win") && -1 != f.indexOf("nt 5.0") && (n = "windows", k = "2000"), -1 != f.indexOf("win") && -1 != f.indexOf("nt") && (n = "windows", k = "NT"), -1 != f.indexOf("win") && -1 != f.indexOf("nt 5.1") && (n = "windows", k = "xp"), -1 != f.indexOf("win") && -1 != f.indexOf("32") && (n = "windows", k = "32"), -1 != f.indexOf("win") && -1 != f.indexOf("nt 5.1") && (n = "windows", k = "7"), -1 != f.indexOf("win") && -1 != f.indexOf("6.0") && (n = "windows", k = "8"), -1 == f.indexOf("win") || -1 == f.indexOf("nt 6.0") && -1 == f.indexOf("nt 6.1") || (n = "windows", k = "9"), -1 != f.indexOf("win") && -1 != f.indexOf("nt 6.2") && (n = "windows", k = "10"), -1 != f.indexOf("linux") && (n = "linux"), -1 != f.indexOf("unix") && (n = "unix"), -1 != f.indexOf("sun") && -1 != f.indexOf("os") && (n = "sun os"), -1 != f.indexOf("ibm") && -1 != f.indexOf("os") && (n = "ibm os/2"), -1 != f.indexOf("mac") && -1 != f.indexOf("pc") && (n = "mac"), -1 != f.indexOf("aix") && (n = "aix"), -1 != f.indexOf("powerpc") && (n = "powerPC"), -1 != f.indexOf("hpux") && (n = "hpux"), -1 != f.indexOf("netbsd") && (n = "NetBSD"), -1 != f.indexOf("bsd") && (n = "BSD"), -1 != f.indexOf("osf1") && (n = "OSF1"), -1 != f.indexOf("irix") && (n = "IRIX", k = ""), -1 != f.indexOf("freebsd") && (n = "FreeBSD"), -1 != f.indexOf("symbianos") && (n = "SymbianOS", k = f.substring(f.indexOf("SymbianOS/") + 10, 3))
    } catch (b) {}
    _fingerprint_step = 3;
    var g = "NA",
        r = "NA";
    try {
        -1 != f.indexOf("msie") && (g = "ie", r = f.substring(f.indexOf("msie ") + 5), r.indexOf(";") && (r = r.substring(0, r.indexOf(";")))); - 1 != f.indexOf("firefox") && (g = "Firefox", r = f.substring(f.indexOf("firefox/") + 8)); - 1 != f.indexOf("opera") && (g = "Opera", r = f.substring(f.indexOf("opera/") + 6, 4)); - 1 != f.indexOf("safari") && (g = "safari", r = f.substring(f.indexOf("safari/") + 7)); - 1 != f.indexOf("chrome") && (g = "chrome", r = f.substring(f.indexOf("chrome/") + 7), r.indexOf(" ") && (r = r.substring(0, r.indexOf(" ")))); - 1 != f.indexOf("navigator") && (g = "navigator", r = f.substring(f.indexOf("navigator/") + 10)); - 1 != f.indexOf("applewebkit") && (g = "applewebkit_chrome", r = f.substring(f.indexOf("applewebkit/") + 12), r.indexOf(" ") && (r = r.substring(0, r.indexOf(" ")))); - 1 != f.indexOf("sogoumobilebrowser") && (g = "\u641c\u72d7\u624b\u673a\u6d4f\u89c8\u5668");
        if (-1 != f.indexOf("ucbrowser") || -1 != f.indexOf("ucweb")) g = "UC\u6d4f\u89c8\u5668";
        if (-1 != f.indexOf("qqbrowser") || -1 != f.indexOf("tencenttraveler")) g = "QQ\u6d4f\u89c8\u5668"; - 1 != f.indexOf("metasr") && (g = "\u641c\u72d7\u6d4f\u89c8\u5668"); - 1 != f.indexOf("360se") && (g = "360\u6d4f\u89c8\u5668"); - 1 != f.indexOf("the world") && (g = "\u4e16\u754c\u4e4b\u7a97\u6d4f\u89c8\u5668"); - 1 != f.indexOf("maxthon") && (g = "\u9068\u6e38\u6d4f\u89c8\u5668")
    } catch (b) {}
    f = function (b) {
        this.options = this.extend(b, {});
        this.nativeForEach = Array.prototype.forEach;
        this.nativeMap = Array.prototype.map
    };
    f.prototype = {
        extend: function (b, c) {
            if (null == b) return c;
            for (var e in b) null != b[e] && c[e] !== b[e] && (c[e] = b[e]);
            return c
        }, getData: function () {
            return w
        }, get: function (b) {
            var c = 1 * r,
                e = [];
            "ie" == g && 7 <= c ? (e.push(h), e.push(a), w = w + ",'userAgent':'" + m(h) + "','language':'" + a + "'", this.browserRedirect(h)) : (e = this.userAgentKey(e), e = this.languageKey(e));
            e.push(g);
            e.push(r);
            e.push(n);
            e.push(k);
            w = w + ",'os':'" + n + "','osVersion':'" + k + "','browser':'" + g + "','browserVersion':'" + r + "'";
            e = this.colorDepthKey(e);
            e = this.screenResolutionKey(e);
            e = this.timezoneOffsetKey(e);
            e = this.sessionStorageKey(e);
            e = this.localStorageKey(e);
            e = this.indexedDbKey(e);
            e = this.addBehaviorKey(e);
            e = this.openDatabaseKey(e);
            e = this.cpuClassKey(e);
            e = this.platformKey(e);
            e = this.hardwareConcurrencyKey(e);
            e = this.doNotTrackKey(e);
            e = this.pluginsKey(e);
            e = this.canvasKey(e);
            e = this.webglKey(e);
            c = this.x64hash128(e.join("~~~"), 31);
            return b(c)
        }, userAgentKey: function (b) {
            this.options.excludeUserAgent || (b.push(navigator.userAgent), w = w + ",'userAgent':'" + m(navigator.userAgent) + "'", this.browserRedirect(navigator.userAgent));
            return b
        }, replaceAll: function (b, c, e) {
            for (; 0 <= b.indexOf(c);) b = b.replace(c, e);
            return b
        }, browserRedirect: function (b) {
            var c = b.toLowerCase();
            b = "ipad" == c.match(/ipad/i);
            var e = "iphone os" == c.match(/iphone os/i),
                a = "midp" == c.match(/midp/i),
                g = "rv:1.2.3.4" == c.match(/rv:1.2.3.4/i),
                r = "ucweb" == c.match(/ucweb/i),
                f = "android" == c.match(/android/i),
                k = "windows ce" == c.match(/windows ce/i);
            c = "windows mobile" == c.match(/windows mobile/i);
            w = b || e || a || g || r || f || k || c ? w + ",'origin':'mobile'" : w + ",'origin':'pc'"
        }, languageKey: function (b) {
            this.options.excludeLanguage || (b.push(navigator.language), w = w + ",'language':'" + this.replaceAll(navigator.language, " ", "_") + "'");
            return b
        }, colorDepthKey: function (b) {
            this.options.excludeColorDepth || (b.push(screen.colorDepth), w = w + ",'colorDepth':'" + screen.colorDepth + "'");
            return b
        }, screenResolutionKey: function (b) {
            if (!this.options.excludeScreenResolution) {
                var c = this.getScreenResolution();
                "undefined" !== typeof c && (b.push(c.join("x")), w = w + ",'screenResolution':'" + c.join("x") + "'")
            }
            return b
        }, getScreenResolution: function () {
            return this.options.detectScreenOrientation ? screen.height > screen.width ? [screen.height, screen.width] : [screen.width, screen.height] : [screen.height, screen.width]
        }, timezoneOffsetKey: function (b) {
            this.options.excludeTimezoneOffset || (b.push((new Date).getTimezoneOffset()), w = w + ",'timezoneOffset':'" + (new Date).getTimezoneOffset() / 60 + "'");
            return b
        }, sessionStorageKey: function (b) {
            !this.options.excludeSessionStorage && this.hasSessionStorage() && (b.push("sessionStorageKey"), w += ",'sessionStorage':true");
            return b
        }, localStorageKey: function (b) {
            !this.options.excludeSessionStorage && this.hasLocalStorage() && (b.push("localStorageKey"), w += ",'localStorage':true");
            return b
        }, indexedDbKey: function (b) {
            !this.options.excludeIndexedDB && this.hasIndexedDB() && (b.push("indexedDbKey"), w += ",'indexedDb':true");
            return b
        }, addBehaviorKey: function (b) {
            document.body && !this.options.excludeAddBehavior && document.body.addBehavior ? (b.push("addBehaviorKey"), w += ",'addBehavior':true") : w += ",'addBehavior':false";
            return b
        }, openDatabaseKey: function (b) {
            !this.options.excludeOpenDatabase && window.openDatabase ? (b.push("openDatabase"), w += ",'openDatabase':true") : w += ",'openDatabase':false";
            return b
        }, cpuClassKey: function (b) {
            this.options.excludeCpuClass || (b.push(this.getNavigatorCpuClass()), w = w + ",'cpu':'" + this.getNavigatorCpuClass() + "'");
            return b
        }, platformKey: function (b) {
            this.options.excludePlatform || (b.push(this.getNavigatorPlatform()), w = w + ",'platform':'" + this.getNavigatorPlatform() + "'");
            return b
        }, hardwareConcurrencyKey: function (b) {
            var c = this.getHardwareConcurrency();
            b.push(c);
            w = w + ",'ccn':'" + c + "'";
            return b
        }, doNotTrackKey: function (b) {
            this.options.excludeDoNotTrack || (b.push(this.getDoNotTrack()), w = w + ",'track':'" + this.getDoNotTrack() + "'");
            return b
        }, canvasKey: function (b) {
            if (!this.options.excludeCanvas && this.isCanvasSupported()) {
                var c = this.getCanvasFp();
                b.push(c);
                _jdfp_canvas_md5 = m(c);
                w = w + ",'canvas':'" + _jdfp_canvas_md5 + "'"
            }
            return b
        }, webglKey: function (b) {
            if (!this.options.excludeWebGL && this.isCanvasSupported()) {
                var c = this.getWebglFp();
                _jdfp_webgl_md5 = m(c);
                b.push(c);
                w = w + ",'webglFp':'" + _jdfp_webgl_md5 + "'"
            }
            return b
        }, pluginsKey: function (b) {
            this.isIE() ? (b.push(this.getIEPluginsString()), w = w + ",'plugins':'" + m(this.getIEPluginsString()) + "'") : (b.push(this.getRegularPluginsString()), w = w + ",'plugins':'" + m(this.getRegularPluginsString()) + "'");
            return b
        }, getRegularPluginsString: function () {
            return this.map(navigator.plugins, function (b) {
                var c = this.map(b, function (b) {
                    return [b.type, b.suffixes].join("~")
                }).join(",");
                return [b.name, b.description, c].join("::")
            }, this).join(";")
        }, getIEPluginsString: function () {
            return window.ActiveXObject ? this.map("AcroPDF.PDF;Adodb.Stream;AgControl.AgControl;DevalVRXCtrl.DevalVRXCtrl.1;MacromediaFlashPaper.MacromediaFlashPaper;Msxml2.DOMDocument;Msxml2.XMLHTTP;PDF.PdfCtrl;QuickTime.QuickTime;QuickTimeCheckObject.QuickTimeCheck.1;RealPlayer;RealPlayer.RealPlayer(tm) ActiveX Control (32-bit);RealVideo.RealVideo(tm) ActiveX Control (32-bit);Scripting.Dictionary;SWCtl.SWCtl;Shell.UIHelper;ShockwaveFlash.ShockwaveFlash;Skype.Detection;TDCCtl.TDCCtl;WMPlayer.OCX;rmocx.RealPlayer G2 Control;rmocx.RealPlayer G2 Control.1".split(";"), function (b) {
                try {
                    return new ActiveXObject(b), b
                } catch (c) {
                    return null
                }
            }).join(";") : ""
        }, hasSessionStorage: function () {
            try {
                return !!window.sessionStorage
            } catch (b) {
                return !0
            }
        }, hasLocalStorage: function () {
            try {
                return !!window.localStorage
            } catch (b) {
                return !0
            }
        }, hasIndexedDB: function () {
            return !!window.indexedDB
        }, getNavigatorCpuClass: function () {
            return navigator.cpuClass ? navigator.cpuClass : "NA"
        }, getNavigatorPlatform: function () {
            return navigator.platform ? navigator.platform : "NA"
        }, getHardwareConcurrency: function () {
            return navigator.hardwareConcurrency ? navigator.hardwareConcurrency : "NA"
        }, getDoNotTrack: function () {
            return navigator.doNotTrack ? navigator.doNotTrack : "NA"
        }, getCanvasFp: function () {
            var b = navigator.userAgent.toLowerCase();
            if ((0 < b.indexOf("jdjr-app") || 0 <= b.indexOf("jdapp")) && (0 < b.indexOf("iphone") || 0 < b.indexOf("ipad"))) return null;
            b = document.createElement("canvas");
            var c = b.getContext("2d");
            c.fillStyle = "red";
            c.fillRect(30, 10, 200, 100);
            c.strokeStyle = "#1a3bc1";
            c.lineWidth = 6;
            c.lineCap = "round";
            c.arc(50, 50, 20, 0, Math.PI, !1);
            c.stroke();
            c.fillStyle = "#42e1a2";
            c.font = "15.4px 'Arial'";
            c.textBaseline = "alphabetic";
            c.fillText("PR flacks quiz gym: TV DJ box when? \u2620", 15, 60);
            c.shadowOffsetX = 1;
            c.shadowOffsetY = 2;
            c.shadowColor = "white";
            c.fillStyle = "rgba(0, 0, 200, 0.5)";
            c.font = "60px 'Not a real font'";
            c.fillText("No\u9a97", 40, 80);
            return b.toDataURL()
        }, getWebglFp: function () {
            var b = navigator.userAgent;
            b = b.toLowerCase();
            if ((0 < b.indexOf("jdjr-app") || 0 <= b.indexOf("jdapp")) && (0 < b.indexOf("iphone") || 0 < b.indexOf("ipad"))) return null;
            b = function (b) {
                c.clearColor(0, 0, 0, 1);
                c.enable(c.DEPTH_TEST);
                c.depthFunc(c.LEQUAL);
                c.clear(c.COLOR_BUFFER_BIT | c.DEPTH_BUFFER_BIT);
                return "[" + b[0] + ", " + b[1] + "]"
            };
            var c = this.getWebglCanvas();
            if (!c) return null;
            var e = [],
                a = c.createBuffer();
            c.bindBuffer(c.ARRAY_BUFFER, a);
            var g = new Float32Array([-.2, -.9, 0, .4, -.26, 0, 0, .732134444, 0]);
            c.bufferData(c.ARRAY_BUFFER, g, c.STATIC_DRAW);
            a.itemSize = 3;
            a.numItems = 3;
            g = c.createProgram();
            var r = c.createShader(c.VERTEX_SHADER);
            c.shaderSource(r, "attribute vec2 attrVertex;varying vec2 varyinTexCoordinate;uniform vec2 uniformOffset;void main(){varyinTexCoordinate=attrVertex+uniformOffset;gl_Position=vec4(attrVertex,0,1);}");
            c.compileShader(r);
            var f = c.createShader(c.FRAGMENT_SHADER);
            c.shaderSource(f, "precision mediump float;varying vec2 varyinTexCoordinate;void main() {gl_FragColor=vec4(varyinTexCoordinate,0,1);}");
            c.compileShader(f);
            c.attachShader(g, r);
            c.attachShader(g, f);
            c.linkProgram(g);
            c.useProgram(g);
            g.vertexPosAttrib = c.getAttribLocation(g, "attrVertex");
            g.offsetUniform = c.getUniformLocation(g, "uniformOffset");
            c.enableVertexAttribArray(g.vertexPosArray);
            c.vertexAttribPointer(g.vertexPosAttrib, a.itemSize, c.FLOAT, !1, 0, 0);
            c.uniform2f(g.offsetUniform, 1, 1);
            c.drawArrays(c.TRIANGLE_STRIP, 0, a.numItems);
            null != c.canvas && e.push(c.canvas.toDataURL());
            e.push("extensions:" + c.getSupportedExtensions().join(";"));
            e.push("extensions:" + c.getSupportedExtensions().join(";"));
            e.push("w1" + b(c.getParameter(c.ALIASED_LINE_WIDTH_RANGE)));
            e.push("w2" + b(c.getParameter(c.ALIASED_POINT_SIZE_RANGE)));
            e.push("w3" + c.getParameter(c.ALPHA_BITS));
            e.push("w4" + (c.getContextAttributes().antialias ? "yes" : "no"));
            e.push("w5" + c.getParameter(c.BLUE_BITS));
            e.push("w6" + c.getParameter(c.DEPTH_BITS));
            e.push("w7" + c.getParameter(c.GREEN_BITS));
            e.push("w8" + function (b) {
                var c, e = b.getExtension("EXT_texture_filter_anisotropic") || b.getExtension("WEBKIT_EXT_texture_filter_anisotropic") || b.getExtension("MOZ_EXT_texture_filter_anisotropic");
                return e ? (c = b.getParameter(e.MAX_TEXTURE_MAX_ANISOTROPY_EXT), 0 === c && (c = 2), c) : null
            }(c));
            e.push("w9" + c.getParameter(c.MAX_COMBINED_TEXTURE_IMAGE_UNITS));
            e.push("w10" + c.getParameter(c.MAX_CUBE_MAP_TEXTURE_SIZE));
            e.push("w11" + c.getParameter(c.MAX_FRAGMENT_UNIFORM_VECTORS));
            e.push("w12" + c.getParameter(c.MAX_RENDERBUFFER_SIZE));
            e.push("w13" + c.getParameter(c.MAX_TEXTURE_IMAGE_UNITS));
            e.push("w14" + c.getParameter(c.MAX_TEXTURE_SIZE));
            e.push("w15" + c.getParameter(c.MAX_VARYING_VECTORS));
            e.push("w16" + c.getParameter(c.MAX_VERTEX_ATTRIBS));
            e.push("w17" + c.getParameter(c.MAX_VERTEX_TEXTURE_IMAGE_UNITS));
            e.push("w18" + c.getParameter(c.MAX_VERTEX_UNIFORM_VECTORS));
            e.push("w19" + b(c.getParameter(c.MAX_VIEWPORT_DIMS)));
            e.push("w20" + c.getParameter(c.RED_BITS));
            e.push("w21" + c.getParameter(c.RENDERER));
            e.push("w22" + c.getParameter(c.SHADING_LANGUAGE_VERSION));
            e.push("w23" + c.getParameter(c.STENCIL_BITS));
            e.push("w24" + c.getParameter(c.VENDOR));
            e.push("w25" + c.getParameter(c.VERSION));
            try {
                var k = c.getExtension("WEBGL_debug_renderer_info");
                k && (e.push("wuv:" + c.getParameter(k.UNMASKED_VENDOR_WEBGL)), e.push("wur:" + c.getParameter(k.UNMASKED_RENDERER_WEBGL)))
            } catch (B) {}
            return e.join("\u00a7")
        }, isCanvasSupported: function () {
            var b = document.createElement("canvas");
            return !(!b.getContext || !b.getContext("2d"))
        }, isIE: function () {
            return "Microsoft Internet Explorer" === navigator.appName || "Netscape" === navigator.appName && /Trident/.test(navigator.userAgent) ? !0 : !1
        }, getWebglCanvas: function () {
            var b = document.createElement("canvas"),
                c = null;
            try {
                var e = navigator.userAgent;
                e = e.toLowerCase();
                (0 < e.indexOf("jdjr-app") || 0 <= e.indexOf("jdapp")) && (0 < e.indexOf("iphone") || 0 < e.indexOf("ipad")) || (c = b.getContext("webgl") || b.getContext("experimental-webgl"))
            } catch (C) {}
            c || (c = null);
            return c
        }, each: function (b, c, e) {
            if (null !== b)
                if (this.nativeForEach && b.forEach === this.nativeForEach) b.forEach(c, e);
                else if (b.length === +b.length)
                for (var a = 0, g = b.length; a < g && c.call(e, b[a], a, b) !== {}; a++);
            else
                for (a in b)
                    if (b.hasOwnProperty(a) && c.call(e, b[a], a, b) === {}) break
        }, map: function (b, c, e) {
            var a = [];
            if (null == b) return a;
            if (this.nativeMap && b.map === this.nativeMap) return b.map(c, e);
            this.each(b, function (b, g, r) {
                a[a.length] = c.call(e, b, g, r)
            });
            return a
        }, x64Add: function (b, c) {
            b = [b[0] >>> 16, b[0] & 65535, b[1] >>> 16, b[1] & 65535];
            c = [c[0] >>> 16, c[0] & 65535, c[1] >>> 16, c[1] & 65535];
            var e = [0, 0, 0, 0];
            e[3] += b[3] + c[3];
            e[2] += e[3] >>> 16;
            e[3] &= 65535;
            e[2] += b[2] + c[2];
            e[1] += e[2] >>> 16;
            e[2] &= 65535;
            e[1] += b[1] + c[1];
            e[0] += e[1] >>> 16;
            e[1] &= 65535;
            e[0] += b[0] + c[0];
            e[0] &= 65535;
            return [e[0] << 16 | e[1], e[2] << 16 | e[3]]
        }, x64Multiply: function (b, c) {
            b = [b[0] >>> 16, b[0] & 65535, b[1] >>> 16, b[1] & 65535];
            c = [c[0] >>> 16, c[0] & 65535, c[1] >>> 16, c[1] & 65535];
            var e = [0, 0, 0, 0];
            e[3] += b[3] * c[3];
            e[2] += e[3] >>> 16;
            e[3] &= 65535;
            e[2] += b[2] * c[3];
            e[1] += e[2] >>> 16;
            e[2] &= 65535;
            e[2] += b[3] * c[2];
            e[1] += e[2] >>> 16;
            e[2] &= 65535;
            e[1] += b[1] * c[3];
            e[0] += e[1] >>> 16;
            e[1] &= 65535;
            e[1] += b[2] * c[2];
            e[0] += e[1] >>> 16;
            e[1] &= 65535;
            e[1] += b[3] * c[1];
            e[0] += e[1] >>> 16;
            e[1] &= 65535;
            e[0] += b[0] * c[3] + b[1] * c[2] + b[2] * c[1] + b[3] * c[0];
            e[0] &= 65535;
            return [e[0] << 16 | e[1], e[2] << 16 | e[3]]
        }, x64Rotl: function (b, c) {
            c %= 64;
            if (32 === c) return [b[1], b[0]];
            if (32 > c) return [b[0] << c | b[1] >>> 32 - c, b[1] << c | b[0] >>> 32 - c];
            c -= 32;
            return [b[1] << c | b[0] >>> 32 - c, b[0] << c | b[1] >>> 32 - c]
        }, x64LeftShift: function (b, c) {
            c %= 64;
            return 0 === c ? b : 32 > c ? [b[0] << c | b[1] >>> 32 - c, b[1] << c] : [b[1] << c - 32, 0]
        }, x64Xor: function (b, c) {
            return [b[0] ^ c[0], b[1] ^ c[1]]
        }, x64Fmix: function (b) {
            b = this.x64Xor(b, [0, b[0] >>> 1]);
            b = this.x64Multiply(b, [4283543511, 3981806797]);
            b = this.x64Xor(b, [0, b[0] >>> 1]);
            b = this.x64Multiply(b, [3301882366, 444984403]);
            return b = this.x64Xor(b, [0, b[0] >>> 1])
        }, x64hash128: function (b, c) {
            b = b || "";
            c = c || 0;
            var e = b.length % 16,
                a = b.length - e,
                g = [0, c];
            c = [0, c];
            for (var r, f, k = [2277735313, 289559509], h = [1291169091, 658871167], n = 0; n < a; n += 16) r = [b.charCodeAt(n + 4) & 255 | (b.charCodeAt(n + 5) & 255) << 8 | (b.charCodeAt(n + 6) & 255) << 16 | (b.charCodeAt(n + 7) & 255) << 24, b.charCodeAt(n) & 255 | (b.charCodeAt(n + 1) & 255) << 8 | (b.charCodeAt(n + 2) & 255) << 16 | (b.charCodeAt(n + 3) & 255) << 24], f = [b.charCodeAt(n + 12) & 255 | (b.charCodeAt(n + 13) & 255) << 8 | (b.charCodeAt(n + 14) & 255) << 16 | (b.charCodeAt(n + 15) & 255) << 24, b.charCodeAt(n + 8) & 255 | (b.charCodeAt(n + 9) & 255) << 8 | (b.charCodeAt(n + 10) & 255) << 16 | (b.charCodeAt(n + 11) & 255) << 24], r = this.x64Multiply(r, k), r = this.x64Rotl(r, 31), r = this.x64Multiply(r, h), g = this.x64Xor(g, r), g = this.x64Rotl(g, 27), g = this.x64Add(g, c), g = this.x64Add(this.x64Multiply(g, [0, 5]), [0, 1390208809]), f = this.x64Multiply(f, h), f = this.x64Rotl(f, 33), f = this.x64Multiply(f, k), c = this.x64Xor(c, f), c = this.x64Rotl(c, 31), c = this.x64Add(c, g), c = this.x64Add(this.x64Multiply(c, [0, 5]), [0, 944331445]);
            r = [0, 0];
            f = [0, 0];
            switch (e) {
            case 15:
                f = this.x64Xor(f, this.x64LeftShift([0, b.charCodeAt(n + 14)], 48));
            case 14:
                f = this.x64Xor(f, this.x64LeftShift([0, b.charCodeAt(n + 13)], 40));
            case 13:
                f = this.x64Xor(f, this.x64LeftShift([0, b.charCodeAt(n + 12)], 32));
            case 12:
                f = this.x64Xor(f, this.x64LeftShift([0, b.charCodeAt(n + 11)], 24));
            case 11:
                f = this.x64Xor(f, this.x64LeftShift([0, b.charCodeAt(n + 10)], 16));
            case 10:
                f = this.x64Xor(f, this.x64LeftShift([0, b.charCodeAt(n + 9)], 8));
            case 9:
                f = this.x64Xor(f, [0, b.charCodeAt(n + 8)]), f = this.x64Multiply(f, h), f = this.x64Rotl(f, 33), f = this.x64Multiply(f, k), c = this.x64Xor(c, f);
            case 8:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 7)], 56));
            case 7:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 6)], 48));
            case 6:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 5)], 40));
            case 5:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 4)], 32));
            case 4:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 3)], 24));
            case 3:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 2)], 16));
            case 2:
                r = this.x64Xor(r, this.x64LeftShift([0, b.charCodeAt(n + 1)], 8));
            case 1:
                r = this.x64Xor(r, [0, b.charCodeAt(n)]), r = this.x64Multiply(r, k), r = this.x64Rotl(r, 31), r = this.x64Multiply(r, h), g = this.x64Xor(g, r)
            }
            g = this.x64Xor(g, [0, b.length]);
            c = this.x64Xor(c, [0, b.length]);
            g = this.x64Add(g, c);
            c = this.x64Add(c, g);
            g = this.x64Fmix(g);
            c = this.x64Fmix(c);
            g = this.x64Add(g, c);
            c = this.x64Add(c, g);
            return ("00000000" + (g[0] >>> 0).toString(16)).slice(-8) + ("00000000" + (g[1] >>> 0).toString(16)).slice(-8) + ("00000000" + (c[0] >>> 0).toString(16)).slice(-8) + ("00000000" + (c[1] >>> 0).toString(16)).slice(-8)
        }
    };
    return f
});
var JDDSecCryptoJS = JDDSecCryptoJS || function (m, l) {
    var t = {},
        u = t.lib = {},
        v = u.Base = function () {
            function a() {}
            return {
                extend: function (g) {
                    a.prototype = this;
                    var b = new a;
                    g && b.mixIn(g);
                    b.hasOwnProperty("init") || (b.init = function () {
                        b.$super.init.apply(this, arguments)
                    });
                    b.init.prototype = b;
                    b.$super = this;
                    return b
                }, create: function () {
                    var a = this.extend();
                    a.init.apply(a, arguments);
                    return a
                }, init: function () {}, mixIn: function (a) {
                    for (var b in a) a.hasOwnProperty(b) && (this[b] = a[b]);
                    a.hasOwnProperty("toString") && (this.toString = a.toString)
                }, clone: function () {
                    return this.init.prototype.extend(this)
                }
            }
        }(),
        x = u.WordArray = v.extend({
            init: function (a, f) {
                a = this.words = a || [];
                this.sigBytes = f != l ? f : 4 * a.length
            }, toString: function (a) {
                return (a || h).stringify(this)
            }, concat: function (a) {
                var g = this.words,
                    b = a.words,
                    c = this.sigBytes;
                a = a.sigBytes;
                this.clamp();
                if (c % 4)
                    for (var e = 0; e < a; e++) g[c + e >>> 2] |= (b[e >>> 2] >>> 24 - e % 4 * 8 & 255) << 24 - (c + e) % 4 * 8;
                else if (65535 < b.length)
                    for (e = 0; e < a; e += 4) g[c + e >>> 2] = b[e >>> 2];
                else g.push.apply(g, b);
                this.sigBytes += a;
                return this
            }, clamp: function () {
                var a = this.words,
                    f = this.sigBytes;
                a[f >>> 2] &= 4294967295 << 32 - f % 4 * 8;
                a.length = m.ceil(f / 4)
            }, clone: function () {
                var a = v.clone.call(this);
                a.words = this.words.slice(0);
                return a
            }, random: function (a) {
                for (var g = [], b = 0; b < a; b += 4) g.push(4294967296 * m.random() | 0);
                return new x.init(g, a)
            }
        });
    u.UUID = v.extend({
        generateUuid: function () {
            for (var a = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".split(""), f = 0, b = a.length; f < b; f++) switch (a[f]) {
            case "x":
                a[f] = m.floor(16 * m.random()).toString(16);
                break;
            case "y":
                a[f] = (m.floor(4 * m.random()) + 8).toString(16)
            }
            return a.join("")
        }
    });
    var w = t.enc = {},
        h = w.Hex = {
            stringify: function (a) {
                var g = a.words;
                a = a.sigBytes;
                var b = [];
                for (var c = 0; c < a; c++) {
                    var e = g[c >>> 2] >>> 24 - c % 4 * 8 & 255;
                    b.push((e >>> 4).toString(16));
                    b.push((e & 15).toString(16))
                }
                return b.join("")
            }, parse: function (a) {
                for (var g = a.length, b = [], c = 0; c < g; c += 2) b[c >>> 3] |= parseInt(a.substr(c, 2), 16) << 24 - c % 8 * 4;
                return new x.init(b, g / 2)
            }
        },
        a = w.Latin1 = {
            stringify: function (a) {
                var g = a.words;
                a = a.sigBytes;
                for (var b = [], c = 0; c < a; c++) b.push(String.fromCharCode(g[c >>> 2] >>> 24 - c % 4 * 8 & 255));
                return b.join("")
            }, parse: function (a) {
                for (var g = a.length, b = [], c = 0; c < g; c++) b[c >>> 2] |= (a.charCodeAt(c) & 255) << 24 - c % 4 * 8;
                return new x.init(b, g)
            }
        },
        f = w.Utf8 = {
            stringify: function (g) {
                try {
                    return decodeURIComponent(escape(a.stringify(g)))
                } catch (r) {
                    throw Error("Malformed UTF-8 data");
                }
            }, parse: function (g) {
                return a.parse(unescape(encodeURIComponent(g)))
            }
        },
        n = u.BufferedBlockAlgorithm = v.extend({
            reset: function () {
                this._data = new x.init;
                this._nDataBytes = 0
            }, _append: function (a) {
                "string" == typeof a && (a = f.parse(a));
                this._data.concat(a);
                this._nDataBytes += a.sigBytes
            }, _process: function (a) {
                var g = this._data,
                    b = g.words,
                    c = g.sigBytes,
                    e = this.blockSize,
                    f = c / (4 * e);
                f = a ? m.ceil(f) : m.max((f | 0) - this._minBufferSize, 0);
                a = f * e;
                c = m.min(4 * a, c);
                if (a) {
                    for (var k = 0; k < a; k += e) this._doProcessBlock(b, k);
                    k = b.splice(0, a);
                    g.sigBytes -= c
                }
                return new x.init(k, c)
            }, clone: function () {
                var a = v.clone.call(this);
                a._data = this._data.clone();
                return a
            }, _minBufferSize: 0
        });
    u.Hasher = n.extend({
        cfg: v.extend(),
        init: function (a) {
            this.cfg = this.cfg.extend(a);
            this.reset()
        }, reset: function () {
            n.reset.call(this);
            this._doReset()
        }, update: function (a) {
            this._append(a);
            this._process();
            return this
        }, finalize: function (a) {
            a && this._append(a);
            return this._doFinalize()
        }, blockSize: 16,
        _createHelper: function (a) {
            return function (g, b) {
                return (new a.init(b)).finalize(g)
            }
        }, _createHmacHelper: function (a) {
            return function (g, b) {
                return (new k.HMAC.init(a, b)).finalize(g)
            }
        }
    });
    var k = t.algo = {};
    return t
}(Math);
JDDSecCryptoJS.lib.Cipher || function (m) {
    var l = JDDSecCryptoJS,
        t = l.lib,
        u = t.Base,
        v = t.WordArray,
        x = t.BufferedBlockAlgorithm,
        w = t.Cipher = x.extend({
            cfg: u.extend(),
            createEncryptor: function (a, f) {
                return this.create(this._ENC_XFORM_MODE, a, f)
            }, init: function (a, f, b) {
                this.cfg = this.cfg.extend(b);
                this._xformMode = a;
                this._key = f;
                this.reset()
            }, reset: function () {
                x.reset.call(this);
                this._doReset()
            }, process: function (a) {
                this._append(a);
                return this._process()
            }, finalize: function (a) {
                a && this._append(a);
                return this._doFinalize()
            }, keySize: 4,
            ivSize: 4,
            _ENC_XFORM_MODE: 1,
            _DEC_XFORM_MODE: 2,
            _createHelper: function () {
                return function (a) {
                    return {
                        encrypt: function (f, b, c) {
                            var e = "string" != typeof b ? k : void 0;
                            return e.encrypt(a, f, b, c)
                        }
                    }
                }
            }()
        }),
        h = l.mode = {},
        a = t.BlockCipherMode = u.extend({
            createEncryptor: function (a, f) {
                return this.Encryptor.create(a, f)
            }, init: function (a, f) {
                this._cipher = a;
                this._iv = f
            }
        });
    h = h.CBC = function () {
        var f = a.extend();
        f.Encryptor = f.extend({
            processBlock: function (a, b) {
                var c = this._cipher,
                    e = c.blockSize,
                    f = this._iv;
                f ? this._iv = m : f = this._prevBlock;
                for (var g = 0; g < e; g++) a[b + g] ^= f[g];
                c.encryptBlock(a, b);
                this._prevBlock = a.slice(b, b + e)
            }
        });
        return f
    }();
    var f = (l.pad = {}).Pkcs7 = {
        pad: function (a, f) {
            f *= 4;
            f -= a.sigBytes % f;
            for (var b = f << 24 | f << 16 | f << 8 | f, c = [], e = 0; e < f; e += 4) c.push(b);
            f = v.create(c, f);
            a.concat(f)
        }, unpad: function (a) {
            a.sigBytes -= a.words[a.sigBytes - 1 >>> 2] & 255
        }
    };
    t.BlockCipher = w.extend({
        cfg: w.cfg.extend({
            mode: h,
            padding: f
        }),
        reset: function () {
            w.reset.call(this);
            var a = this.cfg,
                f = a.iv;
            a = a.mode;
            if (this._xformMode == this._ENC_XFORM_MODE) var b = a.createEncryptor;
            else b = a.createDecryptor, this._minBufferSize = 1;
            this._mode = b.call(a, this, f && f.words)
        }, _doProcessBlock: function (a, f) {
            this._mode.processBlock(a, f)
        }, _doFinalize: function () {
            var a = this.cfg.padding;
            if (this._xformMode == this._ENC_XFORM_MODE) {
                a.pad(this._data, this.blockSize);
                var f = this._process(!0)
            } else f = this._process(!0), a.unpad(f);
            return f
        }, blockSize: 4
    });
    var n = t.CipherParams = u.extend({
        init: function (a) {
            this.mixIn(a)
        }, toString: function (a) {
            return (a || this.formatter).stringify(this)
        }
    });
    l.format = {};
    var k = t.SerializableCipher = u.extend({
        cfg: u.extend({}),
        encrypt: function (a, f, b, c) {
            c = this.cfg.extend(c);
            var e = a.createEncryptor(b, c);
            f = e.finalize(f);
            e = e.cfg;
            return n.create({
                ciphertext: f,
                key: b,
                iv: e.iv,
                algorithm: a,
                mode: e.mode,
                padding: e.padding,
                blockSize: a.blockSize,
                formatter: c.format
            })
        }, _parse: function (a, f) {
            return "string" == typeof a ? f.parse(a, this) : a
        }
    })
}();
(function () {
    var m = JDDSecCryptoJS,
        l = m.lib.BlockCipher,
        t = m.algo,
        u = [],
        v = [],
        x = [],
        w = [],
        h = [],
        a = [],
        f = [],
        n = [],
        k = [];
    (function () {
        for (var g = [], b = 0; 256 > b; b++) g[b] = 128 > b ? b << 1 : b << 1 ^ 283;
        var c = 0,
            e = 0;
        for (b = 0; 256 > b; b++) {
            var l = e ^ e << 1 ^ e << 2 ^ e << 3 ^ e << 4;
            l = l >>> 8 ^ l & 255 ^ 99;
            u[c] = l;
            var q = g[c],
                m = g[q],
                t = g[m],
                y = 257 * g[l] ^ 16843008 * l;
            v[c] = y << 24 | y >>> 8;
            x[c] = y << 16 | y >>> 16;
            w[c] = y << 8 | y >>> 24;
            h[c] = y;
            y = 16843009 * t ^ 65537 * m ^ 257 * q ^ 16843008 * c;
            a[l] = y << 24 | y >>> 8;
            f[l] = y << 16 | y >>> 16;
            n[l] = y << 8 | y >>> 24;
            k[l] = y;
            c ? (c = q ^ g[g[g[t ^ q]]], e ^= g[g[e]]) : c = e = 1
        }
    })();
    var g = [0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54];
    t = t.AES = l.extend({
        _doReset: function () {
            var h = this._key,
                b = h.words,
                c = h.sigBytes / 4;
            h = 4 * ((this._nRounds = c + 6) + 1);
            for (var e = this._keySchedule = [], l = 0; l < h; l++)
                if (l < c) e[l] = b[l];
                else {
                    var q = e[l - 1];
                    l % c ? 6 < c && 4 == l % c && (q = u[q >>> 24] << 24 | u[q >>> 16 & 255] << 16 | u[q >>> 8 & 255] << 8 | u[q & 255]) : (q = q << 8 | q >>> 24, q = u[q >>> 24] << 24 | u[q >>> 16 & 255] << 16 | u[q >>> 8 & 255] << 8 | u[q & 255], q ^= g[l / c | 0] << 24);
                    e[l] = e[l - c] ^ q
                }
            b = this._invKeySchedule = [];
            for (c = 0; c < h; c++) l = h - c, q = c % 4 ? e[l] : e[l - 4], b[c] = 4 > c || 4 >= l ? q : a[u[q >>> 24]] ^ f[u[q >>> 16 & 255]] ^ n[u[q >>> 8 & 255]] ^ k[u[q & 255]]
        }, encryptBlock: function (a, b) {
            this._doCryptBlock(a, b, this._keySchedule, v, x, w, h, u)
        }, _doCryptBlock: function (a, b, c, e, f, g, k, h) {
            for (var n = this._nRounds, q = a[b] ^ c[0], l = a[b + 1] ^ c[1], r = a[b + 2] ^ c[2], m = a[b + 3] ^ c[3], t = 4, u = 1; u < n; u++) {
                var v = e[q >>> 24] ^ f[l >>> 16 & 255] ^ g[r >>> 8 & 255] ^ k[m & 255] ^ c[t++],
                    w = e[l >>> 24] ^ f[r >>> 16 & 255] ^ g[m >>> 8 & 255] ^ k[q & 255] ^ c[t++],
                    x = e[r >>> 24] ^ f[m >>> 16 & 255] ^ g[q >>> 8 & 255] ^ k[l & 255] ^ c[t++];
                m = e[m >>> 24] ^ f[q >>> 16 & 255] ^ g[l >>> 8 & 255] ^ k[r & 255] ^ c[t++];
                q = v;
                l = w;
                r = x
            }
            v = (h[q >>> 24] << 24 | h[l >>> 16 & 255] << 16 | h[r >>> 8 & 255] << 8 | h[m & 255]) ^ c[t++];
            w = (h[l >>> 24] << 24 | h[r >>> 16 & 255] << 16 | h[m >>> 8 & 255] << 8 | h[q & 255]) ^ c[t++];
            x = (h[r >>> 24] << 24 | h[m >>> 16 & 255] << 16 | h[q >>> 8 & 255] << 8 | h[l & 255]) ^ c[t++];
            m = (h[m >>> 24] << 24 | h[q >>> 16 & 255] << 16 | h[l >>> 8 & 255] << 8 | h[r & 255]) ^ c[t++];
            a[b] = v;
            a[b + 1] = w;
            a[b + 2] = x;
            a[b + 3] = m
        }, keySize: 8
    });
    m.AES = l._createHelper(t)
})();
(function () {
    var m = JDDSecCryptoJS,
        l = m.lib,
        t = l.WordArray,
        u = l.Hasher,
        v = [];
    l = m.algo.SHA1 = u.extend({
        _doReset: function () {
            this._hash = new t.init([1732584193, 4023233417, 2562383102, 271733878, 3285377520])
        }, _doProcessBlock: function (l, m) {
            for (var h = this._hash.words, a = h[0], f = h[1], n = h[2], k = h[3], g = h[4], r = 0; 80 > r; r++) {
                if (16 > r) v[r] = l[m + r] | 0;
                else {
                    var b = v[r - 3] ^ v[r - 8] ^ v[r - 14] ^ v[r - 16];
                    v[r] = b << 1 | b >>> 31
                }
                b = (a << 5 | a >>> 27) + g + v[r];
                b = 20 > r ? b + ((f & n | ~f & k) + 1518500249) : 40 > r ? b + ((f ^ n ^ k) + 1859775393) : 60 > r ? b + ((f & n | f & k | n & k) - 1894007588) : b + ((f ^ n ^ k) - 899497514);
                g = k;
                k = n;
                n = f << 30 | f >>> 2;
                f = a;
                a = b
            }
            h[0] = h[0] + a | 0;
            h[1] = h[1] + f | 0;
            h[2] = h[2] + n | 0;
            h[3] = h[3] + k | 0;
            h[4] = h[4] + g | 0
        }, _doFinalize: function () {
            var l = this._data,
                m = l.words,
                h = 8 * this._nDataBytes,
                a = 8 * l.sigBytes;
            m[a >>> 5] |= 128 << 24 - a % 32;
            m[(a + 64 >>> 9 << 4) + 14] = Math.floor(h / 4294967296);
            m[(a + 64 >>> 9 << 4) + 15] = h;
            l.sigBytes = 4 * m.length;
            this._process();
            return this._hash
        }, clone: function () {
            var l = u.clone.call(this);
            l._hash = this._hash.clone();
            return l
        }
    });
    m.SHA1 = u._createHelper(l);
    m.HmacSHA1 = u._createHmacHelper(l)
})();
(function () {
    var m = JDDSecCryptoJS,
        l = m.lib.WordArray;
    m.enc.Base32 = {
        stringify: function (l) {
            var m = l.words,
                t = l.sigBytes,
                x = this._map;
            l.clamp();
            l = [];
            for (var w = 0; w < t; w += 5) {
                for (var h = [], a = 0; 5 > a; a++) h[a] = m[w + a >>> 2] >>> 24 - (w + a) % 4 * 8 & 255;
                h = [h[0] >>> 3 & 31, (h[0] & 7) << 2 | h[1] >>> 6 & 3, h[1] >>> 1 & 31, (h[1] & 1) << 4 | h[2] >>> 4 & 15, (h[2] & 15) << 1 | h[3] >>> 7 & 1, h[3] >>> 2 & 31, (h[3] & 3) << 3 | h[4] >>> 5 & 7, h[4] & 31];
                for (a = 0; 8 > a && w + .625 * a < t; a++) l.push(x.charAt(h[a]))
            }
            if (m = x.charAt(32))
                for (; l.length % 8;) l.push(m);
            return l.join("")
        }, parse: function (m) {
            return l.create()
        }, _map: "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"
    }
})();
(function (m, l, t) {
    "undefined" !== typeof module && module.exports ? module.exports = t() : l[m] = t()
})("JDDMAC", this, function () {
    var m = function () {
            return "00000000 77073096 EE0E612C 990951BA 076DC419 706AF48F E963A535 9E6495A3 0EDB8832 79DCB8A4 E0D5E91E 97D2D988 09B64C2B 7EB17CBD E7B82D07 90BF1D91 1DB71064 6AB020F2 F3B97148 84BE41DE 1ADAD47D 6DDDE4EB F4D4B551 83D385C7 136C9856 646BA8C0 FD62F97A 8A65C9EC 14015C4F 63066CD9 FA0F3D63 8D080DF5 3B6E20C8 4C69105E D56041E4 A2677172 3C03E4D1 4B04D447 D20D85FD A50AB56B 35B5A8FA 42B2986C DBBBC9D6 ACBCF940 32D86CE3 45DF5C75 DCD60DCF ABD13D59 26D930AC 51DE003A C8D75180 BFD06116 21B4F4B5 56B3C423 CFBA9599 B8BDA50F 2802B89E 5F058808 C60CD9B2 B10BE924 2F6F7C87 58684C11 C1611DAB B6662D3D 76DC4190 01DB7106 98D220BC EFD5102A 71B18589 06B6B51F 9FBFE4A5 E8B8D433 7807C9A2 0F00F934 9609A88E E10E9818 7F6A0DBB 086D3D2D 91646C97 E6635C01 6B6B51F4 1C6C6162 856530D8 F262004E 6C0695ED 1B01A57B 8208F4C1 F50FC457 65B0D9C6 12B7E950 8BBEB8EA FCB9887C 62DD1DDF 15DA2D49 8CD37CF3 FBD44C65 4DB26158 3AB551CE A3BC0074 D4BB30E2 4ADFA541 3DD895D7 A4D1C46D D3D6F4FB 4369E96A 346ED9FC AD678846 DA60B8D0 44042D73 33031DE5 AA0A4C5F DD0D7CC9 5005713C 270241AA BE0B1010 C90C2086 5768B525 206F85B3 B966D409 CE61E49F 5EDEF90E 29D9C998 B0D09822 C7D7A8B4 59B33D17 2EB40D81 B7BD5C3B C0BA6CAD EDB88320 9ABFB3B6 03B6E20C 74B1D29A EAD54739 9DD277AF 04DB2615 73DC1683 E3630B12 94643B84 0D6D6A3E 7A6A5AA8 E40ECF0B 9309FF9D 0A00AE27 7D079EB1 F00F9344 8708A3D2 1E01F268 6906C2FE F762575D 806567CB 196C3671 6E6B06E7 FED41B76 89D32BE0 10DA7A5A 67DD4ACC F9B9DF6F 8EBEEFF9 17B7BE43 60B08ED5 D6D6A3E8 A1D1937E 38D8C2C4 4FDFF252 D1BB67F1 A6BC5767 3FB506DD 48B2364B D80D2BDA AF0A1B4C 36034AF6 41047A60 DF60EFC3 A867DF55 316E8EEF 4669BE79 CB61B38C BC66831A 256FD2A0 5268E236 CC0C7795 BB0B4703 220216B9 5505262F C5BA3BBE B2BD0B28 2BB45A92 5CB36A04 C2D7FFA7 B5D0CF31 2CD99E8B 5BDEAE1D 9B64C2B0 EC63F226 756AA39C 026D930A 9C0906A9 EB0E363F 72076785 05005713 95BF4A82 E2B87A14 7BB12BAE 0CB61B38 92D28E9B E5D5BE0D 7CDCEFB7 0BDBDF21 86D3D2D4 F1D4E242 68DDB3F8 1FDA836E 81BE16CD F6B9265B 6FB077E1 18B74777 88085AE6 FF0F6A70 66063BCA 11010B5C 8F659EFF F862AE69 616BFFD3 166CCF45 A00AE278 D70DD2EE 4E048354 3903B3C2 A7672661 D06016F7 4969474D 3E6E77DB AED16A4A D9D65ADC 40DF0B66 37D83BF0 A9BCAE53 DEBB9EC5 47B2CF7F 30B5FFE9 BDBDF21C CABAC28A 53B39330 24B4A3A6 BAD03605 CDD70693 54DE5729 23D967BF B3667A2E C4614AB8 5D681B02 2A6F2B94 B40BBE37 C30C8EA1 5A05DF1B 2D02EF8D".split(" ").map(function (l) {
                return parseInt(l, 16)
            })
        }(),
        l = function () {};
    l.prototype = {
        mac: function (l) {
            for (var t = -1, v = 0, x = l.length; v < x; v++) t = t >>> 8 ^ m[(t ^ l.charCodeAt(v)) & 255];
            return (t ^ -1) >>> 0
        }
    };
    return l
});
var _CurrentPageProtocol = "https:" == document.location.protocol ? "https://" : "http://",
    _JdJrTdRiskDomainName = window.__fp_domain || "gia.jd.com",
    _url_query_str = "",
    _root_domain = "",
    _CurrentPageUrl = function () {
        var m = document.location.href.toString();
        try {
            _root_domain = /^https?:\/\/(?:\w+\.)*?(\w*\.(?:com\.cn|cn|com|net|id))[\\\/]*/.exec(m)[1]
        } catch (t) {}
        var l = m.indexOf("?");
        0 < l && (_url_query_str = m.substring(l + 1), 500 < _url_query_str.length && (_url_query_str = _url_query_str.substring(0, 499)), m = m.substring(0, l));
        return m = m.substring(_CurrentPageProtocol.length)
    }(),
    jd_shadow__ = function () {
        try {
            var m = JDDSecCryptoJS,
                l = [];
            l.push(_CurrentPageUrl);
            var t = m.lib.UUID.generateUuid();
            l.push(t);
            var u = (new Date).getTime();
            l.push(u);
            var v = m.SHA1(l.join("")).toString().toUpperCase();
            l = [];
            l.push("JD3");
            l.push(v);
            var x = (new JDDMAC).mac(l.join(""));
            l.push(x);
            var w = m.enc.Hex.parse("30313233343536373839616263646566"),
                h = m.enc.Hex.parse("4c5751554935255042304e6458323365"),
                a = l.join("");
            return m.AES.encrypt(m.enc.Utf8.parse(a), h, {
                mode: m.mode.CBC,
                padding: m.pad.Pkcs7,
                iv: w
            }).ciphertext.toString(m.enc.Base32)
        } catch (f) {}
    }(),
    td_collect = new function () {
        function m() {
            var h = window.webkitRTCPeerConnection || window.mozRTCPeerConnection || window.RTCPeerConnection;
            if (h) {
                var a = function (a) {
                        var g = /([0-9]{1,3}(\.[0-9]{1,3}){3})/,
                            k = /\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*/;
                        try {
                            var b = g.exec(a);
                            if (null == b || 0 == b.length || void 0 == b) b = k.exec(a);
                            var c = b[1];
                            void 0 === f[c] && v.push(c);
                            f[c] = !0
                        } catch (e) {}
                    },
                    f = {};
                try {
                    var n = new h({
                        iceServers: [{
                            url: "stun:stun.services.mozilla.com"
                        }]
                    })
                } catch (k) {}
                try {
                    void 0 === n && (n = new h({
                        iceServers: []
                    }))
                } catch (k) {}
                if (n || window.mozRTCPeerConnection) try {
                    n.createDataChannel("chat", {
                        reliable: !1
                    })
                } catch (k) {}
                n && (n.onicecandidate = function (f) {
                    f.candidate && a(f.candidate.candidate)
                }, n.createOffer(function (a) {
                    n.setLocalDescription(a, function () {}, function () {})
                }, function () {}), setTimeout(function () {
                    try {
                        n.localDescription.sdp.split("\n").forEach(function (f) {
                            0 === f.indexOf("a=candidate:") && a(f)
                        })
                    } catch (k) {}
                }, 800))
            }
        }

        function l(h) {
            var a;
            return (a = document.cookie.match(new RegExp("(^| )" + h + "=([^;]*)(;|$)"))) ? a[2] : ""
        }

        function t() {
            function h(a) {
                var f = {};
                n.style.fontFamily = a;
                document.body.appendChild(n);
                f.height = n.offsetHeight;
                f.width = n.offsetWidth;
                document.body.removeChild(n);
                return f
            }
            var a = ["monospace", "sans-serif", "serif"],
                f = [],
                n = document.createElement("span");
            n.style.fontSize = "72px";
            n.style.visibility = "hidden";
            n.innerHTML = "mmmmmmmmmmlli";
            for (var k = 0; k < a.length; k++) f[k] = h(a[k]);
            this.checkSupportFont = function (g) {
                for (var k = 0; k < f.length; k++) {
                    var b = h(g + "," + a[k]),
                        c = f[k];
                    if (b.height !== c.height || b.width !== c.width) return !0
                }
                return !1
            }
        }

        function u(h) {
            var a = {};
            a.name = h.name;
            a.filename = h.filename.toLowerCase();
            a.description = h.description;
            void 0 !== h.version && (a.version = h.version);
            a.mimeTypes = [];
            for (var f = 0; f < h.length; f++) {
                var n = h[f],
                    k = {};
                k.description = n.description;
                k.suffixes = n.suffixes;
                k.type = n.type;
                a.mimeTypes.push(k)
            }
            return a
        }
        this.bizId = "";
        this.bioConfig = {
            type: "42",
            operation: 1,
            duraTime: 2,
            interval: 50
        };
        this.worder = null;
        this.deviceInfo = {
            userAgent: "",
            isJdApp: !1,
            isJrApp: !1,
            sdkToken: "",
            fp: "",
            eid: ""
        };
        this.isRpTok = !1;
        this.obtainLocal = function (h) {
            h = "undefined" !== typeof h && h ? !0 : !1;
            var a = {};
            try {
                var f = document.cookie.replace(/(?:(?:^|.*;\s*)3AB9D23F7A4B3C9B\s*=\s*([^;]*).*$)|^.*$/, "$1");
                0 !== f.length && (a.cookie = f)
            } catch (k) {}
            try {
                window.localStorage && null !== window.localStorage && 0 !== window.localStorage.length && (a.localStorage = window.localStorage.getItem("3AB9D23F7A4B3C9B"))
            } catch (k) {}
            try {
                window.sessionStorage && null !== window.sessionStorage && (a.sessionStorage = window.sessionStorage["3AB9D23F7A4B3C9B"])
            } catch (k) {}
            try {
                p.globalStorage && (a.globalStorage = window.globalStorage[".localdomain"]["3AB9D23F7A4B3C9B"])
            } catch (k) {}
            try {
                d && "function" == typeof d.load && "function" == typeof d.getAttribute && (d.load("jdgia_user_data"), a.userData = d.getAttribute("3AB9D23F7A4B3C9B"))
            } catch (k) {}
            try {
                E.indexedDbId && (a.indexedDb = E.indexedDbId)
            } catch (k) {}
            try {
                E.webDbId && (a.webDb = E.webDbId)
            } catch (k) {}
            try {
                for (var n in a)
                    if (32 < a[n].length) {
                        _JdEid = a[n];
                        h || (_eidFlag = !0);
                        break
                    }
            } catch (k) {}
            try {
                ("undefined" === typeof _JdEid || 0 >= _JdEid.length) && this.db("3AB9D23F7A4B3C9B");
                if ("undefined" === typeof _JdEid || 0 >= _JdEid.length) _JdEid = l("3AB9D23F7A4B3C9B");
                if ("undefined" === typeof _JdEid || 0 >= _JdEid.length) _eidFlag = !0
            } catch (k) {}
            return _JdEid
        };
        var v = [],
            x = "Abadi MT Condensed Light;Adobe Fangsong Std;Adobe Hebrew;Adobe Ming Std;Agency FB;Arab;Arabic Typesetting;Arial Black;Batang;Bauhaus 93;Bell MT;Bitstream Vera Serif;Bodoni MT;Bookman Old Style;Braggadocio;Broadway;Calibri;Californian FB;Castellar;Casual;Centaur;Century Gothic;Chalkduster;Colonna MT;Copperplate Gothic Light;DejaVu LGC Sans Mono;Desdemona;DFKai-SB;Dotum;Engravers MT;Eras Bold ITC;Eurostile;FangSong;Forte;Franklin Gothic Heavy;French Script MT;Gabriola;Gigi;Gisha;Goudy Old Style;Gulim;GungSeo;Haettenschweiler;Harrington;Hiragino Sans GB;Impact;Informal Roman;KacstOne;Kino MT;Kozuka Gothic Pr6N;Lohit Gujarati;Loma;Lucida Bright;Lucida Fax;Magneto;Malgun Gothic;Matura MT Script Capitals;Menlo;MingLiU-ExtB;MoolBoran;MS PMincho;MS Reference Sans Serif;News Gothic MT;Niagara Solid;Nyala;Palace Script MT;Papyrus;Perpetua;Playbill;PMingLiU;Rachana;Rockwell;Sawasdee;Script MT Bold;Segoe Print;Showcard Gothic;SimHei;Snap ITC;TlwgMono;Tw Cen MT Condensed Extra Bold;Ubuntu;Umpush;Univers;Utopia;Vladimir Script;Wide Latin".split(";"),
            w = "4game;AdblockPlugin;AdobeExManCCDetect;AdobeExManDetect;Alawar NPAPI utils;Aliedit Plug-In;Alipay Security Control 3;AliSSOLogin plugin;AmazonMP3DownloaderPlugin;AOL Media Playback Plugin;AppUp;ArchiCAD;AVG SiteSafety plugin;Babylon ToolBar;Battlelog Game Launcher;BitCometAgent;Bitdefender QuickScan;BlueStacks Install Detector;CatalinaGroup Update;Citrix ICA Client;Citrix online plug-in;Citrix Receiver Plug-in;Coowon Update;DealPlyLive Update;Default Browser Helper;DivX Browser Plug-In;DivX Plus Web Player;DivX VOD Helper Plug-in;doubleTwist Web Plugin;Downloaders plugin;downloadUpdater;eMusicPlugin DLM6;ESN Launch Mozilla Plugin;ESN Sonar API;Exif Everywhere;Facebook Plugin;File Downloader Plug-in;FileLab plugin;FlyOrDie Games Plugin;Folx 3 Browser Plugin;FUZEShare;GDL Object Web Plug-in 16.00;GFACE Plugin;Ginger;Gnome Shell Integration;Google Earth Plugin;Google Earth Plug-in;Google Gears 0.5.33.0;Google Talk Effects Plugin;Google Update;Harmony Firefox Plugin;Harmony Plug-In;Heroes & Generals live;HPDetect;Html5 location provider;IE Tab plugin;iGetterScriptablePlugin;iMesh plugin;Kaspersky Password Manager;LastPass;LogMeIn Plugin 1.0.0.935;LogMeIn Plugin 1.0.0.961;Ma-Config.com plugin;Microsoft Office 2013;MinibarPlugin;Native Client;Nitro PDF Plug-In;Nokia Suite Enabler Plugin;Norton Identity Safe;npAPI Plugin;NPLastPass;NPPlayerShell;npTongbuAddin;NyxLauncher;Octoshape Streaming Services;Online Storage plug-in;Orbit Downloader;Pando Web Plugin;Parom.TV player plugin;PDF integrado do WebKit;PDF-XChange Viewer;PhotoCenterPlugin1.1.2.2;Picasa;PlayOn Plug-in;QQ2013 Firefox Plugin;QQDownload Plugin;QQMiniDL Plugin;QQMusic;RealDownloader Plugin;Roblox Launcher Plugin;RockMelt Update;Safer Update;SafeSearch;Scripting.Dictionary;SefClient Plugin;Shell.UIHelper;Silverlight Plug-In;Simple Pass;Skype Web Plugin;SumatraPDF Browser Plugin;Symantec PKI Client;Tencent FTN plug-in;Thunder DapCtrl NPAPI Plugin;TorchHelper;Unity Player;Uplay PC;VDownloader;Veetle TV Core;VLC Multimedia Plugin;Web Components;WebKit-integrierte PDF;WEBZEN Browser Extension;Wolfram Mathematica;WordCaptureX;WPI Detector 1.4;Yandex Media Plugin;Yandex PDF Viewer;YouTube Plug-in;zako".split(";");
        this.toJson = "object" === typeof JSON && JSON.stringify;
        this.init = function () {
            _fingerprint_step = 6;
            m();
            _fingerprint_step = 7;
            "function" !== typeof this.toJson && (this.toJson = function (h) {
                var a = typeof h;
                if ("undefined" === a || null === h) return "null";
                if ("number" === a || "boolean" === a) return h + "";
                if ("object" === a && h && h.constructor === Array) {
                    a = [];
                    for (var f = 0; h.length > f; f++) a.push(this.toJson(h[f]));
                    return "[" + (a + "") + "]"
                }
                if ("object" === a) {
                    a = [];
                    for (f in h) h.hasOwnProperty(f) && a.push('"' + f + '":' + this.toJson(h[f]));
                    return "{" + (a + "") + "}"
                }
            });
            this.sdkCollectInit()
        };
        this.sdkCollectInit = function () {
            try {
                try {
                    bp_bizid && (this.bizId = bp_bizid)
                } catch (f) {
                    this.bizId = "jsDefault"
                }
                var h = navigator.userAgent.toLowerCase(),
                    a = !h.match(/(iphone|ipad|ipod)/i) && (-1 < h.indexOf("android") || -1 < h.indexOf("adr"));
                this.deviceInfo.isJdApp = -1 < h.indexOf("jdapp");
                this.deviceInfo.isJrApp = -1 < h.indexOf("jdjr");
                this.deviceInfo.userAgent = navigator.userAgent;
                this.deviceInfo.isAndroid = a;
                this.createWorker()
            } catch (f) {}
        };
        this.db = function (h, a) {
            try {
                _fingerprint_step = "m";
                if (window.openDatabase) {
                    var f = window.openDatabase("sqlite_jdtdstorage", "", "jdtdstorage", 1048576);
                    void 0 !== a && "" != a ? f.transaction(function (f) {
                        f.executeSql("CREATE TABLE IF NOT EXISTS cache(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, value TEXT NOT NULL, UNIQUE (name))", [], function (a, f) {}, function (a, f) {});
                        f.executeSql("INSERT OR REPLACE INTO cache(name, value) VALUES(?, ?)", [h, a], function (a, f) {}, function (a, f) {})
                    }) : f.transaction(function (a) {
                        a.executeSql("SELECT value FROM cache WHERE name=?", [h], function (a, f) {
                            1 <= f.rows.length && (_JdEid = f.rows.item(0).value)
                        }, function (a, f) {})
                    })
                }
                _fingerprint_step = "n"
            } catch (n) {}
        };
        this.setCookie = function (h, a) {
            void 0 !== a && "" != a && (document.cookie = h + "=" + a + "; expires=Tue, 31 Dec 2030 00:00:00 UTC; path=/; domain=" + _root_domain)
        };
        this.tdencrypt = function (h) {
            h = this.toJson(h);
            h = encodeURIComponent(h);
            var a = "",
                f = 0;
            do {
                var n = h.charCodeAt(f++);
                var k = h.charCodeAt(f++);
                var g = h.charCodeAt(f++);
                var l = n >> 2;
                n = (n & 3) << 4 | k >> 4;
                var b = (k & 15) << 2 | g >> 6;
                var c = g & 63;
                isNaN(k) ? b = c = 64 : isNaN(g) && (c = 64);
                a = a + "23IL<N01c7KvwZO56RSTAfghiFyzWJqVabGH4PQdopUrsCuX*xeBjkltDEmn89.-".charAt(l) + "23IL<N01c7KvwZO56RSTAfghiFyzWJqVabGH4PQdopUrsCuX*xeBjkltDEmn89.-".charAt(n) + "23IL<N01c7KvwZO56RSTAfghiFyzWJqVabGH4PQdopUrsCuX*xeBjkltDEmn89.-".charAt(b) + "23IL<N01c7KvwZO56RSTAfghiFyzWJqVabGH4PQdopUrsCuX*xeBjkltDEmn89.-".charAt(c)
            } while (f < h.length);
            return a + "/"
        };
        this.collect = function () {
            var h = new Date;
            try {
                var a = document.createElement("div"),
                    f = {},
                    n = "ActiveBorder ActiveCaption AppWorkspace Background ButtonFace ButtonHighlight ButtonShadow ButtonText CaptionText GrayText Highlight HighlightText InactiveBorder InactiveCaption InactiveCaptionText InfoBackground InfoText Menu MenuText Scrollbar ThreeDDarkShadow ThreeDFace ThreeDHighlight ThreeDLightShadow ThreeDShadow Window WindowFrame WindowText".split(" ");
                if (window.getComputedStyle)
                    for (var k = 0; k < n.length; k++) document.body.appendChild(a), a.style.color = n[k], f[n[k]] = window.getComputedStyle(a).getPropertyValue("color"), document.body.removeChild(a)
            } catch (B) {}
            a = {
                ca: {},
                ts: {},
                m: {}
            };
            n = a.ca;
            n.tdHash = _jdfp_canvas_md5;
            var g = !1;
            if (k = window.WebGLRenderingContext) k = navigator.userAgent, k = k.toLowerCase(), k = (0 < k.indexOf("jdjr-app") || 0 <= k.indexOf("jdapp")) && (0 < k.indexOf("iphone") || 0 < k.indexOf("ipad")) ? !0 : !1, k = !k;
            if (k) {
                var l = ["webgl", "experimental-webgl", "moz-webgl", "webkit-3d"],
                    b = [],
                    c;
                for (k = 0; k < l.length; k++) try {
                    var e = !1;
                    (e = document.createElement("canvas").getContext(l[k], {
                        stencil: !0
                    })) && e && (c = e, b.push(l[k]))
                } catch (B) {}
                b.length && (g = {
                    name: b,
                    gl: c
                })
            }
            if (g) {
                k = g.gl;
                n.contextName = g.name.join();
                n.webglversion = k.getParameter(k.VERSION);
                n.shadingLV = k.getParameter(k.SHADING_LANGUAGE_VERSION);
                n.vendor = k.getParameter(k.VENDOR);
                n.renderer = k.getParameter(k.RENDERER);
                c = [];
                try {
                    c = k.getSupportedExtensions(), n.extensions = c
                } catch (B) {}
                try {
                    var m = k.getExtension("WEBGL_debug_renderer_info");
                    m && (n.wuv = k.getParameter(m.UNMASKED_VENDOR_WEBGL), n.wur = k.getParameter(m.UNMASKED_RENDERER_WEBGL))
                } catch (B) {}
            }
            a.m.documentMode = document.documentMode;
            a.m.compatMode = document.compatMode;
            m = [];
            n = new t;
            for (k = 0; k < x.length; k++) c = x[k], n.checkSupportFont(c) && m.push(c);
            a.fo = m;
            k = {};
            m = [];
            for (var q in navigator) "object" != typeof navigator[q] && (k[q] = navigator[q]), m.push(q);
            k.enumerationOrder = m;
            k.javaEnabled = navigator.javaEnabled();
            try {
                k.taintEnabled = navigator.taintEnabled()
            } catch (B) {}
            a.n = k;
            k = navigator.userAgent.toLowerCase();
            if (q = k.match(/rv:([\d.]+)\) like gecko/)) var z = q[1];
            if (q = k.match(/msie ([\d.]+)/)) z = q[1];
            q = [];
            if (z)
                for (z = "AcroPDF.PDF;Adodb.Stream;AgControl.AgControl;DevalVRXCtrl.DevalVRXCtrl.1;MacromediaFlashPaper.MacromediaFlashPaper;Msxml2.DOMDocument;Msxml2.XMLHTTP;PDF.PdfCtrl;QuickTime.QuickTime;QuickTimeCheckObject.QuickTimeCheck.1;RealPlayer;RealPlayer.RealPlayer(tm) ActiveX Control (32-bit);RealVideo.RealVideo(tm) ActiveX Control (32-bit);rmocx.RealPlayer G2 Control;Scripting.Dictionary;Shell.UIHelper;ShockwaveFlash.ShockwaveFlash;SWCtl.SWCtl;TDCCtl.TDCCtl;WMPlayer.OCX".split(";"), k = 0; k < z.length; k++) {
                    var A = z[k];
                    try {
                        var y = new ActiveXObject(A);
                        m = {};
                        m.name = A;
                        try {
                            m.version = y.GetVariable("$version")
                        } catch (B) {}
                        try {
                            m.version = y.GetVersions()
                        } catch (B) {}
                        m.version && 0 < m.version.length || (m.version = "");
                        q.push(m)
                    } catch (B) {}
                } else {
                    z = navigator.plugins;
                    m = {};
                    for (k = 0; k < z.length; k++) A = z[k], m[A.name] = 1, q.push(u(A));
                    for (k = 0; k < w.length; k++) y = w[k], m[y] || (A = z[y], A && q.push(u(A)))
                }
            z = "availHeight availWidth colorDepth bufferDepth deviceXDPI deviceYDPI height width logicalXDPI logicalYDPI pixelDepth updateInterval".split(" ");
            A = {};
            for (k = 0; z.length > k; k++) y = z[k], void 0 !== screen[y] && (A[y] = screen[y]);
            z = ["devicePixelRatio", "screenTop", "screenLeft"];
            m = {};
            for (k = 0; z.length > k; k++) y = z[k], void 0 !== window[y] && (m[y] = window[y]);
            a.p = q;
            a.w = m;
            a.s = A;
            a.sc = f;
            a.tz = h.getTimezoneOffset();
            a.lil = v.sort().join("|");
            a.wil = "";
            f = {};
            try {
                f.cookie = navigator.cookieEnabled, f.localStorage = !!window.localStorage, f.sessionStorage = !!window.sessionStorage, f.globalStorage = !!window.globalStorage, f.indexedDB = !!window.indexedDB
            } catch (B) {}
            a.ss = f;
            a.ts.deviceTime = h.getTime();
            a.ts.deviceEndTime = (new Date).getTime();
            return this.tdencrypt(a)
        };
        this.collectSdk = function (h) {
            try {
                var a = this,
                    f = !1,
                    n = a.getLocal("BATQW722QTLYVCRD");
                if (null != n && void 0 != n && "" != n) try {
                    var k = JSON.parse(n),
                        g = (new Date).getTime();
                    null != k && void 0 != k.t && "number" == typeof k.t && (12E5 >= g - k.t && void 0 != k.tk && null != k.tk && "" != k.tk && k.tk.startsWith("jdd") ? (a.deviceInfo.sdkToken = k.tk, f = !0) : void 0 != k.tk && null != k.tk && "" != k.tk && (a.deviceInfo.sdkToken = k.tk))
                } catch (r) {}
                n = !1;
                a.deviceInfo.isJdApp ? (a.deviceInfo.clientVersion = navigator.userAgent.split(";")[2], (n = 0 < a.compareVersion(a.deviceInfo.clientVersion, "7.0.2")) && !f && a.getJdSdkCacheToken(function (f) {
                    a.deviceInfo.sdkToken = f;
                    null != f && "" != f && f.startsWith("jdd") || a.getJdBioToken(h)
                })) : a.deviceInfo.isJrApp && (a.deviceInfo.clientVersion = navigator.userAgent.match(/clientVersion=([^&]*)(&|$)/)[1], (n = 0 < a.compareVersion(a.deviceInfo.clientVersion, "4.6.0")) && !f && a.getJdJrSdkCacheToken(function (f) {
                    a.deviceInfo.sdkToken = f;
                    null != f && "" != f && f.startsWith("jdd") || a.getJdJrBioToken(h)
                }));
                "function" == typeof h && h(a.deviceInfo)
            } catch (r) {}
        };
        this.compareVersion = function (h, a) {
            try {
                if (h === a) return 0;
                var f = h.split(".");
                var n = a.split(".");
                for (h = 0; h < f.length; h++) {
                    var k = parseInt(f[h]);
                    if (!n[h]) return 1;
                    var g = parseInt(n[h]);
                    if (k < g) break;
                    if (k > g) return 1
                }
            } catch (r) {}
            return -1
        };
        this.isWKWebView = function () {
            return this.deviceInfo.userAgent.match(/supportJDSHWK/i) || 1 == window._is_jdsh_wkwebview ? !0 : !1
        };
        this.getErrorToken = function (h) {
            try {
                if (h) {
                    var a = (h + "").match(/"token":"(.*?)"/);
                    if (a && 1 < a.length) return a[1]
                }
            } catch (f) {}
            return ""
        };
        this.getJdJrBioToken = function (h) {
            var a = this;
            "undefined" != typeof JrBridge && null != JrBridge && "undefined" != typeof JrBridge._version && (0 > a.compareVersion(JrBridge._version, "2.0.0") ? console.error("\u6865\u7248\u672c\u4f4e\u4e8e2.0\u4e0d\u652f\u6301bio") : JrBridge.callNative({
                type: a.bioConfig.type,
                operation: a.bioConfig.operation,
                biometricData: {
                    bizId: a.bizId,
                    duraTime: a.bioConfig.duraTime,
                    interval: a.bioConfig.interval
                }
            }, function (f) {
                try {
                    "object" != typeof f && (f = JSON.parse(f)), a.deviceInfo.sdkToken = f.token
                } catch (n) {
                    console.error(n)
                }
                null != a.deviceInfo.sdkToken && "" != a.deviceInfo.sdkToken && (f = {
                    tk: a.deviceInfo.sdkToken,
                    t: (new Date).getTime()
                }, a.store("BATQW722QTLYVCRD", JSON.stringify(f)))
            }))
        };
        this.getJdJrSdkCacheToken = function (h) {
            var a = this;
            try {
                "undefined" == typeof JrBridge || null == JrBridge || "undefined" == typeof JrBridge._version || 0 > a.compareVersion(JrBridge._version, "2.0.0") || JrBridge.callNative({
                    type: a.bioConfig.type,
                    operation: 5,
                    biometricData: {
                        bizId: a.bizId,
                        duraTime: a.bioConfig.duraTime,
                        interval: a.bioConfig.interval
                    }
                }, function (f) {
                    var n = "";
                    try {
                        "object" != typeof f && (f = JSON.parse(f)), n = f.token
                    } catch (k) {
                        console.error(k)
                    }
                    null != n && "" != n && "function" == typeof h && (h(n), n.startsWith("jdd") && (f = {
                        tk: n,
                        t: (new Date).getTime()
                    }, a.store("BATQW722QTLYVCRD", JSON.stringify(f))))
                })
            } catch (f) {}
        };
        this.getJdBioToken = function (h) {
            var a = this;
            h = JSON.stringify({
                businessType: "bridgeBiologicalProbe",
                callBackName: "_bioDeviceCb",
                params: {
                    pin: "",
                    jsonData: {
                        type: a.bioConfig.type,
                        operation: a.bioConfig.operation,
                        data: {
                            bizId: a.bizId,
                            duraTime: a.bioConfig.duraTime,
                            interval: a.bioConfig.interval
                        },
                        biometricData: {
                            bizId: a.bizId,
                            duraTime: a.bioConfig.duraTime,
                            interval: a.bioConfig.interval
                        }
                    }
                }
            });
            a.isWKWebView() ? window.webkit.messageHandlers.JDAppUnite.postMessage({
                method: "notifyMessageToNative",
                params: h
            }) : window.JDAppUnite && window.JDAppUnite.notifyMessageToNative(h);
            window._bioDeviceCb = function (f) {
                try {
                    var h = "object" == typeof f ? f : JSON.parse(f);
                    if (void 0 != h && null != h && "0" != h.status) return;
                    null != h.data.token && void 0 != h.data.token && "" != h.data.token && (a.deviceInfo.sdkToken = h.data.token)
                } catch (k) {
                    f = a.getErrorToken(f), null != f && "" != f && (a.deviceInfo.sdkToken = f)
                }
                null != a.deviceInfo.sdkToken && "" != a.deviceInfo.sdkToken && (f = {
                    tk: a.deviceInfo.sdkToken,
                    t: (new Date).getTime()
                }, a.store("BATQW722QTLYVCRD", JSON.stringify(f)))
            }
        };
        this.getJdSdkCacheToken = function (h) {
            try {
                var a = this,
                    f = JSON.stringify({
                        businessType: "bridgeBiologicalProbe",
                        callBackName: "_bioDeviceSdkCacheCb",
                        params: {
                            pin: "",
                            jsonData: {
                                type: a.bioConfig.type,
                                operation: 5,
                                data: {
                                    bizId: a.bizId,
                                    duraTime: a.bioConfig.duraTime,
                                    interval: a.bioConfig.interval
                                },
                                biometricData: {
                                    bizId: a.bizId,
                                    duraTime: a.bioConfig.duraTime,
                                    interval: a.bioConfig.interval
                                }
                            }
                        }
                    });
                a.isWKWebView() ? window.webkit.messageHandlers.JDAppUnite.postMessage({
                    method: "notifyMessageToNative",
                    params: f
                }) : window.JDAppUnite && window.JDAppUnite.notifyMessageToNative(f);
                window._bioDeviceSdkCacheCb = function (f) {
                    var k = "";
                    try {
                        var g = "object" == typeof f ? f : JSON.parse(f);
                        if (void 0 != g && null != g && "0" != g.status) return;
                        k = g.data.token
                    } catch (r) {
                        k = a.getErrorToken(f)
                    }
                    null != k && "" != k && "function" == typeof h && (h(k), k.startsWith("jdd") && (f = {
                        tk: k,
                        t: (new Date).getTime()
                    }, a.store("BATQW722QTLYVCRD", JSON.stringify(f))))
                }
            } catch (n) {}
        };
        this.store = function (h, a) {
            try {
                this.setCookie(h, a)
            } catch (f) {}
            try {
                window.localStorage && window.localStorage.setItem(h, a)
            } catch (f) {}
            try {
                window.sessionStorage && window.sessionStorage.setItem(h, a)
            } catch (f) {}
            try {
                window.globalStorage && window.globalStorage[".localdomain"].setItem(h, a)
            } catch (f) {}
            try {
                this.db(h, _JdEid)
            } catch (f) {}
        };
        this.getLocal = function (h) {
            var a = {},
                f = null;
            try {
                var n = document.cookie.replace(new RegExp("(?:(?:^|.*;\\s*)" + h + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1");
                0 !== n.length && (a.cookie = n)
            } catch (g) {}
            try {
                window.localStorage && null !== window.localStorage && 0 !== window.localStorage.length && (a.localStorage = window.localStorage.getItem(h))
            } catch (g) {}
            try {
                window.sessionStorage && null !== window.sessionStorage && (a.sessionStorage = window.sessionStorage[h])
            } catch (g) {}
            try {
                p.globalStorage && (a.globalStorage = window.globalStorage[".localdomain"][h])
            } catch (g) {}
            try {
                d && "function" == typeof d.load && "function" == typeof d.getAttribute && (d.load("jdgia_user_data"), a.userData = d.getAttribute(h))
            } catch (g) {}
            try {
                E.indexedDbId && (a.indexedDb = E.indexedDbId)
            } catch (g) {}
            try {
                E.webDbId && (a.webDb = E.webDbId)
            } catch (g) {}
            try {
                for (var k in a)
                    if (32 < a[k].length) {
                        f = a[k];
                        break
                    }
            } catch (g) {}
            try {
                if (null == f || "undefined" === typeof f || 0 >= f.length) f = l(h)
            } catch (g) {}
            return f
        };
        this.createWorker = function () {
            if (window.Worker) {
                try {
                    var h = new Blob(["onmessage = function (event) {\n    var data = JSON.parse(event.data);\n    try {\n        var httpRequest;\n        try {\n            httpRequest = new XMLHttpRequest();\n        } catch (h) {}\n        if (!httpRequest)\n            try {\n                httpRequest = new (window['ActiveXObject'])('Microsoft.XMLHTTP')\n            } catch (l) {}\n        if (!httpRequest)\n            try {\n                httpRequest = new (window['ActiveXObject'])('Msxml2.XMLHTTP')\n            } catch (r) {}\n        if (!httpRequest)\n            try {\n                httpRequest = new (window['ActiveXObject'])('Msxml3.XMLHTTP')\n            } catch (n) {}\n\n        if(data){\n            httpRequest['open']('POST', data.url, false);\n            httpRequest['setRequestHeader']('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');\n            httpRequest['onreadystatechange'] = function () {\n                if (4 === httpRequest['readyState'] && 200 === httpRequest['status']) {\n                    postMessage(httpRequest.responseText);\n                }\n            };\n            httpRequest['send'](data.data);\n        }\n\n    }catch (e){console.error(e);}\n};"], {
                        type: "application/javascript"
                    })
                } catch (a) {
                    window.BlobBuilder = window.BlobBuilder || window.WebKitBlobBuilder || window.MozBlobBuilder, h = new BlobBuilder, h.append("onmessage = function (event) {\n    var data = JSON.parse(event.data);\n    try {\n        var httpRequest;\n        try {\n            httpRequest = new XMLHttpRequest();\n        } catch (h) {}\n        if (!httpRequest)\n            try {\n                httpRequest = new (window['ActiveXObject'])('Microsoft.XMLHTTP')\n            } catch (l) {}\n        if (!httpRequest)\n            try {\n                httpRequest = new (window['ActiveXObject'])('Msxml2.XMLHTTP')\n            } catch (r) {}\n        if (!httpRequest)\n            try {\n                httpRequest = new (window['ActiveXObject'])('Msxml3.XMLHTTP')\n            } catch (n) {}\n\n        if(data){\n            httpRequest['open']('POST', data.url, false);\n            httpRequest['setRequestHeader']('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');\n            httpRequest['onreadystatechange'] = function () {\n                if (4 === httpRequest['readyState'] && 200 === httpRequest['status']) {\n                    postMessage(httpRequest.responseText);\n                }\n            };\n            httpRequest['send'](data.data);\n        }\n\n    }catch (e){console.error(e);}\n};"), h = h.getBlob()
                }
                try {
                    this.worker = new Worker(URL.createObjectURL(h))
                } catch (a) {}
            }
        };
        this.reportWorker = function (h, a, f, l) {
            try {
                null != this.worker && (this.worker.postMessage(JSON.stringify({
                    url: h,
                    data: a,
                    success: !1,
                    async: !1
                })), this.worker.onmessage = function (a) {})
            } catch (k) {}
        }
    };

function td_collect_exe() {
    _fingerprint_step = 8;
    var m = td_collect.collect();
    td_collect.collectSdk();
    var l = "string" === typeof orderId ? orderId : "",
        t = "undefined" !== typeof jdfp_pinenp_ext && jdfp_pinenp_ext ? 2 : 1;
    l = {
        pin: _jdJrTdCommonsObtainPin(t),
        oid: l,
        p: "https:" == document.location.protocol ? "s" : "h",
        fp: risk_jd_local_fingerprint,
        ctype: t,
        v: "2.7.9.4",
        f: "3"
    };
    try {
        l.o = _CurrentPageUrl, l.qs = _url_query_str
    } catch (v) {}
    _fingerprint_step = 9;
    0 >= _JdEid.length && (_JdEid = td_collect.obtainLocal(), 0 < _JdEid.length && (_eidFlag = !0));
    l.fc = _JdEid;
    try {
        l.t = jd_risk_token_id
    } catch (v) {}
    try {
        if ("undefined" != typeof gia_fp_qd_uuid && 0 <= gia_fp_qd_uuid.length) l.qi = gia_fp_qd_uuid;
        else {
            var u = _JdJrRiskClientStorage.jdtdstorage_cookie("qd_uid");
            l.qi = void 0 == u ? "" : u
        }
    } catch (v) {}
    "undefined" != typeof jd_shadow__ && 0 < jd_shadow__.length && (l.jtb = jd_shadow__);
    try {
        td_collect.deviceInfo && void 0 != td_collect.deviceInfo && null != td_collect.deviceInfo.sdkToken && "" != td_collect.deviceInfo.sdkToken ? (l.stk = td_collect.deviceInfo.sdkToken, td_collect.isRpTok = !0) : td_collect.isRpTok = !1
    } catch (v) {
        td_collect.isRpTok = !1
    }
    u = td_collect.tdencrypt(l);
    _fingerprint_step = "a";
    jdJrTdsendCorsRequest(_CurrentPageProtocol + _JdJrTdRiskDomainName + "/fcf.html?a=" + u, "d=" + m, function (l) {
        _fingerprint_step = "e";
        var m = l;
        0 < l.indexOf("*_*") && (m = l.split("*_*", 2), l = JSON.parse(m[1]), m = l.eid, _jd_e_joint_ = l);
        if (32 < m.length && 91 >= m.length) {
            (l = 0 < m.indexOf("jd_risk_")) || (_JdEid = m);
            _eidFlag = !0;
            var t = new Date;
            t.setFullYear(t.getFullYear() + 1E3);
            _fingerprint_step = "f";
            try {
                _jdJrTdRelationEidPin(m), _fingerprint_step = "g", l || (td_collect.setCookie("3AB9D23F7A4B3C9B", m), _fingerprint_step = "h")
            } catch (h) {}
            try {
                window.localStorage && (window.localStorage.setItem("3AB9D23F7A4B3C9B", m), _fingerprint_step = "i")
            } catch (h) {}
            try {
                window.sessionStorage && (window.sessionStorage.setItem("3AB9D23F7A4B3C9B", m), _fingerprint_step = "j")
            } catch (h) {}
            try {
                window.globalStorage && (window.globalStorage[".localdomain"].setItem("3AB9D23F7A4B3C9B", m), _fingerprint_step = "k")
            } catch (h) {}
            try {
                td_collect.db("3AB9D23F7A4B3C9B", _JdEid)
            } catch (h) {}
        }
    }, !1)
}

function getJdEid() {
    _JdEid = !_JdEid || 120 < _JdEid.length ? "" : _JdEid;
    var m = {
            eid: _JdEid,
            fp: risk_jd_local_fingerprint,
            sdkToken: ""
        },
        l = "";
    try {
        "" == _JdEid && (l = "a", m.eid = td_collect.obtainLocal(!0), l = "b"), m.token = jd_risk_token_id, m.jstub = jd_shadow__
    } catch (u) {} finally {
        td_collect.deviceInfo.eid = m.eid, td_collect.deviceInfo.fp = m.fp
    }
    if (void 0 === m.eid || "" == m.eid) m.fpstep = _fingerprint_step + l + "" + start_time;
    try {
        if (m.sdkToken = td_collect.deviceInfo.sdkToken, null != td_collect.deviceInfo && void 0 != td_collect.deviceInfo && !td_collect.isRpTok && null != td_collect.deviceInfo.sdkToken && "" != td_collect.deviceInfo.sdkToken) {
            var t = td_collect.tdencrypt({
                fc: m.eid,
                stk: td_collect.deviceInfo.sdkToken
            });
            td_collect.reportWorker(_CurrentPageProtocol + _JdJrTdRiskDomainName + "/ek.html", "a=" + t);
            td_collect.isRpTok = !0
        }
    } catch (u) {}
    return m
}

function getEidJoint() {
    var m = void 0;
    try {
        void 0 == _jd_e_joint_ ? m = getJdEid() : (_jd_e_joint_.fp = risk_jd_local_fingerprint, _jd_e_joint_.token = jd_risk_token_id, m = _jd_e_joint_)
    } catch (l) {}
    return m
}(function () {
    (new JdJrTdRiskFinger).get(function (m) {
        risk_jd_local_fingerprint = m;
        if (0 >= _JdEid.length || !_eidFlag) _JdEid = td_collect.obtainLocal(), 0 >= _JdEid.length && (_eidFlag = !0)
    });
    _fingerprint_step = 5;
    td_collect.init();
    try {
        td_collect_exe()
    } catch (m) {}
})();

function jdJrTdsendCorsRequest(m, l, t, u) {
    try {
        _fingerprint_step = "b";
        try {
            var v = new window.XMLHttpRequest
        } catch (x) {}
        if (!v) try {
            v = new window.ActiveXObject("Microsoft.XMLHTTP")
        } catch (x) {}
        if (!v) try {
            v = new window.ActiveXObject("Msxml2.XMLHTTP")
        } catch (x) {}
        if (!v) try {
            v = new window.ActiveXObject("Msxml3.XMLHTTP")
        } catch (x) {}
        _fingerprint_step = "c";
        v.open("POST", m, !0);
        v.timeout = 1500;
        v.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        v.onreadystatechange = function () {
            4 === v.readyState && 200 === v.status && t && t(v.responseText)
        };
        v.send(l);
        _fingerprint_step = "d"
    } catch (x) {}
}

function JdJrTdFingerDataStream(m, l, t) {
    if ("undefined" !== typeof m && 0 != m)
        if (void 0 === l && (l = 1), void 0 === t && (t = 15), 0 >= _JdEid.length && l < t) setTimeout(function () {
            JdJrTdFingerDataStream(m, l, t)
        }, 20 * l), l++;
        else {
            if ("undefined" !== typeof jd_risk_token_id && 0 < _JdEid.length && 0 < risk_jd_local_fingerprint.length) {
                var u = _jdJrTdCommonsObtainPin("undefined" !== typeof jdfp_pinenp_ext && jdfp_pinenp_ext ? 2 : 1);
                0 < u.length && (u = {
                    p: u,
                    fp: risk_jd_local_fingerprint,
                    e: _JdEid,
                    ct: (new Date).getTime(),
                    t: jd_risk_token_id,
                    opt: m
                }, jdJrTdsendCorsRequest(_CurrentPageProtocol + _JdJrTdRiskDomainName + "/stream.html", "c=" + td_collect.tdencrypt(u)))
            }
        } else throw Error("sourceCode can not be null.");
}

function _jdJrTdRelationEidPin(m) {
    try {
        if (32 <= m.length) {
            var l = _jdJrTdCommonsObtainPin("undefined" !== typeof jdfp_pinenp_ext && jdfp_pinenp_ext ? 2 : 1);
            if (0 < l.length) {
                m = {
                    o: _CurrentPageUrl,
                    p: l,
                    e: m,
                    f: risk_jd_local_fingerprint
                };
                try {
                    m.bizId = _jdtdparam.bizId, m.pvId = _jdtdparam.pvId, m.uvId = _jdtdparam.uvId
                } catch (u) {}
                var t = td_collect.tdencrypt(m);
                jdJrTdsendCorsRequest(_CurrentPageProtocol + _JdJrTdRiskDomainName + "/r.html?v=" + Math.random(), "&d=" + t)
            }
        }
    } catch (u) {}
}

function _jdJrTdCommonsObtainPin(m) {
    var l = "";
    "string" === typeof jd_jr_td_risk_pin && 1 == m ? l = jd_jr_td_risk_pin : "string" === typeof pin ? l = pin : "object" === typeof pin && "string" === typeof jd_jr_td_risk_pin && (l = jd_jr_td_risk_pin);
    return l
};