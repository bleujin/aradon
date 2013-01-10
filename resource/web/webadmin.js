function RGBColor(a) {
    this.ok = !1, a.charAt(0) == "#" && (a = a.substr(1, 6)), a = a.replace(/ /g, ""), a = a.toLowerCase();
    var b = {
        aliceblue: "f0f8ff",
        antiquewhite: "faebd7",
        aqua: "00ffff",
        aquamarine: "7fffd4",
        azure: "f0ffff",
        beige: "f5f5dc",
        bisque: "ffe4c4",
        black: "000000",
        blanchedalmond: "ffebcd",
        blue: "0000ff",
        blueviolet: "8a2be2",
        brown: "a52a2a",
        burlywood: "deb887",
        cadetblue: "5f9ea0",
        chartreuse: "7fff00",
        chocolate: "d2691e",
        coral: "ff7f50",
        cornflowerblue: "6495ed",
        cornsilk: "fff8dc",
        crimson: "dc143c",
        cyan: "00ffff",
        darkblue: "00008b",
        darkcyan: "008b8b",
        darkgoldenrod: "b8860b",
        darkgray: "a9a9a9",
        darkgreen: "006400",
        darkkhaki: "bdb76b",
        darkmagenta: "8b008b",
        darkolivegreen: "556b2f",
        darkorange: "ff8c00",
        darkorchid: "9932cc",
        darkred: "8b0000",
        darksalmon: "e9967a",
        darkseagreen: "8fbc8f",
        darkslateblue: "483d8b",
        darkslategray: "2f4f4f",
        darkturquoise: "00ced1",
        darkviolet: "9400d3",
        deeppink: "ff1493",
        deepskyblue: "00bfff",
        dimgray: "696969",
        dodgerblue: "1e90ff",
        feldspar: "d19275",
        firebrick: "b22222",
        floralwhite: "fffaf0",
        forestgreen: "228b22",
        fuchsia: "ff00ff",
        gainsboro: "dcdcdc",
        ghostwhite: "f8f8ff",
        gold: "ffd700",
        goldenrod: "daa520",
        gray: "808080",
        green: "008000",
        greenyellow: "adff2f",
        honeydew: "f0fff0",
        hotpink: "ff69b4",
        indianred: "cd5c5c",
        indigo: "4b0082",
        ivory: "fffff0",
        khaki: "f0e68c",
        lavender: "e6e6fa",
        lavenderblush: "fff0f5",
        lawngreen: "7cfc00",
        lemonchiffon: "fffacd",
        lightblue: "add8e6",
        lightcoral: "f08080",
        lightcyan: "e0ffff",
        lightgoldenrodyellow: "fafad2",
        lightgrey: "d3d3d3",
        lightgreen: "90ee90",
        lightpink: "ffb6c1",
        lightsalmon: "ffa07a",
        lightseagreen: "20b2aa",
        lightskyblue: "87cefa",
        lightslateblue: "8470ff",
        lightslategray: "778899",
        lightsteelblue: "b0c4de",
        lightyellow: "ffffe0",
        lime: "00ff00",
        limegreen: "32cd32",
        linen: "faf0e6",
        magenta: "ff00ff",
        maroon: "800000",
        mediumaquamarine: "66cdaa",
        mediumblue: "0000cd",
        mediumorchid: "ba55d3",
        mediumpurple: "9370d8",
        mediumseagreen: "3cb371",
        mediumslateblue: "7b68ee",
        mediumspringgreen: "00fa9a",
        mediumturquoise: "48d1cc",
        mediumvioletred: "c71585",
        midnightblue: "191970",
        mintcream: "f5fffa",
        mistyrose: "ffe4e1",
        moccasin: "ffe4b5",
        navajowhite: "ffdead",
        navy: "000080",
        oldlace: "fdf5e6",
        olive: "808000",
        olivedrab: "6b8e23",
        orange: "ffa500",
        orangered: "ff4500",
        orchid: "da70d6",
        palegoldenrod: "eee8aa",
        palegreen: "98fb98",
        paleturquoise: "afeeee",
        palevioletred: "d87093",
        papayawhip: "ffefd5",
        peachpuff: "ffdab9",
        peru: "cd853f",
        pink: "ffc0cb",
        plum: "dda0dd",
        powderblue: "b0e0e6",
        purple: "800080",
        red: "ff0000",
        rosybrown: "bc8f8f",
        royalblue: "4169e1",
        saddlebrown: "8b4513",
        salmon: "fa8072",
        sandybrown: "f4a460",
        seagreen: "2e8b57",
        seashell: "fff5ee",
        sienna: "a0522d",
        silver: "c0c0c0",
        skyblue: "87ceeb",
        slateblue: "6a5acd",
        slategray: "708090",
        snow: "fffafa",
        springgreen: "00ff7f",
        steelblue: "4682b4",
        tan: "d2b48c",
        teal: "008080",
        thistle: "d8bfd8",
        tomato: "ff6347",
        turquoise: "40e0d0",
        violet: "ee82ee",
        violetred: "d02090",
        wheat: "f5deb3",
        white: "ffffff",
        whitesmoke: "f5f5f5",
        yellow: "ffff00",
        yellowgreen: "9acd32"
    };
    for (var c in b) a == c && (a = b[c]);
    var d = [{
        re: /^rgb\((\d{1,3}),\s*(\d{1,3}),\s*(\d{1,3})\)$/,
        example: ["rgb(123, 234, 45)", "rgb(255,234,245)"],
        process: function (a) {
            return [parseInt(a[1]), parseInt(a[2]), parseInt(a[3])]
        }
    }, {
        re: /^(\w{2})(\w{2})(\w{2})$/,
        example: ["#00ff00", "336699"],
        process: function (a) {
            return [parseInt(a[1], 16), parseInt(a[2], 16), parseInt(a[3], 16)]
        }
    }, {
        re: /^(\w{1})(\w{1})(\w{1})$/,
        example: ["#fb0", "f0f"],
        process: function (a) {
            return [parseInt(a[1] + a[1], 16), parseInt(a[2] + a[2], 16), parseInt(a[3] + a[3], 16)]
        }
    }];
    for (var e = 0; e < d.length; e++) {
        var f = d[e].re,
            g = d[e].process,
            h = f.exec(a);
        h && (channels = g(h), this.r = channels[0], this.g = channels[1], this.b = channels[2], this.ok = !0)
    }
    this.r = this.r < 0 || isNaN(this.r) ? 0 : this.r > 255 ? 255 : this.r, this.g = this.g < 0 || isNaN(this.g) ? 0 : this.g > 255 ? 255 : this.g, this.b = this.b < 0 || isNaN(this.b) ? 0 : this.b > 255 ? 255 : this.b, this.toRGB = function () {
        return "rgb(" + this.r + ", " + this.g + ", " + this.b + ")"
    }, this.toHex = function () {
        var a = this.r.toString(16),
            b = this.g.toString(16),
            c = this.b.toString(16);
        return a.length == 1 && (a = "0" + a), b.length == 1 && (b = "0" + b), c.length == 1 && (c = "0" + c), "#" + a + b + c
    }
}(function (a, b) {
    function c() {
        if (!q.isReady) {
            try {
                t.documentElement.doScroll("left")
            } catch (a) {
                setTimeout(c, 1);
                return
            }
            q.ready()
        }
    }
    function d(a, b) {
        b.src ? q.ajax({
            url: b.src,
            async: !1,
            dataType: "script"
        }) : q.globalEval(b.text || b.textContent || b.innerHTML || ""), b.parentNode && b.parentNode.removeChild(b)
    }
    function e(a, c, d, f, g, h) {
        var i = a.length;
        if (typeof c == "object") {
            for (var j in c) e(a, j, c[j], f, g, d);
            return a
        }
        if (d !== b) {
            f = !h && f && q.isFunction(d);
            for (j = 0; j < i; j++) g(a[j], c, f ? d.call(a[j], j, g(a[j], c)) : d, h);
            return a
        }
        return i ? g(a[0], c) : b
    }
    function f() {
        return (new Date).getTime()
    }
    function g() {
        return !1
    }
    function h() {
        return !0
    }
    function i(a, b, c) {
        return c[0].type = a, q.event.handle.apply(b, c)
    }
    function j(a) {
        var b, c = [],
            d = [],
            e = arguments,
            f, g, h, i, j, k;
        g = q.data(this, "events");
        if (!(a.liveFired === this || !g || !g.live || a.button && a.type === "click")) {
            a.liveFired = this;
            var l = g.live.slice(0);
            for (i = 0; i < l.length; i++) g = l[i], g.origType.replace(U, "") === a.type ? d.push(g.selector) : l.splice(i--, 1);
            f = q(a.target).closest(d, a.currentTarget), j = 0;
            for (k = f.length; j < k; j++) for (i = 0; i < l.length; i++) {
                g = l[i];
                if (f[j].selector === g.selector) {
                    h = f[j].elem, d = null;
                    if (g.preType === "mouseenter" || g.preType === "mouseleave") d = q(a.relatedTarget).closest(g.selector)[0];
                    (!d || d !== h) && c.push({
                        elem: h,
                        handleObj: g
                    })
                }
            }
            j = 0;
            for (k = c.length; j < k; j++) {
                f = c[j], a.currentTarget = f.elem, a.data = f.handleObj.data, a.handleObj = f.handleObj;
                if (f.handleObj.origHandler.apply(f.elem, e) === !1) {
                    b = !1;
                    break
                }
            }
            return b
        }
    }
    function k(a, b) {
        return "live." + (a && a !== "*" ? a + "." : "") + b.replace(/\./g, "`").replace(/ /g, "&")
    }
    function l(a) {
        return !a || !a.parentNode || a.parentNode.nodeType === 11
    }
    function m(a, b) {
        var c = 0;
        b.each(function () {
            if (this.nodeName === (a[c] && a[c].nodeName)) {
                var b = q.data(a[c++]),
                    d = q.data(this, b);
                if (b = b && b.events) {
                    delete d.handle, d.events = {};
                    for (var e in b) for (var f in b[e]) q.event.add(this, e, b[e][f], b[e][f].data)
                }
            }
        })
    }
    function n(a, b, c) {
        var d, e, f;
        return b = b && b[0] ? b[0].ownerDocument || b[0] : t, a.length === 1 && typeof a[0] == "string" && a[0].length < 512 && b === t && !bn.test(a[0]) && (q.support.checkClone || !bo.test(a[0])) && (e = !0, (f = q.fragments[a[0]]) && f !== 1 && (d = f)), d || (d = b.createDocumentFragment(), q.clean(a, b, d, c)), e && (q.fragments[a[0]] = f ? d : 1), {
            fragment: d,
            cacheable: e
        }
    }
    function o(a, b) {
        var c = {};
        return q.each(bT.concat.apply([], bT.slice(0, b)), function () {
            c[this] = a
        }), c
    }
    function p(a) {
        return "scrollTo" in a && a.document ? a : a.nodeType === 9 ? a.defaultView || a.parentWindow : !1
    }
    var q = function (a, b) {
        return new q.fn.init(a, b)
    }, r = a.jQuery,
        s = a.$,
        t = a.document,
        u, v = /^[^<]*(<[\w\W]+>)[^>]*$|^#([\w-]+)$/,
        w = /^.[^:#\[\.,]*$/,
        x = /\S/,
        y = /^(\s|\u00A0)+|(\s|\u00A0)+$/g,
        z = /^<(\w+)\s*\/?>(?:<\/\1>)?$/,
        A = navigator.userAgent,
        B = !1,
        C = [],
        D, E = Object.prototype.toString,
        F = Object.prototype.hasOwnProperty,
        G = Array.prototype.push,
        H = Array.prototype.slice,
        I = Array.prototype.indexOf;
    q.fn = q.prototype = {
        init: function (a, c) {
            var d, e;
            if (!a) return this;
            if (a.nodeType) return this.context = this[0] = a, this.length = 1, this;
            if (a === "body" && !c) return this.context = t, this[0] = t.body, this.selector = "body", this.length = 1, this;
            if (typeof a == "string") {
                if ((d = v.exec(a)) && (d[1] || !c)) {
                    if (d[1]) return e = c ? c.ownerDocument || c : t, (a = z.exec(a)) ? q.isPlainObject(c) ? (a = [t.createElement(a[1])], q.fn.attr.call(a, c, !0)) : a = [e.createElement(a[1])] : (a = n([d[1]], [e]), a = (a.cacheable ? a.fragment.cloneNode(!0) : a.fragment).childNodes), q.merge(this, a);
                    if (c = t.getElementById(d[2])) {
                        if (c.id !== d[2]) return u.find(a);
                        this.length = 1, this[0] = c
                    }
                    return this.context = t, this.selector = a, this
                }
                return !c && /^\w+$/.test(a) ? (this.selector = a, this.context = t, a = t.getElementsByTagName(a), q.merge(this, a)) : !c || c.jquery ? (c || u).find(a) : q(c).find(a)
            }
            return q.isFunction(a) ? u.ready(a) : (a.selector !== b && (this.selector = a.selector, this.context = a.context), q.makeArray(a, this))
        },
        selector: "",
        jquery: "1.4.2",
        length: 0,
        size: function () {
            return this.length
        },
        toArray: function () {
            return H.call(this, 0)
        },
        get: function (a) {
            return a == null ? this.toArray() : a < 0 ? this.slice(a)[0] : this[a]
        },
        pushStack: function (a, b, c) {
            var d = q();
            return q.isArray(a) ? G.apply(d, a) : q.merge(d, a), d.prevObject = this, d.context = this.context, b === "find" ? d.selector = this.selector + (this.selector ? " " : "") + c : b && (d.selector = this.selector + "." + b + "(" + c + ")"), d
        },
        each: function (a, b) {
            return q.each(this, a, b)
        },
        ready: function (a) {
            return q.bindReady(), q.isReady ? a.call(t, q) : C && C.push(a), this
        },
        eq: function (a) {
            return a === -1 ? this.slice(a) : this.slice(a, +a + 1)
        },
        first: function () {
            return this.eq(0)
        },
        last: function () {
            return this.eq(-1)
        },
        slice: function () {
            return this.pushStack(H.apply(this, arguments), "slice", H.call(arguments).join(","))
        },
        map: function (a) {
            return this.pushStack(q.map(this, function (b, c) {
                return a.call(b, c, b)
            }))
        },
        end: function () {
            return this.prevObject || q(null)
        },
        push: G,
        sort: [].sort,
        splice: [].splice
    }, q.fn.init.prototype = q.fn, q.extend = q.fn.extend = function () {
        var a = arguments[0] || {}, c = 1,
            d = arguments.length,
            e = !1,
            f, g, h, i;
        typeof a == "boolean" && (e = a, a = arguments[1] || {}, c = 2), typeof a != "object" && !q.isFunction(a) && (a = {}), d === c && (a = this, --c);
        for (; c < d; c++) if ((f = arguments[c]) != null) for (g in f) h = a[g], i = f[g], a !== i && (e && i && (q.isPlainObject(i) || q.isArray(i)) ? (h = h && (q.isPlainObject(h) || q.isArray(h)) ? h : q.isArray(i) ? [] : {}, a[g] = q.extend(e, h, i)) : i !== b && (a[g] = i));
        return a
    }, q.extend({
        noConflict: function (b) {
            return a.$ = s, b && (a.jQuery = r), q
        },
        isReady: !1,
        ready: function () {
            if (!q.isReady) {
                if (!t.body) return setTimeout(q.ready, 13);
                q.isReady = !0;
                if (C) {
                    for (var a, b = 0; a = C[b++];) a.call(t, q);
                    C = null
                }
                q.fn.triggerHandler && q(t).triggerHandler("ready")
            }
        },
        bindReady: function () {
            if (!B) {
                B = !0;
                if (t.readyState === "complete") return q.ready();
                if (t.addEventListener) t.addEventListener("DOMContentLoaded", D, !1), a.addEventListener("load", q.ready, !1);
                else if (t.attachEvent) {
                    t.attachEvent("onreadystatechange", D), a.attachEvent("onload", q.ready);
                    var b = !1;
                    try {
                        b = a.frameElement == null
                    } catch (d) {}
                    t.documentElement.doScroll && b && c()
                }
            }
        },
        isFunction: function (a) {
            return E.call(a) === "[object Function]"
        },
        isArray: function (a) {
            return E.call(a) === "[object Array]"
        },
        isPlainObject: function (a) {
            if (!a || E.call(a) !== "[object Object]" || a.nodeType || a.setInterval) return !1;
            if (a.constructor && !F.call(a, "constructor") && !F.call(a.constructor.prototype, "isPrototypeOf")) return !1;
            var c;
            for (c in a);
            return c === b || F.call(a, c)
        },
        isEmptyObject: function (a) {
            for (var b in a) return !1;
            return !0
        },
        error: function (a) {
            throw a
        },
        parseJSON: function (b) {
            if (typeof b != "string" || !b) return null;
            b = q.trim(b);
            if (/^[\],:{}\s]*$/.test(b.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) return a.JSON && a.JSON.parse ? a.JSON.parse(b) : (new Function("return " + b))();
            q.error("Invalid JSON: " + b)
        },
        noop: function () {},
        globalEval: function (a) {
            if (a && x.test(a)) {
                var b = t.getElementsByTagName("head")[0] || t.documentElement,
                    c = t.createElement("script");
                c.type = "text/javascript", q.support.scriptEval ? c.appendChild(t.createTextNode(a)) : c.text = a, b.insertBefore(c, b.firstChild), b.removeChild(c)
            }
        },
        nodeName: function (a, b) {
            return a.nodeName && a.nodeName.toUpperCase() === b.toUpperCase()
        },
        each: function (a, c, d) {
            var e, f = 0,
                g = a.length,
                h = g === b || q.isFunction(a);
            if (d) {
                if (h) {
                    for (e in a) if (c.apply(a[e], d) === !1) break
                } else for (; f < g;) if (c.apply(a[f++], d) === !1) break
            } else if (h) {
                for (e in a) if (c.call(a[e], e, a[e]) === !1) break
            } else for (d = a[0]; f < g && c.call(d, f, d) !== !1; d = a[++f]);
            return a
        },
        trim: function (a) {
            return (a || "").replace(y, "")
        },
        makeArray: function (a, b) {
            return b = b || [], a != null && (a.length == null || typeof a == "string" || q.isFunction(a) || typeof a != "function" && a.setInterval ? G.call(b, a) : q.merge(b, a)), b
        },
        inArray: function (a, b) {
            if (b.indexOf) return b.indexOf(a);
            for (var c = 0, d = b.length; c < d; c++) if (b[c] === a) return c;
            return -1
        },
        merge: function (a, c) {
            var d = a.length,
                e = 0;
            if (typeof c.length == "number") for (var f = c.length; e < f; e++) a[d++] = c[e];
            else for (; c[e] !== b;) a[d++] = c[e++];
            return a.length = d, a
        },
        grep: function (a, b, c) {
            for (var d = [], e = 0, f = a.length; e < f; e++)!c != !b(a[e], e) && d.push(a[e]);
            return d
        },
        map: function (a, b, c) {
            for (var d = [], e, f = 0, g = a.length; f < g; f++) e = b(a[f], f, c), e != null && (d[d.length] = e);
            return d.concat.apply([], d)
        },
        guid: 1,
        proxy: function (a, c, d) {
            return arguments.length === 2 && (typeof c == "string" ? (d = a, a = d[c], c = b) : c && !q.isFunction(c) && (d = c, c = b)), !c && a && (c = function () {
                return a.apply(d || this, arguments)
            }), a && (c.guid = a.guid = a.guid || c.guid || q.guid++), c
        },
        uaMatch: function (a) {
            return a = a.toLowerCase(), a = /(webkit)[ \/]([\w.]+)/.exec(a) || /(opera)(?:.*version)?[ \/]([\w.]+)/.exec(a) || /(msie) ([\w.]+)/.exec(a) || !/compatible/.test(a) && /(mozilla)(?:.*? rv:([\w.]+))?/.exec(a) || [], {
                browser: a[1] || "",
                version: a[2] || "0"
            }
        },
        browser: {}
    }), A = q.uaMatch(A), A.browser && (q.browser[A.browser] = !0, q.browser.version = A.version), q.browser.webkit && (q.browser.safari = !0), I && (q.inArray = function (a, b) {
        return I.call(b, a)
    }), u = q(t), t.addEventListener ? D = function () {
        t.removeEventListener("DOMContentLoaded", D, !1), q.ready()
    } : t.attachEvent && (D = function () {
        t.readyState === "complete" && (t.detachEvent("onreadystatechange", D), q.ready())
    }),
    function () {
        q.support = {};
        var b = t.documentElement,
            c = t.createElement("script"),
            d = t.createElement("div"),
            e = "script" + f();
        d.style.display = "none", d.innerHTML = "   <link/><table></table><a href='/a' style='color:red;float:left;opacity:.55;'>a</a><input type='checkbox'/>";
        var g = d.getElementsByTagName("*"),
            h = d.getElementsByTagName("a")[0];
        if (!(!g || !g.length || !h)) {
            q.support = {
                leadingWhitespace: d.firstChild.nodeType === 3,
                tbody: !d.getElementsByTagName("tbody").length,
                htmlSerialize: !! d.getElementsByTagName("link").length,
                style: /red/.test(h.getAttribute("style")),
                hrefNormalized: h.getAttribute("href") === "/a",
                opacity: /^0.55$/.test(h.style.opacity),
                cssFloat: !! h.style.cssFloat,
                checkOn: d.getElementsByTagName("input")[0].value === "on",
                optSelected: t.createElement("select").appendChild(t.createElement("option")).selected,
                parentNode: d.removeChild(d.appendChild(t.createElement("div"))).parentNode === null,
                deleteExpando: !0,
                checkClone: !1,
                scriptEval: !1,
                noCloneEvent: !0,
                boxModel: null
            }, c.type = "text/javascript";
            try {
                c.appendChild(t.createTextNode("window." + e + "=1;"))
            } catch (i) {}
            b.insertBefore(c, b.firstChild), a[e] && (q.support.scriptEval = !0, delete a[e]);
            try {
                delete c.test
            } catch (j) {
                q.support.deleteExpando = !1
            }
            b.removeChild(c), d.attachEvent && d.fireEvent && (d.attachEvent("onclick", function k() {
                q.support.noCloneEvent = !1, d.detachEvent("onclick", k)
            }), d.cloneNode(!0).fireEvent("onclick")), d = t.createElement("div"), d.innerHTML = "<input type='radio' name='radiotest' checked='checked'/>", b = t.createDocumentFragment(), b.appendChild(d.firstChild), q.support.checkClone = b.cloneNode(!0).cloneNode(!0).lastChild.checked, q(function () {
                var a = t.createElement("div");
                a.style.width = a.style.paddingLeft = "1px", t.body.appendChild(a), q.boxModel = q.support.boxModel = a.offsetWidth === 2, t.body.removeChild(a).style.display = "none"
            }), b = function (a) {
                var b = t.createElement("div");
                a = "on" + a;
                var c = a in b;
                return c || (b.setAttribute(a, "return;"), c = typeof b[a] == "function"), c
            }, q.support.submitBubbles = b("submit"), q.support.changeBubbles = b("change"), b = c = d = g = h = null
        }
    }(), q.props = {
        "for": "htmlFor",
        "class": "className",
        readonly: "readOnly",
        maxlength: "maxLength",
        cellspacing: "cellSpacing",
        rowspan: "rowSpan",
        colspan: "colSpan",
        tabindex: "tabIndex",
        usemap: "useMap",
        frameborder: "frameBorder"
    };
    var J = "jQuery" + f(),
        K = 0,
        L = {};
    q.extend({
        cache: {},
        expando: J,
        noData: {
            embed: !0,
            object: !0,
            applet: !0
        },
        data: function (c, d, e) {
            if (!c.nodeName || !q.noData[c.nodeName.toLowerCase()]) {
                c = c == a ? L : c;
                var f = c[J],
                    g = q.cache;
                return !f && typeof d == "string" && e === b ? null : (f || (f = ++K), typeof d == "object" ? (c[J] = f, g[f] = q.extend(!0, {}, d)) : g[f] || (c[J] = f, g[f] = {}), c = g[f], e !== b && (c[d] = e), typeof d == "string" ? c[d] : c)
            }
        },
        removeData: function (b, c) {
            if (!b.nodeName || !q.noData[b.nodeName.toLowerCase()]) {
                b = b == a ? L : b;
                var d = b[J],
                    e = q.cache,
                    f = e[d];
                c ? f && (delete f[c], q.isEmptyObject(f) && q.removeData(b)) : (q.support.deleteExpando ? delete b[q.expando] : b.removeAttribute && b.removeAttribute(q.expando), delete e[d])
            }
        }
    }), q.fn.extend({
        data: function (a, c) {
            if (typeof a == "undefined" && this.length) return q.data(this[0]);
            if (typeof a == "object") return this.each(function () {
                q.data(this, a)
            });
            var d = a.split(".");
            d[1] = d[1] ? "." + d[1] : "";
            if (c === b) {
                var e = this.triggerHandler("getData" + d[1] + "!", [d[0]]);
                return e === b && this.length && (e = q.data(this[0], a)), e === b && d[1] ? this.data(d[0]) : e
            }
            return this.trigger("setData" + d[1] + "!", [d[0], c]).each(function () {
                q.data(this, a, c)
            })
        },
        removeData: function (a) {
            return this.each(function () {
                q.removeData(this, a)
            })
        }
    }), q.extend({
        queue: function (a, b, c) {
            if (a) {
                b = (b || "fx") + "queue";
                var d = q.data(a, b);
                return c ? (!d || q.isArray(c) ? d = q.data(a, b, q.makeArray(c)) : d.push(c), d) : d || []
            }
        },
        dequeue: function (a, b) {
            b = b || "fx";
            var c = q.queue(a, b),
                d = c.shift();
            d === "inprogress" && (d = c.shift()), d && (b === "fx" && c.unshift("inprogress"), d.call(a, function () {
                q.dequeue(a, b)
            }))
        }
    }), q.fn.extend({
        queue: function (a, c) {
            return typeof a != "string" && (c = a, a = "fx"), c === b ? q.queue(this[0], a) : this.each(function () {
                var b = q.queue(this, a, c);
                a === "fx" && b[0] !== "inprogress" && q.dequeue(this, a)
            })
        },
        dequeue: function (a) {
            return this.each(function () {
                q.dequeue(this, a)
            })
        },
        delay: function (a, b) {
            return a = q.fx ? q.fx.speeds[a] || a : a, b = b || "fx", this.queue(b, function () {
                var c = this;
                setTimeout(function () {
                    q.dequeue(c, b)
                }, a)
            })
        },
        clearQueue: function (a) {
            return this.queue(a || "fx", [])
        }
    });
    var M = /[\n\t]/g,
        N = /\s+/,
        O = /\r/g,
        P = /href|src|style/,
        Q = /(button|input)/i,
        R = /(button|input|object|select|textarea)/i,
        S = /^(a|area)$/i,
        T = /radio|checkbox/;
    q.fn.extend({
        attr: function (a, b) {
            return e(this, a, b, !0, q.attr)
        },
        removeAttr: function (a) {
            return this.each(function () {
                q.attr(this, a, ""), this.nodeType === 1 && this.removeAttribute(a)
            })
        },
        addClass: function (a) {
            if (q.isFunction(a)) return this.each(function (b) {
                var c = q(this);
                c.addClass(a.call(this, b, c.attr("class")))
            });
            if (a && typeof a == "string") for (var b = (a || "").split(N), c = 0, d = this.length; c < d; c++) {
                var e = this[c];
                if (e.nodeType === 1) if (e.className) {
                    for (var f = " " + e.className + " ", g = e.className, h = 0, i = b.length; h < i; h++) f.indexOf(" " + b[h] + " ") < 0 && (g += " " + b[h]);
                    e.className = q.trim(g)
                } else e.className = a
            }
            return this
        },
        removeClass: function (a) {
            if (q.isFunction(a)) return this.each(function (b) {
                var c = q(this);
                c.removeClass(a.call(this, b, c.attr("class")))
            });
            if (a && typeof a == "string" || a === b) for (var c = (a || "").split(N), d = 0, e = this.length; d < e; d++) {
                var f = this[d];
                if (f.nodeType === 1 && f.className) if (a) {
                    for (var g = (" " + f.className + " ").replace(M, " "), h = 0, i = c.length; h < i; h++) g = g.replace(" " + c[h] + " ", " ");
                    f.className = q.trim(g)
                } else f.className = ""
            }
            return this
        },
        toggleClass: function (a, b) {
            var c = typeof a,
                d = typeof b == "boolean";
            return q.isFunction(a) ? this.each(function (c) {
                var d = q(this);
                d.toggleClass(a.call(this, c, d.attr("class"), b), b)
            }) : this.each(function () {
                if (c === "string") for (var e, f = 0, g = q(this), h = b, i = a.split(N); e = i[f++];) h = d ? h : !g.hasClass(e), g[h ? "addClass" : "removeClass"](e);
                else if (c === "undefined" || c === "boolean") this.className && q.data(this, "__className__", this.className), this.className = this.className || a === !1 ? "" : q.data(this, "__className__") || ""
            })
        },
        hasClass: function (a) {
            a = " " + a + " ";
            for (var b = 0, c = this.length; b < c; b++) if ((" " + this[b].className + " ").replace(M, " ").indexOf(a) > -1) return !0;
            return !1
        },
        val: function (a) {
            if (a === b) {
                var c = this[0];
                if (c) {
                    if (q.nodeName(c, "option")) return (c.attributes.value || {}).specified ? c.value : c.text;
                    if (q.nodeName(c, "select")) {
                        var d = c.selectedIndex,
                            e = [],
                            f = c.options;
                        c = c.type === "select-one";
                        if (d < 0) return null;
                        var g = c ? d : 0;
                        for (d = c ? d + 1 : f.length; g < d; g++) {
                            var h = f[g];
                            if (h.selected) {
                                a = q(h).val();
                                if (c) return a;
                                e.push(a)
                            }
                        }
                        return e
                    }
                    return T.test(c.type) && !q.support.checkOn ? c.getAttribute("value") === null ? "on" : c.value : (c.value || "").replace(O, "")
                }
                return b
            }
            var i = q.isFunction(a);
            return this.each(function (b) {
                var c = q(this),
                    d = a;
                if (this.nodeType === 1) {
                    i && (d = a.call(this, b, c.val())), typeof d == "number" && (d += "");
                    if (q.isArray(d) && T.test(this.type)) this.checked = q.inArray(c.val(), d) >= 0;
                    else if (q.nodeName(this, "select")) {
                        var e = q.makeArray(d);
                        q("option", this).each(function () {
                            this.selected = q.inArray(q(this).val(), e) >= 0
                        }), e.length || (this.selectedIndex = -1)
                    } else this.value = d
                }
            })
        }
    }), q.extend({
        attrFn: {
            val: !0,
            css: !0,
            html: !0,
            text: !0,
            data: !0,
            width: !0,
            height: !0,
            offset: !0
        },
        attr: function (a, c, d, e) {
            if (!a || a.nodeType === 3 || a.nodeType === 8) return b;
            if (e && c in q.attrFn) return q(a)[c](d);
            e = a.nodeType !== 1 || !q.isXMLDoc(a);
            var f = d !== b;
            c = e && q.props[c] || c;
            if (a.nodeType === 1) {
                var g = P.test(c);
                return c in a && e && !g ? (f && (c === "type" && Q.test(a.nodeName) && a.parentNode && q.error("type property can't be changed"), a[c] = d), q.nodeName(a, "form") && a.getAttributeNode(c) ? a.getAttributeNode(c).nodeValue : c === "tabIndex" ? (c = a.getAttributeNode("tabIndex")) && c.specified ? c.value : R.test(a.nodeName) || S.test(a.nodeName) && a.href ? 0 : b : a[c]) : !q.support.style && e && c === "style" ? (f && (a.style.cssText = "" + d), a.style.cssText) : (f && a.setAttribute(c, "" + d), a = !q.support.hrefNormalized && e && g ? a.getAttribute(c, 2) : a.getAttribute(c), a === null ? b : a)
            }
            return q.style(a, c, d)
        }
    });
    var U = /\.(.*)$/,
        V = function (a) {
            return a.replace(/[^\w\s\.\|`]/g, function (a) {
                return "\\" + a
            })
        };
    q.event = {
        add: function (c, d, e, f) {
            if (c.nodeType !== 3 && c.nodeType !== 8) {
                c.setInterval && c !== a && !c.frameElement && (c = a);
                var g, h;
                e.handler && (g = e, e = g.handler), e.guid || (e.guid = q.guid++);
                if (h = q.data(c)) {
                    var i = h.events = h.events || {}, j = h.handle;
                    j || (h.handle = j = function () {
                        return typeof q != "undefined" && !q.event.triggered ? q.event.handle.apply(j.elem, arguments) : b
                    }), j.elem = c, d = d.split(" ");
                    for (var k, l = 0, m; k = d[l++];) {
                        h = g ? q.extend({}, g) : {
                            handler: e,
                            data: f
                        }, k.indexOf(".") > -1 ? (m = k.split("."), k = m.shift(), h.namespace = m.slice(0).sort().join(".")) : (m = [], h.namespace = ""), h.type = k, h.guid = e.guid;
                        var n = i[k],
                            o = q.event.special[k] || {};
                        if (!n) {
                            n = i[k] = [];
                            if (!o.setup || o.setup.call(c, f, m, j) === !1) c.addEventListener ? c.addEventListener(k, j, !1) : c.attachEvent && c.attachEvent("on" + k, j)
                        }
                        o.add && (o.add.call(c, h), h.handler.guid || (h.handler.guid = e.guid)), n.push(h), q.event.global[k] = !0
                    }
                    c = null
                }
            }
        },
        global: {},
        remove: function (a, b, c, d) {
            if (a.nodeType !== 3 && a.nodeType !== 8) {
                var e, f = 0,
                    g, h, i, j, k, l, m = q.data(a),
                    n = m && m.events;
                if (m && n) {
                    b && b.type && (c = b.handler, b = b.type);
                    if (!b || typeof b == "string" && b.charAt(0) === ".") {
                        b = b || "";
                        for (e in n) q.event.remove(a, e + b)
                    } else {
                        for (b = b.split(" "); e = b[f++];) {
                            j = e, g = e.indexOf(".") < 0, h = [], g || (h = e.split("."), e = h.shift(), i = new RegExp("(^|\\.)" + q.map(h.slice(0).sort(), V).join("\\.(?:.*\\.)?") + "(\\.|$)"));
                            if (k = n[e]) if (c) {
                                j = q.event.special[e] || {};
                                for (o = d || 0; o < k.length; o++) {
                                    l = k[o];
                                    if (c.guid === l.guid) {
                                        if (g || i.test(l.namespace)) d == null && k.splice(o--, 1), j.remove && j.remove.call(a, l);
                                        if (d != null) break
                                    }
                                }
                                if (k.length === 0 || d != null && k.length === 1)(!j.teardown || j.teardown.call(a, h) === !1) && W(a, e, m.handle), delete n[e]
                            } else for (var o = 0; o < k.length; o++) {
                                l = k[o];
                                if (g || i.test(l.namespace)) q.event.remove(a, j, l.handler, o), k.splice(o--, 1)
                            }
                        }
                        if (q.isEmptyObject(n)) {
                            if (b = m.handle) b.elem = null;
                            delete m.events, delete m.handle, q.isEmptyObject(m) && q.removeData(a)
                        }
                    }
                }
            }
        },
        trigger: function (a, c, d, e) {
            var f = a.type || a;
            if (!e) {
                a = typeof a == "object" ? a[J] ? a : q.extend(q.Event(f), a) : q.Event(f), f.indexOf("!") >= 0 && (a.type = f = f.slice(0, -1), a.exclusive = !0), d || (a.stopPropagation(), q.event.global[f] && q.each(q.cache, function () {
                    this.events && this.events[f] && q.event.trigger(a, c, this.handle.elem)
                }));
                if (!d || d.nodeType === 3 || d.nodeType === 8) return b;
                a.result = b, a.target = d, c = q.makeArray(c), c.unshift(a)
            }
            a.currentTarget = d, (e = q.data(d, "handle")) && e.apply(d, c), e = d.parentNode || d.ownerDocument;
            try {
                d && d.nodeName && q.noData[d.nodeName.toLowerCase()] || d["on" + f] && d["on" + f].apply(d, c) === !1 && (a.result = !1)
            } catch (g) {}
            if (!a.isPropagationStopped() && e) q.event.trigger(a, c, e, !0);
            else if (!a.isDefaultPrevented()) {
                e = a.target;
                var h, i = q.nodeName(e, "a") && f === "click",
                    j = q.event.special[f] || {};
                if ((!j._default || j._default.call(d, a) === !1) && !i && !(e && e.nodeName && q.noData[e.nodeName.toLowerCase()])) {
                    try {
                        if (e[f]) {
                            if (h = e["on" + f]) e["on" + f] = null;
                            q.event.triggered = !0, e[f]()
                        }
                    } catch (k) {}
                    h && (e["on" + f] = h), q.event.triggered = !1
                }
            }
        },
        handle: function (c) {
            var d, e, f, g;
            c = arguments[0] = q.event.fix(c || a.event), c.currentTarget = this, d = c.type.indexOf(".") < 0 && !c.exclusive, d || (e = c.type.split("."), c.type = e.shift(), f = new RegExp("(^|\\.)" + e.slice(0).sort().join("\\.(?:.*\\.)?") + "(\\.|$)")), g = q.data(this, "events"), e = g[c.type];
            if (g && e) {
                e = e.slice(0), g = 0;
                for (var h = e.length; g < h; g++) {
                    var i = e[g];
                    if (d || f.test(i.namespace)) {
                        c.handler = i.handler, c.data = i.data, c.handleObj = i, i = i.handler.apply(this, arguments), i !== b && (c.result = i, i === !1 && (c.preventDefault(), c.stopPropagation()));
                        if (c.isImmediatePropagationStopped()) break
                    }
                }
            }
            return c.result
        },
        props: "altKey attrChange attrName bubbles button cancelable charCode clientX clientY ctrlKey currentTarget data detail eventPhase fromElement handler keyCode layerX layerY metaKey newValue offsetX offsetY originalTarget pageX pageY prevValue relatedNode relatedTarget screenX screenY shiftKey srcElement target toElement view wheelDelta which".split(" "),
        fix: function (a) {
            if (a[J]) return a;
            var c = a;
            a = q.Event(c);
            for (var d = this.props.length, e; d;) e = this.props[--d], a[e] = c[e];
            return a.target || (a.target = a.srcElement || t), a.target.nodeType === 3 && (a.target = a.target.parentNode), !a.relatedTarget && a.fromElement && (a.relatedTarget = a.fromElement === a.target ? a.toElement : a.fromElement), a.pageX == null && a.clientX != null && (c = t.documentElement, d = t.body, a.pageX = a.clientX + (c && c.scrollLeft || d && d.scrollLeft || 0) - (c && c.clientLeft || d && d.clientLeft || 0), a.pageY = a.clientY + (c && c.scrollTop || d && d.scrollTop || 0) - (c && c.clientTop || d && d.clientTop || 0)), !a.which && (a.charCode || a.charCode === 0 ? a.charCode : a.keyCode) && (a.which = a.charCode || a.keyCode), !a.metaKey && a.ctrlKey && (a.metaKey = a.ctrlKey), !a.which && a.button !== b && (a.which = a.button & 1 ? 1 : a.button & 2 ? 3 : a.button & 4 ? 2 : 0), a
        },
        guid: 1e8,
        proxy: q.proxy,
        special: {
            ready: {
                setup: q.bindReady,
                teardown: q.noop
            },
            live: {
                add: function (a) {
                    q.event.add(this, a.origType, q.extend({}, a, {
                        handler: j
                    }))
                },
                remove: function (a) {
                    var b = !0,
                        c = a.origType.replace(U, "");
                    q.each(q.data(this, "events").live || [], function () {
                        if (c === this.origType.replace(U, "")) return b = !1
                    }), b && q.event.remove(this, a.origType, j)
                }
            },
            beforeunload: {
                setup: function (a, b, c) {
                    return this.setInterval && (this.onbeforeunload = c), !1
                },
                teardown: function (a, b) {
                    this.onbeforeunload === b && (this.onbeforeunload = null)
                }
            }
        }
    };
    var W = t.removeEventListener ? function (a, b, c) {
            a.removeEventListener(b, c, !1)
        } : function (a, b, c) {
            a.detachEvent("on" + b, c)
        };
    q.Event = function (a) {
        if (!this.preventDefault) return new q.Event(a);
        a && a.type ? (this.originalEvent = a, this.type = a.type) : this.type = a, this.timeStamp = f(), this[J] = !0
    }, q.Event.prototype = {
        preventDefault: function () {
            this.isDefaultPrevented = h;
            var a = this.originalEvent;
            a && (a.preventDefault && a.preventDefault(), a.returnValue = !1)
        },
        stopPropagation: function () {
            this.isPropagationStopped = h;
            var a = this.originalEvent;
            a && (a.stopPropagation && a.stopPropagation(), a.cancelBubble = !0)
        },
        stopImmediatePropagation: function () {
            this.isImmediatePropagationStopped = h, this.stopPropagation()
        },
        isDefaultPrevented: g,
        isPropagationStopped: g,
        isImmediatePropagationStopped: g
    };
    var X = function (a) {
        var b = a.relatedTarget;
        try {
            for (; b && b !== this;) b = b.parentNode;
            b !== this && (a.type = a.data, q.event.handle.apply(this, arguments))
        } catch (c) {}
    }, Y = function (a) {
        a.type = a.data, q.event.handle.apply(this, arguments)
    };
    q.each({
        mouseenter: "mouseover",
        mouseleave: "mouseout"
    }, function (a, b) {
        q.event.special[a] = {
            setup: function (c) {
                q.event.add(this, b, c && c.selector ? Y : X, a)
            },
            teardown: function (a) {
                q.event.remove(this, b, a && a.selector ? Y : X)
            }
        }
    }), q.support.submitBubbles || (q.event.special.submit = {
        setup: function () {
            if (this.nodeName.toLowerCase() !== "form") q.event.add(this, "click.specialSubmit", function (a) {
                var b = a.target,
                    c = b.type;
                if ((c === "submit" || c === "image") && q(b).closest("form").length) return i("submit", this, arguments)
            }), q.event.add(this, "keypress.specialSubmit", function (a) {
                var b = a.target,
                    c = b.type;
                if ((c === "text" || c === "password") && q(b).closest("form").length && a.keyCode === 13) return i("submit", this, arguments)
            });
            else return !1
        },
        teardown: function () {
            q.event.remove(this, ".specialSubmit")
        }
    });
    if (!q.support.changeBubbles) {
        var Z = /textarea|input|select/i,
            $, _ = function (a) {
                var b = a.type,
                    c = a.value;
                return b === "radio" || b === "checkbox" ? c = a.checked : b === "select-multiple" ? c = a.selectedIndex > -1 ? q.map(a.options, function (a) {
                    return a.selected
                }).join("-") : "" : a.nodeName.toLowerCase() === "select" && (c = a.selectedIndex), c
            }, ba = function (a, c) {
                var d = a.target,
                    e, f;
                if ( !! Z.test(d.nodeName) && !d.readOnly) {
                    e = q.data(d, "_change_data"), f = _(d), (a.type !== "focusout" || d.type !== "radio") && q.data(d, "_change_data", f);
                    if (e !== b && f !== e) if (e != null || f) return a.type = "change", q.event.trigger(a, c, d)
                }
            };
        q.event.special.change = {
            filters: {
                focusout: ba,
                click: function (a) {
                    var b = a.target,
                        c = b.type;
                    if (c === "radio" || c === "checkbox" || b.nodeName.toLowerCase() === "select") return ba.call(this, a)
                },
                keydown: function (a) {
                    var b = a.target,
                        c = b.type;
                    if (a.keyCode === 13 && b.nodeName.toLowerCase() !== "textarea" || a.keyCode === 32 && (c === "checkbox" || c === "radio") || c === "select-multiple") return ba.call(this, a)
                },
                beforeactivate: function (a) {
                    a = a.target, q.data(a, "_change_data", _(a))
                }
            },
            setup: function () {
                if (this.type === "file") return !1;
                for (var a in $) q.event.add(this, a + ".specialChange", $[a]);
                return Z.test(this.nodeName)
            },
            teardown: function () {
                return q.event.remove(this, ".specialChange"), Z.test(this.nodeName)
            }
        }, $ = q.event.special.change.filters
    }
    t.addEventListener && q.each({
        focus: "focusin",
        blur: "focusout"
    }, function (a, b) {
        function c(a) {
            return a = q.event.fix(a), a.type = b, q.event.handle.call(this, a)
        }
        q.event.special[b] = {
            setup: function () {
                this.addEventListener(a, c, !0)
            },
            teardown: function () {
                this.removeEventListener(a, c, !0)
            }
        }
    }), q.each(["bind", "one"], function (a, c) {
        q.fn[c] = function (a, d, e) {
            if (typeof a == "object") {
                for (var f in a) this[c](f, d, a[f], e);
                return this
            }
            q.isFunction(d) && (e = d, d = b);
            var g = c === "one" ? q.proxy(e, function (a) {
                return q(this).unbind(a, g), e.apply(this, arguments)
            }) : e;
            if (a === "unload" && c !== "one") this.one(a, d, e);
            else {
                f = 0;
                for (var h = this.length; f < h; f++) q.event.add(this[f], a, g, d)
            }
            return this
        }
    }), q.fn.extend({
        unbind: function (a, b) {
            if (typeof a == "object" && !a.preventDefault) for (var c in a) this.unbind(c, a[c]);
            else {
                c = 0;
                for (var d = this.length; c < d; c++) q.event.remove(this[c], a, b)
            }
            return this
        },
        delegate: function (a, b, c, d) {
            return this.live(b, c, d, a)
        },
        undelegate: function (a, b, c) {
            return arguments.length === 0 ? this.unbind("live") : this.die(b, null, c, a)
        },
        trigger: function (a, b) {
            return this.each(function () {
                q.event.trigger(a, b, this)
            })
        },
        triggerHandler: function (a, b) {
            if (this[0]) return a = q.Event(a), a.preventDefault(), a.stopPropagation(), q.event.trigger(a, b, this[0]), a.result
        },
        toggle: function (a) {
            for (var b = arguments, c = 1; c < b.length;) q.proxy(a, b[c++]);
            return this.click(q.proxy(a, function (d) {
                var e = (q.data(this, "lastToggle" + a.guid) || 0) % c;
                return q.data(this, "lastToggle" + a.guid, e + 1), d.preventDefault(), b[e].apply(this, arguments) || !1
            }))
        },
        hover: function (a, b) {
            return this.mouseenter(a).mouseleave(b || a)
        }
    });
    var bb = {
        focus: "focusin",
        blur: "focusout",
        mouseenter: "mouseover",
        mouseleave: "mouseout"
    };
    q.each(["live", "die"], function (a, c) {
        q.fn[c] = function (a, d, e, f) {
            var g, h = 0,
                i, j, l = f || this.selector,
                m = f ? this : q(this.context);
            q.isFunction(d) && (e = d, d = b);
            for (a = (a || "").split(" ");
            (g = a[h++]) != null;) f = U.exec(g), i = "", f && (i = f[0], g = g.replace(U, "")), g === "hover" ? a.push("mouseenter" + i, "mouseleave" + i) : (j = g, g === "focus" || g === "blur" ? (a.push(bb[g] + i), g += i) : g = (bb[g] || g) + i, c === "live" ? m.each(function () {
                q.event.add(this, k(g, l), {
                    data: d,
                    selector: l,
                    handler: e,
                    origType: g,
                    origHandler: e,
                    preType: j
                })
            }) : m.unbind(k(g, l), e));
            return this
        }
    }), q.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error".split(" "), function (a, b) {
        q.fn[b] = function (a) {
            return a ? this.bind(b, a) : this.trigger(b)
        }, q.attrFn && (q.attrFn[b] = !0)
    }), a.attachEvent && !a.addEventListener && a.attachEvent("onunload", function () {
        for (var a in q.cache) if (q.cache[a].handle) try {
            q.event.remove(q.cache[a].handle.elem)
        } catch (b) {}
    }),
    function () {
        function a(b) {
            for (var c = "", d, e = 0; b[e]; e++) d = b[e], d.nodeType === 3 || d.nodeType === 4 ? c += d.nodeValue : d.nodeType !== 8 && (c += a(d.childNodes));
            return c
        }
        function c(a, b, c, d, e, f) {
            e = 0;
            for (var g = d.length; e < g; e++) {
                var h = d[e];
                if (h) {
                    h = h[a];
                    for (var i = !1; h;) {
                        if (h.sizcache === c) {
                            i = d[h.sizset];
                            break
                        }
                        h.nodeType === 1 && !f && (h.sizcache = c, h.sizset = e);
                        if (h.nodeName.toLowerCase() === b) {
                            i = h;
                            break
                        }
                        h = h[a]
                    }
                    d[e] = i
                }
            }
        }
        function d(a, b, c, d, e, f) {
            e = 0;
            for (var g = d.length; e < g; e++) {
                var h = d[e];
                if (h) {
                    h = h[a];
                    for (var i = !1; h;) {
                        if (h.sizcache === c) {
                            i = d[h.sizset];
                            break
                        }
                        if (h.nodeType === 1) {
                            f || (h.sizcache = c, h.sizset = e);
                            if (typeof b != "string") {
                                if (h === b) {
                                    i = !0;
                                    break
                                }
                            } else if (j.filter(b, [h]).length > 0) {
                                i = h;
                                break
                            }
                        }
                        h = h[a]
                    }
                    d[e] = i
                }
            }
        }
        var e = /((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^[\]]*\]|['"][^'"]*['"]|[^[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g,
            f = 0,
            g = Object.prototype.toString,
            h = !1,
            i = !0;
        [0, 0].sort(function () {
            return i = !1, 0
        });
        var j = function (a, b, c, d) {
            c = c || [];
            var f = b = b || t;
            if (b.nodeType !== 1 && b.nodeType !== 9) return [];
            if (!a || typeof a != "string") return c;
            for (var h = [], i, m, o, p, q = !0, v = s(b), w = a;
            (e.exec(""), i = e.exec(w)) !== null;) {
                w = i[3], h.push(i[1]);
                if (i[2]) {
                    p = i[3];
                    break
                }
            }
            if (h.length > 1 && l.exec(a)) if (h.length === 2 && k.relative[h[0]]) m = u(h[0] + h[1], b);
            else for (m = k.relative[h[0]] ? [b] : j(h.shift(), b); h.length;) a = h.shift(), k.relative[a] && (a += h.shift()), m = u(a, m);
            else {
                !d && h.length > 1 && b.nodeType === 9 && !v && k.match.ID.test(h[0]) && !k.match.ID.test(h[h.length - 1]) && (i = j.find(h.shift(), b, v), b = i.expr ? j.filter(i.expr, i.set)[0] : i.set[0]);
                if (b) {
                    i = d ? {
                        expr: h.pop(),
                        set: n(d)
                    } : j.find(h.pop(), h.length !== 1 || h[0] !== "~" && h[0] !== "+" || !b.parentNode ? b : b.parentNode, v), m = i.expr ? j.filter(i.expr, i.set) : i.set, h.length > 0 ? o = n(m) : q = !1;
                    for (; h.length;) {
                        var x = h.pop();
                        i = x, k.relative[x] ? i = h.pop() : x = "", i == null && (i = b), k.relative[x](o, i, v)
                    }
                } else o = []
            }
            o || (o = m), o || j.error(x || a);
            if (g.call(o) === "[object Array]") if (q) if (b && b.nodeType === 1) for (a = 0; o[a] != null; a++) o[a] && (o[a] === !0 || o[a].nodeType === 1 && r(b, o[a])) && c.push(m[a]);
            else for (a = 0; o[a] != null; a++) o[a] && o[a].nodeType === 1 && c.push(m[a]);
            else c.push.apply(c, o);
            else n(o, c);
            return p && (j(p, f, c, d), j.uniqueSort(c)), c
        };
        j.uniqueSort = function (a) {
            if (p) {
                h = i, a.sort(p);
                if (h) for (var b = 1; b < a.length; b++) a[b] === a[b - 1] && a.splice(b--, 1)
            }
            return a
        }, j.matches = function (a, b) {
            return j(a, null, null, b)
        }, j.find = function (a, b, c) {
            var d, e;
            if (!a) return [];
            for (var f = 0, g = k.order.length; f < g; f++) {
                var h = k.order[f];
                if (e = k.leftMatch[h].exec(a)) {
                    var i = e[1];
                    e.splice(1, 1);
                    if (i.substr(i.length - 1) !== "\\") {
                        e[1] = (e[1] || "").replace(/\\/g, ""), d = k.find[h](e, b, c);
                        if (d != null) {
                            a = a.replace(k.match[h], "");
                            break
                        }
                    }
                }
            }
            return d || (d = b.getElementsByTagName("*")), {
                set: d,
                expr: a
            }
        }, j.filter = function (a, c, d, e) {
            for (var f = a, g = [], h = c, i, l, m = c && c[0] && s(c[0]); a && c.length;) {
                for (var n in k.filter) if ((i = k.leftMatch[n].exec(a)) != null && i[2]) {
                    var o = k.filter[n],
                        p, q;
                    q = i[1], l = !1, i.splice(1, 1);
                    if (q.substr(q.length - 1) !== "\\") {
                        h === g && (g = []);
                        if (k.preFilter[n]) if (i = k.preFilter[n](i, h, d, g, e, m)) {
                            if (i === !0) continue
                        } else l = p = !0;
                        if (i) for (var r = 0;
                        (q = h[r]) != null; r++) if (q) {
                            p = o(q, i, r, h);
                            var t = e ^ !! p;
                            d && p != null ? t ? l = !0 : h[r] = !1 : t && (g.push(q), l = !0)
                        }
                        if (p !== b) {
                            d || (h = g), a = a.replace(k.match[n], "");
                            if (!l) return [];
                            break
                        }
                    }
                }
                if (a === f) if (l == null) j.error(a);
                else break;
                f = a
            }
            return h
        }, j.error = function (a) {
            throw "Syntax error, unrecognized expression: " + a
        };
        var k = j.selectors = {
            order: ["ID", "NAME", "TAG"],
            match: {
                ID: /#((?:[\w\u00c0-\uFFFF-]|\\.)+)/,
                CLASS: /\.((?:[\w\u00c0-\uFFFF-]|\\.)+)/,
                NAME: /\[name=['"]*((?:[\w\u00c0-\uFFFF-]|\\.)+)['"]*\]/,
                ATTR: /\[\s*((?:[\w\u00c0-\uFFFF-]|\\.)+)\s*(?:(\S?=)\s*(['"]*)(.*?)\3|)\s*\]/,
                TAG: /^((?:[\w\u00c0-\uFFFF\*-]|\\.)+)/,
                CHILD: /:(only|nth|last|first)-child(?:\((even|odd|[\dn+-]*)\))?/,
                POS: /:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^-]|$)/,
                PSEUDO: /:((?:[\w\u00c0-\uFFFF-]|\\.)+)(?:\((['"]?)((?:\([^\)]+\)|[^\(\)]*)+)\2\))?/
            },
            leftMatch: {},
            attrMap: {
                "class": "className",
                "for": "htmlFor"
            },
            attrHandle: {
                href: function (a) {
                    return a.getAttribute("href")
                }
            },
            relative: {
                "+": function (a, b) {
                    var c = typeof b == "string",
                        d = c && !/\W/.test(b);
                    c = c && !d, d && (b = b.toLowerCase()), d = 0;
                    for (var e = a.length, f; d < e; d++) if (f = a[d]) {
                        for (;
                        (f = f.previousSibling) && f.nodeType !== 1;);
                        a[d] = c || f && f.nodeName.toLowerCase() === b ? f || !1 : f === b
                    }
                    c && j.filter(b, a, !0)
                },
                ">": function (a, b) {
                    var c = typeof b == "string";
                    if (c && !/\W/.test(b)) {
                        b = b.toLowerCase();
                        for (var d = 0, e = a.length; d < e; d++) {
                            var f = a[d];
                            f && (c = f.parentNode, a[d] = c.nodeName.toLowerCase() === b ? c : !1)
                        }
                    } else {
                        d = 0;
                        for (e = a.length; d < e; d++) if (f = a[d]) a[d] = c ? f.parentNode : f.parentNode === b;
                        c && j.filter(b, a, !0)
                    }
                },
                "": function (a, b, e) {
                    var g = f++,
                        h = d;
                    if (typeof b == "string" && !/\W/.test(b)) {
                        var i = b = b.toLowerCase();
                        h = c
                    }
                    h("parentNode", b, g, a, i, e)
                },
                "~": function (a, b, e) {
                    var g = f++,
                        h = d;
                    if (typeof b == "string" && !/\W/.test(b)) {
                        var i = b = b.toLowerCase();
                        h = c
                    }
                    h("previousSibling", b, g, a, i, e)
                }
            },
            find: {
                ID: function (a, b, c) {
                    if (typeof b.getElementById != "undefined" && !c) return (a = b.getElementById(a[1])) ? [a] : []
                },
                NAME: function (a, b) {
                    if (typeof b.getElementsByName != "undefined") {
                        var c = [];
                        b = b.getElementsByName(a[1]);
                        for (var d = 0, e = b.length; d < e; d++) b[d].getAttribute("name") === a[1] && c.push(b[d]);
                        return c.length === 0 ? null : c
                    }
                },
                TAG: function (a, b) {
                    return b.getElementsByTagName(a[1])
                }
            },
            preFilter: {
                CLASS: function (a, b, c, d, e, f) {
                    a = " " + a[1].replace(/\\/g, "") + " ";
                    if (f) return a;
                    f = 0;
                    for (var g;
                    (g = b[f]) != null; f++) g && (e ^ (g.className && (" " + g.className + " ").replace(/[\t\n]/g, " ").indexOf(a) >= 0) ? c || d.push(g) : c && (b[f] = !1));
                    return !1
                },
                ID: function (a) {
                    return a[1].replace(/\\/g, "")
                },
                TAG: function (a) {
                    return a[1].toLowerCase()
                },
                CHILD: function (a) {
                    if (a[1] === "nth") {
                        var b = /(-?)(\d*)n((?:\+|-)?\d*)/.exec(a[2] === "even" && "2n" || a[2] === "odd" && "2n+1" || !/\D/.test(a[2]) && "0n+" + a[2] || a[2]);
                        a[2] = b[1] + (b[2] || 1) - 0, a[3] = b[3] - 0
                    }
                    return a[0] = f++, a
                },
                ATTR: function (a, b, c, d, e, f) {
                    return b = a[1].replace(/\\/g, ""), !f && k.attrMap[b] && (a[1] = k.attrMap[b]), a[2] === "~=" && (a[4] = " " + a[4] + " "), a
                },
                PSEUDO: function (a, b, c, d, f) {
                    if (a[1] === "not") if ((e.exec(a[3]) || "").length > 1 || /^\w/.test(a[3])) a[3] = j(a[3], null, null, b);
                    else return a = j.filter(a[3], b, c, !0 ^ f), c || d.push.apply(d, a), !1;
                    else if (k.match.POS.test(a[0]) || k.match.CHILD.test(a[0])) return !0;
                    return a
                },
                POS: function (a) {
                    return a.unshift(!0), a
                }
            },
            filters: {
                enabled: function (a) {
                    return a.disabled === !1 && a.type !== "hidden"
                },
                disabled: function (a) {
                    return a.disabled === !0
                },
                checked: function (a) {
                    return a.checked === !0
                },
                selected: function (a) {
                    return a.selected === !0
                },
                parent: function (a) {
                    return !!a.firstChild
                },
                empty: function (a) {
                    return !a.firstChild
                },
                has: function (a, b, c) {
                    return !!j(c[3], a).length
                },
                header: function (a) {
                    return /h\d/i.test(a.nodeName)
                },
                text: function (a) {
                    return "text" === a.type
                },
                radio: function (a) {
                    return "radio" === a.type
                },
                checkbox: function (a) {
                    return "checkbox" === a.type
                },
                file: function (a) {
                    return "file" === a.type
                },
                password: function (a) {
                    return "password" === a.type
                },
                submit: function (a) {
                    return "submit" === a.type
                },
                image: function (a) {
                    return "image" === a.type
                },
                reset: function (a) {
                    return "reset" === a.type
                },
                button: function (a) {
                    return "button" === a.type || a.nodeName.toLowerCase() === "button"
                },
                input: function (a) {
                    return /input|select|textarea|button/i.test(a.nodeName)
                }
            },
            setFilters: {
                first: function (a, b) {
                    return b === 0
                },
                last: function (a, b, c, d) {
                    return b === d.length - 1
                },
                even: function (a, b) {
                    return b % 2 === 0
                },
                odd: function (a, b) {
                    return b % 2 === 1
                },
                lt: function (a, b, c) {
                    return b < c[3] - 0
                },
                gt: function (a, b, c) {
                    return b > c[3] - 0
                },
                nth: function (a, b, c) {
                    return c[3] - 0 === b
                },
                eq: function (a, b, c) {
                    return c[3] - 0 === b
                }
            },
            filter: {
                PSEUDO: function (b, c, d, e) {
                    var f = c[1],
                        g = k.filters[f];
                    if (g) return g(b, d, c, e);
                    if (f === "contains") return (b.textContent || b.innerText || a([b]) || "").indexOf(c[3]) >= 0;
                    if (f === "not") {
                        c = c[3], d = 0;
                        for (e = c.length; d < e; d++) if (c[d] === b) return !1;
                        return !0
                    }
                    j.error("Syntax error, unrecognized expression: " + f)
                },
                CHILD: function (a, b) {
                    var c = b[1],
                        d = a;
                    switch (c) {
                        case "only":
                        case "first":
                            for (; d = d.previousSibling;) if (d.nodeType === 1) return !1;
                            if (c === "first") return !0;
                            d = a;
                        case "last":
                            for (; d = d.nextSibling;) if (d.nodeType === 1) return !1;
                            return !0;
                        case "nth":
                            c = b[2];
                            var e = b[3];
                            if (c === 1 && e === 0) return !0;
                            b = b[0];
                            var f = a.parentNode;
                            if (f && (f.sizcache !== b || !a.nodeIndex)) {
                                var g = 0;
                                for (d = f.firstChild; d; d = d.nextSibling) d.nodeType === 1 && (d.nodeIndex = ++g);
                                f.sizcache = b
                            }
                            return a = a.nodeIndex - e, c === 0 ? a === 0 : a % c === 0 && a / c >= 0
                    }
                },
                ID: function (a, b) {
                    return a.nodeType === 1 && a.getAttribute("id") === b
                },
                TAG: function (a, b) {
                    return b === "*" && a.nodeType === 1 || a.nodeName.toLowerCase() === b
                },
                CLASS: function (a, b) {
                    return (" " + (a.className || a.getAttribute("class")) + " ").indexOf(b) > -1
                },
                ATTR: function (a, b) {
                    var c = b[1];
                    a = k.attrHandle[c] ? k.attrHandle[c](a) : a[c] != null ? a[c] : a.getAttribute(c), c = a + "";
                    var d = b[2];
                    return b = b[4], a == null ? d === "!=" : d === "=" ? c === b : d === "*=" ? c.indexOf(b) >= 0 : d === "~=" ? (" " + c + " ").indexOf(b) >= 0 : b ? d === "!=" ? c !== b : d === "^=" ? c.indexOf(b) === 0 : d === "$=" ? c.substr(c.length - b.length) === b : d === "|=" ? c === b || c.substr(0, b.length + 1) === b + "-" : !1 : c && a !== !1
                },
                POS: function (a, b, c, d) {
                    var e = k.setFilters[b[2]];
                    if (e) return e(a, c, b, d)
                }
            }
        }, l = k.match.POS;
        for (var m in k.match) k.match[m] = new RegExp(k.match[m].source + /(?![^\[]*\])(?![^\(]*\))/.source), k.leftMatch[m] = new RegExp(/(^(?:.|\r|\n)*?)/.source + k.match[m].source.replace(/\\(\d+)/g, function (a, b) {
            return "\\" + (b - 0 + 1)
        }));
        var n = function (a, b) {
            return a = Array.prototype.slice.call(a, 0), b ? (b.push.apply(b, a), b) : a
        };
        try {
            Array.prototype.slice.call(t.documentElement.childNodes, 0)
        } catch (o) {
            n = function (a, b) {
                b = b || [];
                if (g.call(a) === "[object Array]") Array.prototype.push.apply(b, a);
                else if (typeof a.length == "number") for (var c = 0, d = a.length; c < d; c++) b.push(a[c]);
                else for (c = 0; a[c]; c++) b.push(a[c]);
                return b
            }
        }
        var p;
        t.documentElement.compareDocumentPosition ? p = function (a, b) {
            return !a.compareDocumentPosition || !b.compareDocumentPosition ? (a == b && (h = !0), a.compareDocumentPosition ? -1 : 1) : (a = a.compareDocumentPosition(b) & 4 ? -1 : a === b ? 0 : 1, a === 0 && (h = !0), a)
        } : "sourceIndex" in t.documentElement ? p = function (a, b) {
            return !a.sourceIndex || !b.sourceIndex ? (a == b && (h = !0), a.sourceIndex ? -1 : 1) : (a = a.sourceIndex - b.sourceIndex, a === 0 && (h = !0), a)
        } : t.createRange && (p = function (a, b) {
            if (!a.ownerDocument || !b.ownerDocument) return a == b && (h = !0), a.ownerDocument ? -1 : 1;
            var c = a.ownerDocument.createRange(),
                d = b.ownerDocument.createRange();
            return c.setStart(a, 0), c.setEnd(a, 0), d.setStart(b, 0), d.setEnd(b, 0), a = c.compareBoundaryPoints(Range.START_TO_END, d), a === 0 && (h = !0), a
        }),
        function () {
            var a = t.createElement("div"),
                c = "script" + (new Date).getTime();
            a.innerHTML = "<a name='" + c + "'/>";
            var d = t.documentElement;
            d.insertBefore(a, d.firstChild), t.getElementById(c) && (k.find.ID = function (a, c, d) {
                if (typeof c.getElementById != "undefined" && !d) return (c = c.getElementById(a[1])) ? c.id === a[1] || typeof c.getAttributeNode != "undefined" && c.getAttributeNode("id").nodeValue === a[1] ? [c] : b : []
            }, k.filter.ID = function (a, b) {
                var c = typeof a.getAttributeNode != "undefined" && a.getAttributeNode("id");
                return a.nodeType === 1 && c && c.nodeValue === b
            }), d.removeChild(a), d = a = null
        }(),
        function () {
            var a = t.createElement("div");
            a.appendChild(t.createComment("")), a.getElementsByTagName("*").length > 0 && (k.find.TAG = function (a, b) {
                b = b.getElementsByTagName(a[1]);
                if (a[1] === "*") {
                    a = [];
                    for (var c = 0; b[c]; c++) b[c].nodeType === 1 && a.push(b[c]);
                    b = a
                }
                return b
            }), a.innerHTML = "<a href='#'></a>", a.firstChild && typeof a.firstChild.getAttribute != "undefined" && a.firstChild.getAttribute("href") !== "#" && (k.attrHandle.href = function (a) {
                return a.getAttribute("href", 2)
            }), a = null
        }(), t.querySelectorAll && function () {
            var a = j,
                b = t.createElement("div");
            b.innerHTML = "<p class='TEST'></p>";
            if (!b.querySelectorAll || b.querySelectorAll(".TEST").length !== 0) {
                j = function (b, c, d, e) {
                    c = c || t;
                    if (!e && c.nodeType === 9 && !s(c)) try {
                        return n(c.querySelectorAll(b), d)
                    } catch (f) {}
                    return a(b, c, d, e)
                };
                for (var c in a) j[c] = a[c];
                b = null
            }
        }(),
        function () {
            var a = t.createElement("div");
            a.innerHTML = "<div class='test e'></div><div class='test'></div>", !! a.getElementsByClassName && a.getElementsByClassName("e").length !== 0 && (a.lastChild.className = "e", a.getElementsByClassName("e").length !== 1 && (k.order.splice(1, 0, "CLASS"), k.find.CLASS = function (a, b, c) {
                if (typeof b.getElementsByClassName != "undefined" && !c) return b.getElementsByClassName(a[1])
            }, a = null))
        }();
        var r = t.compareDocumentPosition ? function (a, b) {
                return !!(a.compareDocumentPosition(b) & 16)
            } : function (a, b) {
                return a !== b && (a.contains ? a.contains(b) : !0)
            }, s = function (a) {
                return (a = (a ? a.ownerDocument || a : 0).documentElement) ? a.nodeName !== "HTML" : !1
            }, u = function (a, b) {
                var c = [],
                    d = "",
                    e;
                for (b = b.nodeType ? [b] : b; e = k.match.PSEUDO.exec(a);) d += e[0], a = a.replace(k.match.PSEUDO, "");
                a = k.relative[a] ? a + "*" : a, e = 0;
                for (var f = b.length; e < f; e++) j(a, b[e], c);
                return j.filter(d, c)
            };
        q.find = j, q.expr = j.selectors, q.expr[":"] = q.expr.filters, q.unique = j.uniqueSort, q.text = a, q.isXMLDoc = s, q.contains = r
    }();
    var bc = /Until$/,
        bd = /^(?:parents|prevUntil|prevAll)/,
        be = /,/;
    H = Array.prototype.slice;
    var bf = function (a, b, c) {
        if (q.isFunction(b)) return q.grep(a, function (a, d) {
            return !!b.call(a, d, a) === c
        });
        if (b.nodeType) return q.grep(a, function (a) {
            return a === b === c
        });
        if (typeof b == "string") {
            var d = q.grep(a, function (a) {
                return a.nodeType === 1
            });
            if (w.test(b)) return q.filter(b, d, !c);
            b = q.filter(b, d)
        }
        return q.grep(a, function (a) {
            return q.inArray(a, b) >= 0 === c
        })
    };
    q.fn.extend({
        find: function (a) {
            for (var b = this.pushStack("", "find", a), c = 0, d = 0, e = this.length; d < e; d++) {
                c = b.length, q.find(a, this[d], b);
                if (d > 0) for (var f = c; f < b.length; f++) for (var g = 0; g < c; g++) if (b[g] === b[f]) {
                    b.splice(f--, 1);
                    break
                }
            }
            return b
        },
        has: function (a) {
            var b = q(a);
            return this.filter(function () {
                for (var a = 0, c = b.length; a < c; a++) if (q.contains(this, b[a])) return !0
            })
        },
        not: function (a) {
            return this.pushStack(bf(this, a, !1), "not", a)
        },
        filter: function (a) {
            return this.pushStack(bf(this, a, !0), "filter", a)
        },
        is: function (a) {
            return !!a && q.filter(a, this).length > 0
        },
        closest: function (a, b) {
            if (q.isArray(a)) {
                var c = [],
                    d = this[0],
                    e, f = {}, g;
                if (d && a.length) {
                    e = 0;
                    for (var h = a.length; e < h; e++) g = a[e], f[g] || (f[g] = q.expr.match.POS.test(g) ? q(g, b || this.context) : g);
                    for (; d && d.ownerDocument && d !== b;) {
                        for (g in f) {
                            e = f[g];
                            if (e.jquery ? e.index(d) > -1 : q(d).is(e)) c.push({
                                selector: g,
                                elem: d
                            }), delete f[g]
                        }
                        d = d.parentNode
                    }
                }
                return c
            }
            var i = q.expr.match.POS.test(a) ? q(a, b || this.context) : null;
            return this.map(function (c, d) {
                for (; d && d.ownerDocument && d !== b;) {
                    if (i ? i.index(d) > -1 : q(d).is(a)) return d;
                    d = d.parentNode
                }
                return null
            })
        },
        index: function (a) {
            return !a || typeof a == "string" ? q.inArray(this[0], a ? q(a) : this.parent().children()) : q.inArray(a.jquery ? a[0] : a, this)
        },
        add: function (a, b) {
            return a = typeof a == "string" ? q(a, b || this.context) : q.makeArray(a), b = q.merge(this.get(), a), this.pushStack(l(a[0]) || l(b[0]) ? b : q.unique(b))
        },
        andSelf: function () {
            return this.add(this.prevObject)
        }
    }), q.each({
        parent: function (a) {
            return (a = a.parentNode) && a.nodeType !== 11 ? a : null
        },
        parents: function (a) {
            return q.dir(a, "parentNode")
        },
        parentsUntil: function (a, b, c) {
            return q.dir(a, "parentNode", c)
        },
        next: function (a) {
            return q.nth(a, 2, "nextSibling")
        },
        prev: function (a) {
            return q.nth(a, 2, "previousSibling")
        },
        nextAll: function (a) {
            return q.dir(a, "nextSibling")
        },
        prevAll: function (a) {
            return q.dir(a, "previousSibling")
        },
        nextUntil: function (a, b, c) {
            return q.dir(a, "nextSibling", c)
        },
        prevUntil: function (a, b, c) {
            return q.dir(a, "previousSibling", c)
        },
        siblings: function (a) {
            return q.sibling(a.parentNode.firstChild, a)
        },
        children: function (a) {
            return q.sibling(a.firstChild)
        },
        contents: function (a) {
            return q.nodeName(a, "iframe") ? a.contentDocument || a.contentWindow.document : q.makeArray(a.childNodes)
        }
    }, function (a, b) {
        q.fn[a] = function (c, d) {
            var e = q.map(this, b, c);
            return bc.test(a) || (d = c), d && typeof d == "string" && (e = q.filter(d, e)), e = this.length > 1 ? q.unique(e) : e, (this.length > 1 || be.test(d)) && bd.test(a) && (e = e.reverse()), this.pushStack(e, a, H.call(arguments).join(","))
        }
    }), q.extend({
        filter: function (a, b, c) {
            return c && (a = ":not(" + a + ")"), q.find.matches(a, b)
        },
        dir: function (a, c, d) {
            var e = [];
            for (a = a[c]; a && a.nodeType !== 9 && (d === b || a.nodeType !== 1 || !q(a).is(d));) a.nodeType === 1 && e.push(a), a = a[c];
            return e
        },
        nth: function (a, b, c) {
            b = b || 1;
            for (var d = 0; a; a = a[c]) if (a.nodeType === 1 && ++d === b) break;
            return a
        },
        sibling: function (a, b) {
            for (var c = []; a; a = a.nextSibling) a.nodeType === 1 && a !== b && c.push(a);
            return c
        }
    });
    var bg = / jQuery\d+="(?:\d+|null)"/g,
        bh = /^\s+/,
        bi = /(<([\w:]+)[^>]*?)\/>/g,
        bj = /^(?:area|br|col|embed|hr|img|input|link|meta|param)$/i,
        bk = /<([\w:]+)/,
        bl = /<tbody/i,
        bm = /<|&#?\w+;/,
        bn = /<script|<object|<embed|<option|<style/i,
        bo = /checked\s*(?:[^=]|=\s*.checked.)/i,
        bp = function (a, b, c) {
            return bj.test(c) ? a : b + "></" + c + ">"
        }, bq = {
            option: [1, "<select multiple='multiple'>", "</select>"],
            legend: [1, "<fieldset>", "</fieldset>"],
            thead: [1, "<table>", "</table>"],
            tr: [2, "<table><tbody>", "</tbody></table>"],
            td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
            col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
            area: [1, "<map>", "</map>"],
            _default: [0, "", ""]
        };
    bq.optgroup = bq.option, bq.tbody = bq.tfoot = bq.colgroup = bq.caption = bq.thead, bq.th = bq.td, q.support.htmlSerialize || (bq._default = [1, "div<div>", "</div>"]), q.fn.extend({
        text: function (a) {
            return q.isFunction(a) ? this.each(function (b) {
                var c = q(this);
                c.text(a.call(this, b, c.text()))
            }) : typeof a != "object" && a !== b ? this.empty().append((this[0] && this[0].ownerDocument || t).createTextNode(a)) : q.text(this)
        },
        wrapAll: function (a) {
            if (q.isFunction(a)) return this.each(function (b) {
                q(this).wrapAll(a.call(this, b))
            });
            if (this[0]) {
                var b = q(a, this[0].ownerDocument).eq(0).clone(!0);
                this[0].parentNode && b.insertBefore(this[0]), b.map(function () {
                    for (var a = this; a.firstChild && a.firstChild.nodeType === 1;) a = a.firstChild;
                    return a
                }).append(this)
            }
            return this
        },
        wrapInner: function (a) {
            return q.isFunction(a) ? this.each(function (b) {
                q(this).wrapInner(a.call(this, b))
            }) : this.each(function () {
                var b = q(this),
                    c = b.contents();
                c.length ? c.wrapAll(a) : b.append(a)
            })
        },
        wrap: function (a) {
            return this.each(function () {
                q(this).wrapAll(a)
            })
        },
        unwrap: function () {
            return this.parent().each(function () {
                q.nodeName(this, "body") || q(this).replaceWith(this.childNodes)
            }).end()
        },
        append: function () {
            return this.domManip(arguments, !0, function (a) {
                this.nodeType === 1 && this.appendChild(a)
            })
        },
        prepend: function () {
            return this.domManip(arguments, !0, function (a) {
                this.nodeType === 1 && this.insertBefore(a, this.firstChild)
            })
        },
        before: function () {
            if (this[0] && this[0].parentNode) return this.domManip(arguments, !1, function (a) {
                this.parentNode.insertBefore(a, this)
            });
            if (arguments.length) {
                var a = q(arguments[0]);
                return a.push.apply(a, this.toArray()), this.pushStack(a, "before", arguments)
            }
        },
        after: function () {
            if (this[0] && this[0].parentNode) return this.domManip(arguments, !1, function (a) {
                this.parentNode.insertBefore(a, this.nextSibling)
            });
            if (arguments.length) {
                var a = this.pushStack(this, "after", arguments);
                return a.push.apply(a, q(arguments[0]).toArray()), a
            }
        },
        remove: function (a, b) {
            for (var c = 0, d;
            (d = this[c]) != null; c++) if (!a || q.filter(a, [d]).length)!b && d.nodeType === 1 && (q.cleanData(d.getElementsByTagName("*")), q.cleanData([d])), d.parentNode && d.parentNode.removeChild(d);
            return this
        },
        empty: function () {
            for (var a = 0, b;
            (b = this[a]) != null; a++) for (b.nodeType === 1 && q.cleanData(b.getElementsByTagName("*")); b.firstChild;) b.removeChild(b.firstChild);
            return this
        },
        clone: function (a) {
            var b = this.map(function () {
                if (!q.support.noCloneEvent && !q.isXMLDoc(this)) {
                    var a = this.outerHTML,
                        b = this.ownerDocument;
                    return a || (a = b.createElement("div"), a.appendChild(this.cloneNode(!0)), a = a.innerHTML), q.clean([a.replace(bg, "").replace(/=([^="'>\s]+\/)>/g, '="$1">').replace(bh, "")], b)[0]
                }
                return this.cloneNode(!0)
            });
            return a === !0 && (m(this, b), m(this.find("*"), b.find("*"))), b
        },
        html: function (a) {
            if (a === b) return this[0] && this[0].nodeType === 1 ? this[0].innerHTML.replace(bg, "") : null;
            if (typeof a == "string" && !bn.test(a) && (q.support.leadingWhitespace || !bh.test(a)) && !bq[(bk.exec(a) || ["", ""])[1].toLowerCase()]) {
                a = a.replace(bi, bp);
                try {
                    for (var c = 0, d = this.length; c < d; c++) this[c].nodeType === 1 && (q.cleanData(this[c].getElementsByTagName("*")), this[c].innerHTML = a)
                } catch (e) {
                    this.empty().append(a)
                }
            } else q.isFunction(a) ? this.each(function (b) {
                var c = q(this),
                    d = c.html();
                c.empty().append(function () {
                    return a.call(this, b, d)
                })
            }) : this.empty().append(a);
            return this
        },
        replaceWith: function (a) {
            return this[0] && this[0].parentNode ? q.isFunction(a) ? this.each(function (b) {
                var c = q(this),
                    d = c.html();
                c.replaceWith(a.call(this, b, d))
            }) : (typeof a != "string" && (a = q(a).detach()), this.each(function () {
                var b = this.nextSibling,
                    c = this.parentNode;
                q(this).remove(), b ? q(b).before(a) : q(c).append(a)
            })) : this.pushStack(q(q.isFunction(a) ? a() : a), "replaceWith", a)
        },
        detach: function (a) {
            return this.remove(a, !0)
        },
        domManip: function (a, c, e) {
            function f(a) {
                return q.nodeName(a, "table") ? a.getElementsByTagName("tbody")[0] || a.appendChild(a.ownerDocument.createElement("tbody")) : a
            }
            var g, h, i = a[0],
                j = [],
                k;
            if (!q.support.checkClone && arguments.length === 3 && typeof i == "string" && bo.test(i)) return this.each(function () {
                q(this).domManip(a, c, e, !0)
            });
            if (q.isFunction(i)) return this.each(function (d) {
                var f = q(this);
                a[0] = i.call(this, d, c ? f.html() : b), f.domManip(a, c, e)
            });
            if (this[0]) {
                g = i && i.parentNode, g = q.support.parentNode && g && g.nodeType === 11 && g.childNodes.length === this.length ? {
                    fragment: g
                } : n(a, this, j), k = g.fragment;
                if (h = k.childNodes.length === 1 ? k = k.firstChild : k.firstChild) {
                    c = c && q.nodeName(h, "tr");
                    for (var l = 0, m = this.length; l < m; l++) e.call(c ? f(this[l], h) : this[l], l > 0 || g.cacheable || this.length > 1 ? k.cloneNode(!0) : k)
                }
                j.length && q.each(j, d)
            }
            return this
        }
    }), q.fragments = {}, q.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function (a, b) {
        q.fn[a] = function (c) {
            var d = [];
            c = q(c);
            var e = this.length === 1 && this[0].parentNode;
            if (e && e.nodeType === 11 && e.childNodes.length === 1 && c.length === 1) return c[b](this[0]), this;
            e = 0;
            for (var f = c.length; e < f; e++) {
                var g = (e > 0 ? this.clone(!0) : this).get();
                q.fn[b].apply(q(c[e]), g), d = d.concat(g)
            }
            return this.pushStack(d, a, c.selector)
        }
    }), q.extend({
        clean: function (a, b, c, d) {
            b = b || t, typeof b.createElement == "undefined" && (b = b.ownerDocument || b[0] && b[0].ownerDocument || t);
            for (var e = [], f = 0, g;
            (g = a[f]) != null; f++) {
                typeof g == "number" && (g += "");
                if (g) {
                    if (typeof g == "string" && !bm.test(g)) g = b.createTextNode(g);
                    else if (typeof g == "string") {
                        g = g.replace(bi, bp);
                        var h = (bk.exec(g) || ["", ""])[1].toLowerCase(),
                            i = bq[h] || bq._default,
                            j = i[0],
                            k = b.createElement("div");
                        for (k.innerHTML = i[1] + g + i[2]; j--;) k = k.lastChild;
                        if (!q.support.tbody) {
                            j = bl.test(g), h = h === "table" && !j ? k.firstChild && k.firstChild.childNodes : i[1] === "<table>" && !j ? k.childNodes : [];
                            for (i = h.length - 1; i >= 0; --i) q.nodeName(h[i], "tbody") && !h[i].childNodes.length && h[i].parentNode.removeChild(h[i])
                        }!q.support.leadingWhitespace && bh.test(g) && k.insertBefore(b.createTextNode(bh.exec(g)[0]), k.firstChild), g = k.childNodes
                    }
                    g.nodeType ? e.push(g) : e = q.merge(e, g)
                }
            }
            if (c) for (f = 0; e[f]; f++) d && q.nodeName(e[f], "script") && (!e[f].type || e[f].type.toLowerCase() === "text/javascript") ? d.push(e[f].parentNode ? e[f].parentNode.removeChild(e[f]) : e[f]) : (e[f].nodeType === 1 && e.splice.apply(e, [f + 1, 0].concat(q.makeArray(e[f].getElementsByTagName("script")))), c.appendChild(e[f]));
            return e
        },
        cleanData: function (a) {
            for (var b, c, d = q.cache, e = q.event.special, f = q.support.deleteExpando, g = 0, h;
            (h = a[g]) != null; g++) if (c = h[q.expando]) {
                b = d[c];
                if (b.events) for (var i in b.events) e[i] ? q.event.remove(h, i) : W(h, i, b.handle);
                f ? delete h[q.expando] : h.removeAttribute && h.removeAttribute(q.expando), delete d[c]
            }
        }
    });
    var br = /z-?index|font-?weight|opacity|zoom|line-?height/i,
        bs = /alpha\([^)]*\)/,
        bt = /opacity=([^)]*)/,
        bu = /float/i,
        bv = /-([a-z])/ig,
        bw = /([A-Z])/g,
        bx = /^-?\d+(?:px)?$/i,
        by = /^-?\d/,
        bz = {
            position: "absolute",
            visibility: "hidden",
            display: "block"
        }, bA = ["Left", "Right"],
        bB = ["Top", "Bottom"],
        bC = t.defaultView && t.defaultView.getComputedStyle,
        bD = q.support.cssFloat ? "cssFloat" : "styleFloat",
        bE = function (a, b) {
            return b.toUpperCase()
        };
    q.fn.css = function (a, c) {
        return e(this, a, c, !0, function (a, c, d) {
            if (d === b) return q.curCSS(a, c);
            typeof d == "number" && !br.test(c) && (d += "px"), q.style(a, c, d)
        })
    }, q.extend({
        style: function (a, c, d) {
            if (!a || a.nodeType === 3 || a.nodeType === 8) return b;
            (c === "width" || c === "height") && parseFloat(d) < 0 && (d = b);
            var e = a.style || a,
                f = d !== b;
            return !q.support.opacity && c === "opacity" ? (f && (e.zoom = 1, c = parseInt(d, 10) + "" == "NaN" ? "" : "alpha(opacity=" + d * 100 + ")", a = e.filter || q.curCSS(a, "filter") || "", e.filter = bs.test(a) ? a.replace(bs, c) : c), e.filter && e.filter.indexOf("opacity=") >= 0 ? parseFloat(bt.exec(e.filter)[1]) / 100 + "" : "") : (bu.test(c) && (c = bD), c = c.replace(bv, bE), f && (e[c] = d), e[c])
        },
        css: function (a, b, c, d) {
            if (b === "width" || b === "height") {
                var e, f = b === "width" ? bA : bB;

                function g() {
                    e = b === "width" ? a.offsetWidth : a.offsetHeight, d !== "border" && q.each(f, function () {
                        d || (e -= parseFloat(q.curCSS(a, "padding" + this, !0)) || 0), d === "margin" ? e += parseFloat(q.curCSS(a, "margin" + this, !0)) || 0 : e -= parseFloat(q.curCSS(a, "border" + this + "Width", !0)) || 0
                    })
                }
                return a.offsetWidth !== 0 ? g() : q.swap(a, bz, g), Math.max(0, Math.round(e))
            }
            return q.curCSS(a, b, c)
        },
        curCSS: function (a, b, c) {
            var d, e = a.style;
            if (!q.support.opacity && b === "opacity" && a.currentStyle) return d = bt.test(a.currentStyle.filter || "") ? parseFloat(RegExp.$1) / 100 + "" : "", d === "" ? "1" : d;
            bu.test(b) && (b = bD);
            if (!c && e && e[b]) d = e[b];
            else if (bC) {
                bu.test(b) && (b = "float"), b = b.replace(bw, "-$1").toLowerCase(), e = a.ownerDocument.defaultView;
                if (!e) return null;
                if (a = e.getComputedStyle(a, null)) d = a.getPropertyValue(b);
                b === "opacity" && d === "" && (d = "1")
            } else if (a.currentStyle) {
                c = b.replace(bv, bE), d = a.currentStyle[b] || a.currentStyle[c];
                if (!bx.test(d) && by.test(d)) {
                    b = e.left;
                    var f = a.runtimeStyle.left;
                    a.runtimeStyle.left = a.currentStyle.left, e.left = c === "fontSize" ? "1em" : d || 0, d = e.pixelLeft + "px", e.left = b, a.runtimeStyle.left = f
                }
            }
            return d
        },
        swap: function (a, b, c) {
            var d = {};
            for (var e in b) d[e] = a.style[e], a.style[e] = b[e];
            c.call(a);
            for (e in b) a.style[e] = d[e]
        }
    }), q.expr && q.expr.filters && (q.expr.filters.hidden = function (a) {
        var b = a.offsetWidth,
            c = a.offsetHeight,
            d = a.nodeName.toLowerCase() === "tr";
        return b === 0 && c === 0 && !d ? !0 : b > 0 && c > 0 && !d ? !1 : q.curCSS(a, "display") === "none"
    }, q.expr.filters.visible = function (a) {
        return !q.expr.filters.hidden(a)
    });
    var bF = f(),
        bG = /<script(.|\s)*?\/script>/gi,
        bH = /select|textarea/i,
        bI = /color|date|datetime|email|hidden|month|number|password|range|search|tel|text|time|url|week/i,
        bJ = /=\?(&|$)/,
        bK = /\?/,
        bL = /(\?|&)_=.*?(&|$)/,
        bM = /^(\w+:)?\/\/([^\/?#]+)/,
        bN = /%20/g,
        bO = q.fn.load;
    q.fn.extend({
        load: function (a, b, c) {
            if (typeof a != "string") return bO.call(this, a);
            if (!this.length) return this;
            var d = a.indexOf(" ");
            if (d >= 0) {
                var e = a.slice(d, a.length);
                a = a.slice(0, d)
            }
            d = "GET", b && (q.isFunction(b) ? (c = b, b = null) : typeof b == "object" && (b = q.param(b, q.ajaxSettings.traditional), d = "POST"));
            var f = this;
            return q.ajax({
                url: a,
                type: d,
                dataType: "html",
                data: b,
                complete: function (a, b) {
                    (b === "success" || b === "notmodified") && f.html(e ? q("<div />").append(a.responseText.replace(bG, "")).find(e) : a.responseText), c && f.each(c, [a.responseText, b, a])
                }
            }), this
        },
        serialize: function () {
            return q.param(this.serializeArray())
        },
        serializeArray: function () {
            return this.map(function () {
                return this.elements ? q.makeArray(this.elements) : this
            }).filter(function () {
                return this.name && !this.disabled && (this.checked || bH.test(this.nodeName) || bI.test(this.type))
            }).map(function (a, b) {
                return a = q(this).val(), a == null ? null : q.isArray(a) ? q.map(a, function (a) {
                    return {
                        name: b.name,
                        value: a
                    }
                }) : {
                    name: b.name,
                    value: a
                }
            }).get()
        }
    }), q.each("ajaxStart ajaxStop ajaxComplete ajaxError ajaxSuccess ajaxSend".split(" "), function (a, b) {
        q.fn[b] = function (a) {
            return this.bind(b, a)
        }
    }), q.extend({
        get: function (a, b, c, d) {
            return q.isFunction(b) && (d = d || c, c = b, b = null), q.ajax({
                type: "GET",
                url: a,
                data: b,
                success: c,
                dataType: d
            })
        },
        getScript: function (a, b) {
            return q.get(a, null, b, "script")
        },
        getJSON: function (a, b, c) {
            return q.get(a, b, c, "json")
        },
        post: function (a, b, c, d) {
            return q.isFunction(b) && (d = d || c, c = b, b = {}), q.ajax({
                type: "POST",
                url: a,
                data: b,
                success: c,
                dataType: d
            })
        },
        ajaxSetup: function (a) {
            q.extend(q.ajaxSettings, a)
        },
        ajaxSettings: {
            url: location.href,
            global: !0,
            type: "GET",
            contentType: "application/x-www-form-urlencoded",
            processData: !0,
            async: !0,
            xhr: a.XMLHttpRequest && (a.location.protocol !== "file:" || !a.ActiveXObject) ? function () {
                return new a.XMLHttpRequest
            } : function () {
                try {
                    return new a.ActiveXObject("Microsoft.XMLHTTP")
                } catch (b) {}
            },
            accepts: {
                xml: "application/xml, text/xml",
                html: "text/html",
                script: "text/javascript, application/javascript",
                json: "application/json, text/javascript",
                text: "text/plain",
                _default: "*/*"
            }
        },
        lastModified: {},
        etag: {},
        ajax: function (c) {
            function d() {
                h.success && h.success.call(l, k, j, v), h.global && g("ajaxSuccess", [v, h])
            }
            function e() {
                h.complete && h.complete.call(l, v, j), h.global && g("ajaxComplete", [v, h]), h.global && !--q.active && q.event.trigger("ajaxStop")
            }
            function g(a, b) {
                (h.context ? q(h.context) : q.event).trigger(a, b)
            }
            var h = q.extend(!0, {}, q.ajaxSettings, c),
                i, j, k, l = c && c.context || h,
                m = h.type.toUpperCase();
            h.data && h.processData && typeof h.data != "string" && (h.data = q.param(h.data, h.traditional));
            if (h.dataType === "jsonp") {
                if (m === "GET") bJ.test(h.url) || (h.url += (bK.test(h.url) ? "&" : "?") + (h.jsonp || "callback") + "=?");
                else if (!h.data || !bJ.test(h.data)) h.data = (h.data ? h.data + "&" : "") + (h.jsonp || "callback") + "=?";
                h.dataType = "json"
            }
            h.dataType === "json" && (h.data && bJ.test(h.data) || bJ.test(h.url)) && (i = h.jsonpCallback || "jsonp" + bF++, h.data && (h.data = (h.data + "").replace(bJ, "=" + i + "$1")), h.url = h.url.replace(bJ, "=" + i + "$1"), h.dataType = "script", a[i] = a[i] || function (c) {
                k = c, d(), e(), a[i] = b;
                try {
                    delete a[i]
                } catch (f) {}
                p && p.removeChild(r)
            }), h.dataType === "script" && h.cache === null && (h.cache = !1);
            if (h.cache === !1 && m === "GET") {
                var n = f(),
                    o = h.url.replace(bL, "$1_=" + n + "$2");
                h.url = o + (o === h.url ? (bK.test(h.url) ? "&" : "?") + "_=" + n : "")
            }
            h.data && m === "GET" && (h.url += (bK.test(h.url) ? "&" : "?") + h.data), h.global && !(q.active++) && q.event.trigger("ajaxStart"), n = (n = bM.exec(h.url)) && (n[1] && n[1] !== location.protocol || n[2] !== location.host);
            if (h.dataType === "script" && m === "GET" && n) {
                var p = t.getElementsByTagName("head")[0] || t.documentElement,
                    r = t.createElement("script");
                r.src = h.url, h.scriptCharset && (r.charset = h.scriptCharset);
                if (!i) {
                    var s = !1;
                    r.onload = r.onreadystatechange = function () {
                        !s && (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") && (s = !0, d(), e(), r.onload = r.onreadystatechange = null, p && r.parentNode && p.removeChild(r))
                    }
                }
                return p.insertBefore(r, p.firstChild), b
            }
            var u = !1,
                v = h.xhr();
            if (v) {
                h.username ? v.open(m, h.url, h.async, h.username, h.password) : v.open(m, h.url, h.async);
                try {
                    (h.data || c && c.contentType) && v.setRequestHeader("Content-Type", h.contentType), h.ifModified && (q.lastModified[h.url] && v.setRequestHeader("If-Modified-Since", q.lastModified[h.url]), q.etag[h.url] && v.setRequestHeader("If-None-Match", q.etag[h.url])), n || v.setRequestHeader("X-Requested-With", "XMLHttpRequest"), v.setRequestHeader("Accept", h.dataType && h.accepts[h.dataType] ? h.accepts[h.dataType] + ", */*" : h.accepts._default)
                } catch (w) {}
                if (h.beforeSend && h.beforeSend.call(l, v, h) === !1) return h.global && !--q.active && q.event.trigger("ajaxStop"), v.abort(), !1;
                h.global && g("ajaxSend", [v, h]);
                var x = v.onreadystatechange = function (a) {
                    if (!v || v.readyState === 0 || a === "abort") u || e(), u = !0, v && (v.onreadystatechange = q.noop);
                    else if (!u && v && (v.readyState === 4 || a === "timeout")) {
                        u = !0, v.onreadystatechange = q.noop, j = a === "timeout" ? "timeout" : q.httpSuccess(v) ? h.ifModified && q.httpNotModified(v, h.url) ? "notmodified" : "success" : "error";
                        var b;
                        if (j === "success") try {
                            k = q.httpData(v, h.dataType, h)
                        } catch (c) {
                            j = "parsererror", b = c
                        }
                        j === "success" || j === "notmodified" ? i || d() : q.handleError(h, v, j, b), e(), a === "timeout" && v.abort(), h.async && (v = null)
                    }
                };
                try {
                    var y = v.abort;
                    v.abort = function () {
                        v && y.call(v), x("abort")
                    }
                } catch (z) {}
                h.async && h.timeout > 0 && setTimeout(function () {
                    v && !u && x("timeout")
                }, h.timeout);
                try {
                    v.send(m === "POST" || m === "PUT" || m === "DELETE" ? h.data : null)
                } catch (A) {
                    q.handleError(h, v, null, A), e()
                }
                return h.async || x(), v
            }
        },
        handleError: function (a, b, c, d) {
            a.error && a.error.call(a.context || a, b, c, d), a.global && (a.context ? q(a.context) : q.event).trigger("ajaxError", [b, a, d])
        },
        active: 0,
        httpSuccess: function (a) {
            try {
                return !a.status && location.protocol === "file:" || a.status >= 200 && a.status < 300 || a.status === 304 || a.status === 1223 || a.status === 0
            } catch (b) {}
            return !1
        },
        httpNotModified: function (a, b) {
            var c = a.getResponseHeader("Last-Modified"),
                d = a.getResponseHeader("Etag");
            return c && (q.lastModified[b] = c), d && (q.etag[b] = d), a.status === 304 || a.status === 0
        },
        httpData: function (a, b, c) {
            var d = a.getResponseHeader("content-type") || "",
                e = b === "xml" || !b && d.indexOf("xml") >= 0;
            return a = e ? a.responseXML : a.responseText, e && a.documentElement.nodeName === "parsererror" && q.error("parsererror"), c && c.dataFilter && (a = c.dataFilter(a, b)), typeof a == "string" && (b === "json" || !b && d.indexOf("json") >= 0 ? a = q.parseJSON(a) : (b === "script" || !b && d.indexOf("javascript") >= 0) && q.globalEval(a)), a
        },
        param: function (a, c) {
            function d(a, b) {
                q.isArray(b) ? q.each(b, function (b, f) {
                    c || /\[\]$/.test(a) ? e(a, f) : d(a + "[" + (typeof f == "object" || q.isArray(f) ? b : "") + "]", f)
                }) : !c && b != null && typeof b == "object" ? q.each(b, function (b, c) {
                    d(a + "[" + b + "]", c)
                }) : e(a, b)
            }
            function e(a, b) {
                b = q.isFunction(b) ? b() : b, f[f.length] = encodeURIComponent(a) + "=" + encodeURIComponent(b)
            }
            var f = [];
            c === b && (c = q.ajaxSettings.traditional);
            if (q.isArray(a) || a.jquery) q.each(a, function () {
                e(this.name, this.value)
            });
            else for (var g in a) d(g, a[g]);
            return f.join("&").replace(bN, "+")
        }
    });
    var bP = {}, bQ = /toggle|show|hide/,
        bR = /^([+-]=)?([\d+-.]+)(.*)$/,
        bS, bT = [
            ["height", "marginTop", "marginBottom", "paddingTop", "paddingBottom"],
            ["width", "marginLeft", "marginRight", "paddingLeft", "paddingRight"],
            ["opacity"]
        ];
    q.fn.extend({
        show: function (a, b) {
            if (a || a === 0) return this.animate(o("show", 3), a, b);
            a = 0;
            for (b = this.length; a < b; a++) {
                var c = q.data(this[a], "olddisplay");
                this[a].style.display = c || "";
                if (q.css(this[a], "display") === "none") {
                    c = this[a].nodeName;
                    var d;
                    if (bP[c]) d = bP[c];
                    else {
                        var e = q("<" + c + " />").appendTo("body");
                        d = e.css("display"), d === "none" && (d = "block"), e.remove(), bP[c] = d
                    }
                    q.data(this[a], "olddisplay", d)
                }
            }
            a = 0;
            for (b = this.length; a < b; a++) this[a].style.display = q.data(this[a], "olddisplay") || "";
            return this
        },
        hide: function (a, b) {
            if (a || a === 0) return this.animate(o("hide", 3), a, b);
            a = 0;
            for (b = this.length; a < b; a++) {
                var c = q.data(this[a], "olddisplay");
                !c && c !== "none" && q.data(this[a], "olddisplay", q.css(this[a], "display"))
            }
            a = 0;
            for (b = this.length; a < b; a++) this[a].style.display = "none";
            return this
        },
        _toggle: q.fn.toggle,
        toggle: function (a, b) {
            var c = typeof a == "boolean";
            return q.isFunction(a) && q.isFunction(b) ? this._toggle.apply(this, arguments) : a == null || c ? this.each(function () {
                var b = c ? a : q(this).is(":hidden");
                q(this)[b ? "show" : "hide"]()
            }) : this.animate(o("toggle", 3), a, b), this
        },
        fadeTo: function (a, b, c) {
            return this.filter(":hidden").css("opacity", 0).show().end().animate({
                opacity: b
            }, a, c)
        },
        animate: function (a, b, c, d) {
            var e = q.speed(b, c, d);
            return q.isEmptyObject(a) ? this.each(e.complete) : this[e.queue === !1 ? "each" : "queue"](function () {
                var b = q.extend({}, e),
                    c, d = this.nodeType === 1 && q(this).is(":hidden"),
                    f = this;
                for (c in a) {
                    var g = c.replace(bv, bE);
                    c !== g && (a[g] = a[c], delete a[c], c = g);
                    if (a[c] === "hide" && d || a[c] === "show" && !d) return b.complete.call(this);
                    (c === "height" || c === "width") && this.style && (b.display = q.css(this, "display"), b.overflow = this.style.overflow), q.isArray(a[c]) && ((b.specialEasing = b.specialEasing || {})[c] = a[c][1], a[c] = a[c][0])
                }
                return b.overflow != null && (this.style.overflow = "hidden"), b.curAnim = q.extend({}, a), q.each(a, function (c, e) {
                    var g = new q.fx(f, b, c);
                    if (bQ.test(e)) g[e === "toggle" ? d ? "show" : "hide" : e](a);
                    else {
                        var h = bR.exec(e),
                            i = g.cur(!0) || 0;
                        if (h) {
                            e = parseFloat(h[2]);
                            var j = h[3] || "px";
                            j !== "px" && (f.style[c] = (e || 1) + j, i = (e || 1) / g.cur(!0) * i, f.style[c] = i + j), h[1] && (e = (h[1] === "-=" ? -1 : 1) * e + i), g.custom(i, e, j)
                        } else g.custom(i, e, "")
                    }
                }), !0
            })
        },
        stop: function (a, b) {
            var c = q.timers;
            return a && this.queue([]), this.each(function () {
                for (var a = c.length - 1; a >= 0; a--) c[a].elem === this && (b && c[a](!0), c.splice(a, 1))
            }), b || this.dequeue(), this
        }
    }), q.each({
        slideDown: o("show", 1),
        slideUp: o("hide", 1),
        slideToggle: o("toggle", 1),
        fadeIn: {
            opacity: "show"
        },
        fadeOut: {
            opacity: "hide"
        }
    }, function (a, b) {
        q.fn[a] = function (a, c) {
            return this.animate(b, a, c)
        }
    }), q.extend({
        speed: function (a, b, c) {
            var d = a && typeof a == "object" ? a : {
                complete: c || !c && b || q.isFunction(a) && a,
                duration: a,
                easing: c && b || b && !q.isFunction(b) && b
            };
            return d.duration = q.fx.off ? 0 : typeof d.duration == "number" ? d.duration : q.fx.speeds[d.duration] || q.fx.speeds._default, d.old = d.complete, d.complete = function () {
                d.queue !== !1 && q(this).dequeue(), q.isFunction(d.old) && d.old.call(this)
            }, d
        },
        easing: {
            linear: function (a, b, c, d) {
                return c + d * a
            },
            swing: function (a, b, c, d) {
                return (-Math.cos(a * Math.PI) / 2 + .5) * d + c
            }
        },
        timers: [],
        fx: function (a, b, c) {
            this.options = b, this.elem = a, this.prop = c, b.orig || (b.orig = {})
        }
    }), q.fx.prototype = {
        update: function () {
            this.options.step && this.options.step.call(this.elem, this.now, this), (q.fx.step[this.prop] || q.fx.step._default)(this), (this.prop === "height" || this.prop === "width") && this.elem.style && (this.elem.style.display = "block")
        },
        cur: function (a) {
            return this.elem[this.prop] == null || !! this.elem.style && this.elem.style[this.prop] != null ? (a = parseFloat(q.css(this.elem, this.prop, a))) && a > -1e4 ? a : parseFloat(q.curCSS(this.elem, this.prop)) || 0 : this.elem[this.prop]
        },
        custom: function (a, b, c) {
            function d(a) {
                return e.step(a)
            }
            this.startTime = f(), this.start = a, this.end = b, this.unit = c || this.unit || "px", this.now = this.start, this.pos = this.state = 0;
            var e = this;
            d.elem = this.elem, d() && q.timers.push(d) && !bS && (bS = setInterval(q.fx.tick, 13))
        },
        show: function () {
            this.options.orig[this.prop] = q.style(this.elem, this.prop), this.options.show = !0, this.custom(this.prop === "width" || this.prop === "height" ? 1 : 0, this.cur()), q(this.elem).show()
        },
        hide: function () {
            this.options.orig[this.prop] = q.style(this.elem, this.prop), this.options.hide = !0, this.custom(this.cur(), 0)
        },
        step: function (a) {
            var b = f(),
                c = !0;
            if (a || b >= this.options.duration + this.startTime) {
                this.now = this.end, this.pos = this.state = 1, this.update(), this.options.curAnim[this.prop] = !0;
                for (var d in this.options.curAnim) this.options.curAnim[d] !== !0 && (c = !1);
                if (c) {
                    this.options.display != null && (this.elem.style.overflow = this.options.overflow, a = q.data(this.elem, "olddisplay"), this.elem.style.display = a ? a : this.options.display, q.css(this.elem, "display") === "none" && (this.elem.style.display = "block")), this.options.hide && q(this.elem).hide();
                    if (this.options.hide || this.options.show) for (var e in this.options.curAnim) q.style(this.elem, e, this.options.orig[e]);
                    this.options.complete.call(this.elem)
                }
                return !1
            }
            return e = b - this.startTime, this.state = e / this.options.duration, a = this.options.easing || (q.easing.swing ? "swing" : "linear"), this.pos = q.easing[this.options.specialEasing && this.options.specialEasing[this.prop] || a](this.state, e, 0, 1, this.options.duration), this.now = this.start + (this.end - this.start) * this.pos, this.update(), !0
        }
    }, q.extend(q.fx, {
        tick: function () {
            for (var a = q.timers, b = 0; b < a.length; b++) a[b]() || a.splice(b--, 1);
            a.length || q.fx.stop()
        },
        stop: function () {
            clearInterval(bS), bS = null
        },
        speeds: {
            slow: 600,
            fast: 200,
            _default: 400
        },
        step: {
            opacity: function (a) {
                q.style(a.elem, "opacity", a.now)
            },
            _default: function (a) {
                a.elem.style && a.elem.style[a.prop] != null ? a.elem.style[a.prop] = (a.prop === "width" || a.prop === "height" ? Math.max(0, a.now) : a.now) + a.unit : a.elem[a.prop] = a.now
            }
        }
    }), q.expr && q.expr.filters && (q.expr.filters.animated = function (a) {
        return q.grep(q.timers, function (b) {
            return a === b.elem
        }).length
    }), q.fn.offset = "getBoundingClientRect" in t.documentElement ? function (a) {
        var b = this[0];
        if (a) return this.each(function (b) {
            q.offset.setOffset(this, a, b)
        });
        if (!b || !b.ownerDocument) return null;
        if (b === b.ownerDocument.body) return q.offset.bodyOffset(b);
        var c = b.getBoundingClientRect(),
            d = b.ownerDocument;
        return b = d.body, d = d.documentElement, {
            top: c.top + (self.pageYOffset || q.support.boxModel && d.scrollTop || b.scrollTop) - (d.clientTop || b.clientTop || 0),
            left: c.left + (self.pageXOffset || q.support.boxModel && d.scrollLeft || b.scrollLeft) - (d.clientLeft || b.clientLeft || 0)
        }
    } : function (a) {
        var b = this[0];
        if (a) return this.each(function (b) {
            q.offset.setOffset(this, a, b)
        });
        if (!b || !b.ownerDocument) return null;
        if (b === b.ownerDocument.body) return q.offset.bodyOffset(b);
        q.offset.initialize();
        var c = b.offsetParent,
            d = b,
            e = b.ownerDocument,
            f, g = e.documentElement,
            h = e.body;
        d = (e = e.defaultView) ? e.getComputedStyle(b, null) : b.currentStyle;
        for (var i = b.offsetTop, j = b.offsetLeft;
        (b = b.parentNode) && b !== h && b !== g;) {
            if (q.offset.supportsFixedPosition && d.position === "fixed") break;
            f = e ? e.getComputedStyle(b, null) : b.currentStyle, i -= b.scrollTop, j -= b.scrollLeft, b === c && (i += b.offsetTop, j += b.offsetLeft, q.offset.doesNotAddBorder && (!q.offset.doesAddBorderForTableAndCells || !/^t(able|d|h)$/i.test(b.nodeName)) && (i += parseFloat(f.borderTopWidth) || 0, j += parseFloat(f.borderLeftWidth) || 0), d = c, c = b.offsetParent), q.offset.subtractsBorderForOverflowNotVisible && f.overflow !== "visible" && (i += parseFloat(f.borderTopWidth) || 0, j += parseFloat(f.borderLeftWidth) || 0), d = f
        }
        if (d.position === "relative" || d.position === "static") i += h.offsetTop, j += h.offsetLeft;
        return q.offset.supportsFixedPosition && d.position === "fixed" && (i += Math.max(g.scrollTop, h.scrollTop), j += Math.max(g.scrollLeft, h.scrollLeft)), {
            top: i,
            left: j
        }
    }, q.offset = {
        initialize: function () {
            var a = t.body,
                b = t.createElement("div"),
                c, d, e, f = parseFloat(q.curCSS(a, "marginTop", !0)) || 0;
            q.extend(b.style, {
                position: "absolute",
                top: 0,
                left: 0,
                margin: 0,
                border: 0,
                width: "1px",
                height: "1px",
                visibility: "hidden"
            }), b.innerHTML = "<div style='position:absolute;top:0;left:0;margin:0;border:5px solid #000;padding:0;width:1px;height:1px;'><div></div></div><table style='position:absolute;top:0;left:0;margin:0;border:5px solid #000;padding:0;width:1px;height:1px;' cellpadding='0' cellspacing='0'><tr><td></td></tr></table>", a.insertBefore(b, a.firstChild), c = b.firstChild, d = c.firstChild, e = c.nextSibling.firstChild.firstChild, this.doesNotAddBorder = d.offsetTop !== 5, this.doesAddBorderForTableAndCells = e.offsetTop === 5, d.style.position = "fixed", d.style.top = "20px", this.supportsFixedPosition = d.offsetTop === 20 || d.offsetTop === 15, d.style.position = d.style.top = "", c.style.overflow = "hidden", c.style.position = "relative", this.subtractsBorderForOverflowNotVisible = d.offsetTop === -5, this.doesNotIncludeMarginInBodyOffset = a.offsetTop !== f, a.removeChild(b), q.offset.initialize = q.noop
        },
        bodyOffset: function (a) {
            var b = a.offsetTop,
                c = a.offsetLeft;
            return q.offset.initialize(), q.offset.doesNotIncludeMarginInBodyOffset && (b += parseFloat(q.curCSS(a, "marginTop", !0)) || 0, c += parseFloat(q.curCSS(a, "marginLeft", !0)) || 0), {
                top: b,
                left: c
            }
        },
        setOffset: function (a, b, c) {
            /static/.test(q.curCSS(a, "position")) && (a.style.position = "relative");
            var d = q(a),
                e = d.offset(),
                f = parseInt(q.curCSS(a, "top", !0), 10) || 0,
                g = parseInt(q.curCSS(a, "left", !0), 10) || 0;
            q.isFunction(b) && (b = b.call(a, c, e)), c = {
                top: b.top - e.top + f,
                left: b.left - e.left + g
            }, "using" in b ? b.using.call(a, c) : d.css(c)
        }
    }, q.fn.extend({
        position: function () {
            if (!this[0]) return null;
            var a = this[0],
                b = this.offsetParent(),
                c = this.offset(),
                d = /^body|html$/i.test(b[0].nodeName) ? {
                    top: 0,
                    left: 0
                } : b.offset();
            return c.top -= parseFloat(q.curCSS(a, "marginTop", !0)) || 0, c.left -= parseFloat(q.curCSS(a, "marginLeft", !0)) || 0, d.top += parseFloat(q.curCSS(b[0], "borderTopWidth", !0)) || 0, d.left += parseFloat(q.curCSS(b[0], "borderLeftWidth", !0)) || 0, {
                top: c.top - d.top,
                left: c.left - d.left
            }
        },
        offsetParent: function () {
            return this.map(function () {
                for (var a = this.offsetParent || t.body; a && !/^body|html$/i.test(a.nodeName) && q.css(a, "position") === "static";) a = a.offsetParent;
                return a
            })
        }
    }), q.each(["Left", "Top"], function (a, c) {
        var d = "scroll" + c;
        q.fn[d] = function (c) {
            var e = this[0],
                f;
            return e ? c !== b ? this.each(function () {
                (f = p(this)) ? f.scrollTo(a ? q(f).scrollLeft() : c, a ? c : q(f).scrollTop()) : this[d] = c
            }) : (f = p(e)) ? "pageXOffset" in f ? f[a ? "pageYOffset" : "pageXOffset"] : q.support.boxModel && f.document.documentElement[d] || f.document.body[d] : e[d] : null
        }
    }), q.each(["Height", "Width"], function (a, c) {
        var d = c.toLowerCase();
        q.fn["inner" + c] = function () {
            return this[0] ? q.css(this[0], d, !1, "padding") : null
        }, q.fn["outer" + c] = function (a) {
            return this[0] ? q.css(this[0], d, !1, a ? "margin" : "border") : null
        }, q.fn[d] = function (a) {
            var e = this[0];
            return e ? q.isFunction(a) ? this.each(function (b) {
                var c = q(this);
                c[d](a.call(this, b, c[d]()))
            }) : "scrollTo" in e && e.document ? e.document.compatMode === "CSS1Compat" && e.document.documentElement["client" + c] || e.document.body["client" + c] : e.nodeType === 9 ? Math.max(e.documentElement["client" + c], e.body["scroll" + c], e.documentElement["scroll" + c], e.body["offset" + c], e.documentElement["offset" + c]) : a === b ? q.css(e, d) : this.css(d, typeof a == "string" ? a : a + "px") : a == null ? null : this
        }
    }), a.jQuery = a.$ = q
})(window), define("lib/jquery", function () {}),
function () {
    var a = this,
        b = a._,
        c = {}, d = Array.prototype,
        e = Object.prototype,
        f = d.slice,
        g = d.unshift,
        h = e.toString,
        i = e.hasOwnProperty,
        j = d.forEach,
        k = d.map,
        l = d.reduce,
        m = d.reduceRight,
        n = d.filter,
        o = d.every,
        p = d.some,
        q = d.indexOf,
        r = d.lastIndexOf,
        s = Array.isArray,
        t = Object.keys,
        u = function (a) {
            return new z(a)
        };
    typeof module != "undefined" && module.exports ? (module.exports = u, u._ = u) : a._ = u, u.VERSION = "1.1.4";
    var v = u.each = u.forEach = function (a, b, d) {
        var e;
        if (a == null) return;
        if (j && a.forEach === j) a.forEach(b, d);
        else if (u.isNumber(a.length)) {
            for (var f = 0, g = a.length; f < g; f++) if (b.call(d, a[f], f, a) === c) return
        } else for (var h in a) if (i.call(a, h) && b.call(d, a[h], h, a) === c) return
    };
    u.map = function (a, b, c) {
        var d = [];
        return a == null ? d : k && a.map === k ? a.map(b, c) : (v(a, function (a, e, f) {
            d[d.length] = b.call(c, a, e, f)
        }), d)
    }, u.reduce = u.foldl = u.inject = function (a, b, c, d) {
        var e = c !== void 0;
        a == null && (a = []);
        if (l && a.reduce === l) return d && (b = u.bind(b, d)), e ? a.reduce(b, c) : a.reduce(b);
        v(a, function (a, f, g) {
            !e && f === 0 ? (c = a, e = !0) : c = b.call(d, c, a, f, g)
        });
        if (!e) throw new TypeError("Reduce of empty array with no initial value");
        return c
    }, u.reduceRight = u.foldr = function (a, b, c, d) {
        a == null && (a = []);
        if (m && a.reduceRight === m) return d && (b = u.bind(b, d)), c !== void 0 ? a.reduceRight(b, c) : a.reduceRight(b);
        var e = (u.isArray(a) ? a.slice() : u.toArray(a)).reverse();
        return u.reduce(e, b, c, d)
    }, u.find = u.detect = function (a, b, c) {
        var d;
        return w(a, function (a, e, f) {
            if (b.call(c, a, e, f)) return d = a, !0
        }), d
    }, u.filter = u.select = function (a, b, c) {
        var d = [];
        return a == null ? d : n && a.filter === n ? a.filter(b, c) : (v(a, function (a, e, f) {
            b.call(c, a, e, f) && (d[d.length] = a)
        }), d)
    }, u.reject = function (a, b, c) {
        var d = [];
        return a == null ? d : (v(a, function (a, e, f) {
            b.call(c, a, e, f) || (d[d.length] = a)
        }), d)
    }, u.every = u.all = function (a, b, d) {
        b = b || u.identity;
        var e = !0;
        return a == null ? e : o && a.every === o ? a.every(b, d) : (v(a, function (a, f, g) {
            if (!(e = e && b.call(d, a, f, g))) return c
        }), e)
    };
    var w = u.some = u.any = function (a, b, d) {
        b = b || u.identity;
        var e = !1;
        return a == null ? e : p && a.some === p ? a.some(b, d) : (v(a, function (a, f, g) {
            if (e = b.call(d, a, f, g)) return c
        }), e)
    };
    u.include = u.contains = function (a, b) {
        var c = !1;
        return a == null ? c : q && a.indexOf === q ? a.indexOf(b) != -1 : (w(a, function (a) {
            if (c = a === b) return !0
        }), c)
    }, u.invoke = function (a, b) {
        var c = f.call(arguments, 2);
        return u.map(a, function (a) {
            return (b ? a[b] : a).apply(a, c)
        })
    }, u.pluck = function (a, b) {
        return u.map(a, function (a) {
            return a[b]
        })
    }, u.max = function (a, b, c) {
        if (!b && u.isArray(a)) return Math.max.apply(Math, a);
        var d = {
            computed: -Infinity
        };
        return v(a, function (a, e, f) {
            var g = b ? b.call(c, a, e, f) : a;
            g >= d.computed && (d = {
                value: a,
                computed: g
            })
        }), d.value
    }, u.min = function (a, b, c) {
        if (!b && u.isArray(a)) return Math.min.apply(Math, a);
        var d = {
            computed: Infinity
        };
        return v(a, function (a, e, f) {
            var g = b ? b.call(c, a, e, f) : a;
            g < d.computed && (d = {
                value: a,
                computed: g
            })
        }), d.value
    }, u.sortBy = function (a, b, c) {
        return u.pluck(u.map(a, function (a, d, e) {
            return {
                value: a,
                criteria: b.call(c, a, d, e)
            }
        }).sort(function (a, b) {
            var c = a.criteria,
                d = b.criteria;
            return c < d ? -1 : c > d ? 1 : 0
        }), "value")
    }, u.sortedIndex = function (a, b, c) {
        c = c || u.identity;
        var d = 0,
            e = a.length;
        while (d < e) {
            var f = d + e >> 1;
            c(a[f]) < c(b) ? d = f + 1 : e = f
        }
        return d
    }, u.toArray = function (a) {
        return a ? a.toArray ? a.toArray() : u.isArray(a) ? a : u.isArguments(a) ? f.call(a) : u.values(a) : []
    }, u.size = function (a) {
        return u.toArray(a).length
    }, u.first = u.head = function (a, b, c) {
        return b && !c ? f.call(a, 0, b) : a[0]
    }, u.rest = u.tail = function (a, b, c) {
        return f.call(a, u.isUndefined(b) || c ? 1 : b)
    }, u.last = function (a) {
        return a[a.length - 1]
    }, u.compact = function (a) {
        return u.filter(a, function (a) {
            return !!a
        })
    }, u.flatten = function (a) {
        return u.reduce(a, function (a, b) {
            return u.isArray(b) ? a.concat(u.flatten(b)) : (a[a.length] = b, a)
        }, [])
    }, u.without = function (a) {
        var b = f.call(arguments, 1);
        return u.filter(a, function (a) {
            return !u.include(b, a)
        })
    }, u.uniq = u.unique = function (a) {
        return u.reduce(a, function (a, b, c) {
            if (0 == c || !u.include(a, b)) a[a.length] = b;
            return a
        }, [])
    }, u.intersect = function (a) {
        var b = f.call(arguments, 1);
        return u.filter(u.uniq(a), function (a) {
            return u.every(b, function (b) {
                return u.indexOf(b, a) >= 0
            })
        })
    }, u.zip = function () {
        var a = f.call(arguments),
            b = u.max(u.pluck(a, "length")),
            c = new Array(b);
        for (var d = 0; d < b; d++) c[d] = u.pluck(a, "" + d);
        return c
    }, u.indexOf = function (a, b, c) {
        if (a == null) return -1;
        if (c) {
            var d = u.sortedIndex(a, b);
            return a[d] === b ? d : -1
        }
        if (q && a.indexOf === q) return a.indexOf(b);
        for (var d = 0, e = a.length; d < e; d++) if (a[d] === b) return d;
        return -1
    }, u.lastIndexOf = function (a, b) {
        if (a == null) return -1;
        if (r && a.lastIndexOf === r) return a.lastIndexOf(b);
        var c = a.length;
        while (c--) if (a[c] === b) return c;
        return -1
    }, u.range = function (a, b, c) {
        var d = f.call(arguments),
            e = d.length <= 1,
            a = e ? 0 : d[0],
            b = e ? d[0] : d[1],
            c = d[2] || 1,
            g = Math.max(Math.ceil((b - a) / c), 0),
            h = 0,
            i = new Array(g);
        while (h < g) i[h++] = a, a += c;
        return i
    }, u.bind = function (a, b) {
        var c = f.call(arguments, 2);
        return function () {
            return a.apply(b || {}, c.concat(f.call(arguments)))
        }
    }, u.bindAll = function (a) {
        var b = f.call(arguments, 1);
        return b.length == 0 && (b = u.functions(a)), v(b, function (b) {
            a[b] = u.bind(a[b], a)
        }), a
    }, u.memoize = function (a, b) {
        var c = {};
        return b = b || u.identity,
        function () {
            var d = b.apply(this, arguments);
            return d in c ? c[d] : c[d] = a.apply(this, arguments)
        }
    }, u.delay = function (a, b) {
        var c = f.call(arguments, 2);
        return setTimeout(function () {
            return a.apply(a, c)
        }, b)
    }, u.defer = function (a) {
        return u.delay.apply(u, [a, 1].concat(f.call(arguments, 1)))
    };
    var x = function (a, b, c) {
        var d;
        return function () {
            var e = this,
                f = arguments,
                g = function () {
                    d = null, a.apply(e, f)
                };
            c && clearTimeout(d);
            if (c || !d) d = setTimeout(g, b)
        }
    };
    u.throttle = function (a, b) {
        return x(a, b, !1)
    }, u.debounce = function (a, b) {
        return x(a, b, !0)
    }, u.wrap = function (a, b) {
        return function () {
            var c = [a].concat(f.call(arguments));
            return b.apply(this, c)
        }
    }, u.compose = function () {
        var a = f.call(arguments);
        return function () {
            var b = f.call(arguments);
            for (var c = a.length - 1; c >= 0; c--) b = [a[c].apply(this, b)];
            return b[0]
        }
    }, u.keys = t || function (a) {
        if (u.isArray(a)) return u.range(0, a.length);
        var b = [];
        for (var c in a) i.call(a, c) && (b[b.length] = c);
        return b
    }, u.values = function (a) {
        return u.map(a, u.identity)
    }, u.functions = u.methods = function (a) {
        return u.filter(u.keys(a), function (b) {
            return u.isFunction(a[b])
        }).sort()
    }, u.extend = function (a) {
        return v(f.call(arguments, 1), function (b) {
            for (var c in b) a[c] = b[c]
        }), a
    }, u.clone = function (a) {
        return u.isArray(a) ? a.slice() : u.extend({}, a)
    }, u.tap = function (a, b) {
        return b(a), a
    }, u.isEqual = function (a, b) {
        if (a === b) return !0;
        var c = typeof a,
            d = typeof b;
        if (c != d) return !1;
        if (a == b) return !0;
        if (!a && b || a && !b) return !1;
        a._chain && (a = a._wrapped), b._chain && (b = b._wrapped);
        if (a.isEqual) return a.isEqual(b);
        if (u.isDate(a) && u.isDate(b)) return a.getTime() === b.getTime();
        if (u.isNaN(a) && u.isNaN(b)) return !1;
        if (u.isRegExp(a) && u.isRegExp(b)) return a.source === b.source && a.global === b.global && a.ignoreCase === b.ignoreCase && a.multiline === b.multiline;
        if (c !== "object") return !1;
        if (a.length && a.length !== b.length) return !1;
        var e = u.keys(a),
            f = u.keys(b);
        if (e.length != f.length) return !1;
        for (var g in a) if (!(g in b) || !u.isEqual(a[g], b[g])) return !1;
        return !0
    }, u.isEmpty = function (a) {
        if (u.isArray(a) || u.isString(a)) return a.length === 0;
        for (var b in a) if (i.call(a, b)) return !1;
        return !0
    }, u.isElement = function (a) {
        return !!a && a.nodeType == 1
    }, u.isArray = s || function (a) {
        return h.call(a) === "[object Array]"
    }, u.isArguments = function (a) {
        return !!a && !! i.call(a, "callee")
    }, u.isFunction = function (a) {
        return !!(a && a.constructor && a.call && a.apply)
    }, u.isString = function (a) {
        return !!(a === "" || a && a.charCodeAt && a.substr)
    }, u.isNumber = function (a) {
        return !!(a === 0 || a && a.toExponential && a.toFixed)
    }, u.isNaN = function (a) {
        return a !== a
    }, u.isBoolean = function (a) {
        return a === !0 || a === !1
    }, u.isDate = function (a) {
        return !!(a && a.getTimezoneOffset && a.setUTCFullYear)
    }, u.isRegExp = function (a) {
        return !(!(a && a.test && a.exec) || !a.ignoreCase && a.ignoreCase !== !1)
    }, u.isNull = function (a) {
        return a === null
    }, u.isUndefined = function (a) {
        return a === void 0
    }, u.noConflict = function () {
        return a._ = b, this
    }, u.identity = function (a) {
        return a
    }, u.times = function (a, b, c) {
        for (var d = 0; d < a; d++) b.call(c, d)
    }, u.mixin = function (a) {
        v(u.functions(a), function (b) {
            B(b, u[b] = a[b])
        })
    };
    var y = 0;
    u.uniqueId = function (a) {
        var b = y++;
        return a ? a + b : b
    }, u.templateSettings = {
        evaluate: /<%([\s\S]+?)%>/g,
        interpolate: /<%=([\s\S]+?)%>/g
    }, u.template = function (a, b) {
        var c = u.templateSettings,
            d = "var __p=[],print=function(){__p.push.apply(__p,arguments);};with(obj||{}){__p.push('" + a.replace(/\\/g, "\\\\").replace(/'/g, "\\'").replace(c.interpolate, function (a, b) {
                return "'," + b.replace(/\\'/g, "'") + ",'"
            }).replace(c.evaluate || null, function (a, b) {
                return "');" + b.replace(/\\'/g, "'").replace(/[\r\n\t]/g, " ") + "__p.push('"
            }).replace(/\r/g, "\\r").replace(/\n/g, "\\n").replace(/\t/g, "\\t") + "');}return __p.join('');",
            e = new Function("obj", d);
        return b ? e(b) : e
    };
    var z = function (a) {
        this._wrapped = a
    };
    u.prototype = z.prototype;
    var A = function (a, b) {
        return b ? u(a).chain() : a
    }, B = function (a, b) {
        z.prototype[a] = function () {
            var a = f.call(arguments);
            return g.call(a, this._wrapped), A(b.apply(u, a), this._chain)
        }
    };
    u.mixin(u), v(["pop", "push", "reverse", "shift", "sort", "splice", "unshift"], function (a) {
        var b = d[a];
        z.prototype[a] = function () {
            return b.apply(this._wrapped, arguments), A(this._wrapped, this._chain)
        }
    }), v(["concat", "join", "slice"], function (a) {
        var b = d[a];
        z.prototype[a] = function () {
            return A(b.apply(this._wrapped, arguments), this._chain)
        }
    }), z.prototype.chain = function () {
        return this._chain = !0, this
    }, z.prototype.value = function () {
        return this._wrapped
    }
}();
var neo4j = neo4j || {};
neo4j.services = neo4j.services || {}, neo4j.exceptions = neo4j.exceptions || {}, neo4j.exceptions.HttpException = function (a, b, c, d) {
    var d = d || "A server error or a network error occurred. Status code: " + a + ".";
    this.status = a, this.data = b || {}, this.req = c || {}, Error.call(this, d)
}, neo4j.exceptions.HttpException.prototype = new Error, neo4j.exceptions.HttpException.RESPONSE_CODES = {
    Conflict: 409,
    NotFound: 404
},
function () {
    var a = neo4j.exceptions.HttpException.prototype,
        b = neo4j.exceptions.HttpException.RESPONSE_CODES;
    _.each(_.keys(b), function (c) {
        a["is" + c] = function () {
            return this.status === b[c]
        }
    })
}(), neo4j.exceptions.ConnectionLostException = function () {
    neo4j.exceptions.HttpException.call(this, -1, null, null, "The server connection was lost.")
}, neo4j.exceptions.HttpException.prototype = new neo4j.exceptions.HttpException, neo4j.exceptions.NotFoundException = function (a) {
    Error.call(this, "The object at url " + a + " does not exist."), this.url = a
}, neo4j.exceptions.NotFoundException.prototype = new Error, neo4j.exceptions.InvalidDataException = function () {
    Error.call(this, "Unable to create relationship or node from the provided data. This may be because you tried to get a node or relationship from an invalid url.")
}, neo4j.exceptions.InvalidDataException.prototype = new Error, neo4j.exceptions.StartNodeSameAsEndNodeException = function (a) {
    Error.call(this, "You cannot create a relationship with the same start and end node."), this.url = a
}, neo4j.exceptions.StartNodeSameAsEndNodeException.prototype = new Error, _.extend(neo4j, {
    setTimeout: function (a, b) {
        if (typeof setTimeout != "undefined") return setTimeout(a, b);
        b === 0 ? a() : neo4j.log("No timeout implementation found, unable to do timed tasks.")
    },
    clearTimeout: function (a) {
        typeof clearTimeout != "undefined" ? clearTimeout(intervalId) : neo4j.log("No timeout implementation found, unable to do timed tasks.")
    },
    _intervals: {}
}), _.extend(neo4j, {
    setInterval: function (a, b) {
        if (typeof setInterval != "undefined") return setInterval(a, b);
        if (typeof setTimeout != "undefined") {
            var c = (new Date).getTime();

            function d() {
                a(), neo4j._intervals[c] = setTimeout(d, b)
            }
            return neo4j._intervals[c] = setTimeout(d, b), c
        }
        neo4j.log("No timeout or interval implementation found, unable to do timed tasks.")
    },
    clearInterval: function (a) {
        typeof clearInterval != "undefined" ? clearInterval(a) : typeof clearTimeout != "undefined" ? clearTimeout(neo4j._intervals[a]) : neo4j.log("No timeout or interval implementation found, unable to do timed tasks.")
    },
    _intervals: {}
}), neo4j.Promise = function (a) {
    _.bindAll(this, "then", "fulfill", "fail", "addHandlers", "addFulfilledHandler", "addFailedHandler", "_callHandlers", "_callHandler", "_addHandler"), this._handlers = [], typeof a == "function" && a(this.fulfill, this.fail)
}, neo4j.Promise.wrap = function (a) {
    return a instanceof neo4j.Promise ? a : neo4j.Promise.fulfilled(a)
}, neo4j.Promise.fulfilled = function (a) {
    return new neo4j.Promise(function (b) {
        b(a)
    })
}, neo4j.Promise.join = function () {
    var a = _.toArray(arguments);
    return a.length == 1 ? a[0] : new neo4j.Promise(function (b, c) {
        function e(a) {
            a.length > 0 ? a.shift().addFulfilledHandler(function (b) {
                d.push(b), e(a)
            }) : b(d)
        }
        var d = [];
        for (var f = 0, g = a.length; f < g; f++) a[f].addFailedHandler(c);
        e(a)
    })
}, _.extend(neo4j.Promise.prototype, {
    then: function (a, b) {
        var c = this;
        return new neo4j.Promise(function (d, e) {
            c.addHandlers(function (b) {
                a ? a(b, d, e) : d(b)
            }, function (a) {
                typeof b == "function" ? b(a, d, e) : e(a)
            })
        })
    },
    chain: function (a) {
        var b = this;
        this.chainedPromise = a, a.then(null, function (a) {
            b.fail(a)
        })
    },
    fulfill: function (a) {
        if (this.chainedPromise) {
            var b = this;
            this.chainedPromise.then(function () {
                b._fulfill(a)
            })
        } else this._fulfill(a)
    },
    fail: function (a) {
        this._complete || (this._failedResult = a, this._fulfilled = !1, this._complete = !0, this._callHandlers())
    },
    _fulfill: function (a) {
        this._complete || (this._fulfilledResult = a, this._fulfilled = !0, this._complete = !0, this._callHandlers())
    },
    _callHandlers: function () {
        _.each(this._handlers, this._callHandler)
    },
    _callHandler: function (a) {
        this._fulfilled && typeof a.fulfilled == "function" ? a.fulfilled(this._fulfilledResult) : typeof a.failed == "function" && a.failed(this._failedResult)
    },
    addHandlers: function (a, b) {
        a = a || function () {}, b = b || function () {}, this._addHandler({
            fulfilled: a,
            failed: b
        })
    },
    addFulfilledHandler: function (a) {
        this.addHandlers(a)
    },
    addFailedHandler: function (a) {
        this.addHandlers(null, a)
    },
    _addHandler: function (a) {
        this._complete ? this._callHandler(a) : this._handlers.push(a)
    }
}), neo4j.cachedFunction = function (a, b, c) {
    var d = null,
        e = null,
        f = !1,
        c = c || !1,
        g = [];
    return function () {
        var i = arguments[b];
        f ? i.apply(e, d) : (g.push(i), g.length === 1 && (arguments[b] = function () {
            e = this, d = arguments, f = !0;
            for (var a in g) g[a].apply(e, d);
            g = [], c && setTimeout(function () {
                f = !1
            }, c)
        }, a.apply(this, arguments)))
    }
}, neo4j.log = function () {
    typeof console != "undefined" && typeof console.log == "function" && console.log.apply(this, arguments)
}, neo4j.proxy = function (a, b) {
    return _.bind(a, b)
}, neo4j.Events = function (a) {
    this.uniqueNamespaceCount = 0, this.handlers = {}, this.context = a || {}
}, neo4j.Events.prototype.createUniqueNamespace = function () {
    return "uniq#" + this.uniqueNamespaceCount++
}, neo4j.Events.prototype.bind = function (a, b) {
    typeof this.handlers[a] == "undefined" && (this.handlers[a] = []), this.handlers[a].push(b)
}, neo4j.Events.prototype.trigger = function (a, b) {
    if (typeof this.handlers[a] != "undefined") {
        var b = b || {}, c = this.handlers[a],
            d = _.extend({
                key: a,
                data: b
            }, this.context);
        for (var e = 0, f = c.length; e < f; e++) neo4j.setTimeout(function (b) {
            return function () {
                try {
                    b(d)
                } catch (c) {
                    neo4j.log("Event handler for event " + a + " threw exception.", c)
                }
            }
        }(c[e]), 0)
    }
}, neo4j.events = new neo4j.Events, neo4j.jqueryWebProvider = {
    ajax: function (a) {
        function i(a, b, c) {
            c.status === 0 ? j(c) : f.apply(this, arguments)
        }
        function j(a) {
            try {
                if (a.status === 200) return f(null)
            } catch (b) {}
            try {
                if (a.status === 0) g(new neo4j.exceptions.ConnectionLostException);
                else {
                    var c = JSON.parse(a.responseText);
                    g(new neo4j.exceptions.HttpException(a.status, c, a))
                }
            } catch (b) {
                g(new neo4j.exceptions.HttpException(-1, {}, a))
            }
        }
        var b = a.timeout || 216e5,
            c = a.method,
            d = a.url,
            e = a.data,
            f = a.success,
            g = a.failure,
            h = c === "GET",
            k = this.isCrossDomain;
        (function (a, c, d, e, f) {
            d === null || d === "null" ? d = "" : h || (d = JSON.stringify(d)), k(c) && window.XDomainRequest ? typeof f == "function" && f(new neo4j.exceptions.HttpException(-1, null, null, "Cross-domain requests are available in IE, but are not yet implemented in neo4js.")) : $.ajax({
                url: c,
                type: a,
                data: d,
                timeout: b,
                cache: !1,
                processData: h,
                success: i,
                contentType: "application/json",
                error: j,
                dataType: "json"
            })
        })(c, d, e, f, g)
    },
    isCrossDomain: function (a) {
        if (a) {
            var b = a.indexOf("://");
            return b === -1 || b > 7 ? !1 : a.substring(b + 3).split("/", 1)[0] !== window.location.host
        }
        return !1
    }
}, neo4j.Web = function (a, b) {
    this.webProvider = a || neo4j.jqueryWebProvider, this.events = b || neo4j.events
}, _.extend(neo4j.Web.prototype, {
    get: function (a, b, c, d) {
        return this.ajax("GET", a, b, c, d)
    },
    post: function (a, b, c, d) {
        return this.ajax("POST", a, b, c, d)
    },
    put: function (a, b, c, d) {
        return this.ajax("PUT", a, b, c, d)
    },
    del: function (a, b, c, d) {
        return this.ajax("DELETE", a, b, c, d)
    },
    ajax: function () {
        var a = this._processAjaxArguments(arguments),
            b = this;
        return a.userFail = this.wrapFailureCallback(a.failure), a.userSuccess = a.success, new neo4j.Promise(function (c, d) {
            a.failure = function () {
                d.call(this, {
                    error: arguments[0],
                    args: arguments
                }), a.userFail.apply(this, arguments)
            }, a.success = function () {
                c.call(this, {
                    data: arguments[0],
                    args: arguments
                }), a.userSuccess.apply(this, arguments)
            };
            try {
                b.webProvider.ajax(a)
            } catch (e) {
                a.failure(e)
            }
        })
    },
    looksLikeUrl: function (a) {
        var b = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
        return b.test(a)
    },
    setWebProvider: function (a) {
        this.webProvider = a
    },
    replace: function (a, b) {
        var c = {
            url: a
        };
        return _.each(_.keys(b), function (a) {
            c.url = c.url.replace("{" + a + "}", b[a])
        }), c.url
    },
    wrapFailureCallback: function (a) {
        var b = this.events;
        return function (c) {
            typeof c != "undefined" && c instanceof neo4j.exceptions.ConnectionLostException && (b.trigger("web.connection_lost", _.toArray(arguments)), b.trigger("web.connection.failed", _.toArray(arguments))), a.apply(this, arguments)
        }
    },
    _processAjaxArguments: function (a) {
        var b, c, d, e, f, a = _.toArray(a);
        return b = a.shift(), c = a.shift(), d = a.length > 0 && !_.isFunction(a[0]) ? a.shift() : null, e = a.length > 0 ? a.shift() : null, f = a.length > 0 ? a.shift() : null, e = _.isFunction(e) ? e : function () {}, f = _.isFunction(f) ? f : function () {}, {
            method: b,
            url: c,
            data: d,
            success: e,
            failure: f
        }
    }
}), neo4j.Service = function (a) {
    this.callsWaiting = [], this.loadServiceDefinition = neo4j.cachedFunction(this.loadServiceDefinition, 0), this.events = new neo4j.Events, this.bind = neo4j.proxy(this.events.bind, this.events), this.trigger = neo4j.proxy(this.events.trigger, this.events), this.db = a, this.db.bind("services.loaded", neo4j.proxy(function () {
        this.initialized || this.setNotAvailable()
    }, this))
}, neo4j.Service.resourceFactory = function (a) {
    var b = a.urlArgs || [],
        c = b.length,
        d = a.after ? a.after : function (a, b) {
            b(a)
        }, e = a.before ? a.before : function (a, b) {
            a.apply(this, b)
        }, f = a.errorHandler ? a.errorHandler : function (a, b) {
            a({
                message: "An error occurred, please see attached error object.",
                error: b
            })
        }, g = function () {
            var e = neo4j.proxy(d, this),
                g = neo4j.proxy(f, this);
            if (c > 0) {
                var h = {};
                for (var i = 0; i < c; i++) h[b[i]] = arguments[i];
                var j = this.db.web.replace(this.resources[a.resource], h)
            } else var j = this.resources[a.resource];
            var k = null,
                l = function () {};
            arguments.length > c && (typeof arguments[arguments.length - 1] == "function" && (l = arguments[arguments.length - 1]), arguments.length - 1 > c && (k = arguments[arguments.length - 2])), k !== null ? this.db.web.ajax(a.method, j, k, function (a) {
                e(a, l)
            }, function (a) {
                g(l, a)
            }) : this.db.web.ajax(a.method, j, function (a) {
                e(a, l)
            }, function (a) {
                g(l, a)
            })
        };
    return function () {
        this.serviceMethodPreflight(function () {
            e.call(this, neo4j.proxy(g, this), arguments)
        }, arguments)
    }
}, _.extend(neo4j.Service.prototype, {
    initialized: !1,
    available: null,
    resources: null,
    handleWaitingCalls: function () {
        for (var a = 0, b = this.callsWaiting.length; a < b; a++) try {
            this.serviceMethodPreflight(this.callsWaiting[a].method, this.callsWaiting[a].args)
        } catch (c) {
            neo4j.log(c)
        }
    },
    loadServiceDefinition: function (a) {
        this.get("/", neo4j.proxy(function (b) {
            this.resources = b.resources, this.trigger("service.definition.loaded", b), a(b)
        }, this))
    },
    makeAvailable: function (a) {
        this.initialized = !0, this.available = !0, this.url = a, this.handleWaitingCalls()
    },
    setNotAvailable: function () {
        this.initialized = !0, this.available = !1, this.handleWaitingCalls()
    },
    get: function (a, b, c, d) {
        this.db.web.get(this.url + a, b, c, d)
    },
    del: function (a, b, c, d) {
        this.db.web.del(this.url + a, b, c, d)
    },
    post: function (a, b, c, d) {
        this.db.web.post(this.url + a, b, c, d)
    },
    put: function (a, b, c, d) {
        this.db.web.put(this.url + a, b, c, d)
    },
    serviceMethodPreflight: function (a, b) {
        if (this.available === !1) throw new Error("The service you are accessing is not available for this server.");
        if (!this.initialized) {
            this.callsWaiting.push({
                method: a,
                args: b
            });
            return
        }
        b = b || [], this.resources !== null ? a.apply(this, b) : this.loadServiceDefinition(neo4j.proxy(function () {
            a.apply(this, b)
        }, this))
    }
}), neo4j.GraphDatabaseHeartbeat = function (a) {
    this.db = a, this.monitor = a.manage.monitor, this.listeners = {}, this.idCounter = 0, this.listenerCounter = 0, this.timespan = {
        year: 31536e3,
        month: 2678400,
        week: 604800,
        day: 86400,
        hours: 21600,
        minutes: 2100
    }, this.startTimestamp = Math.round((new Date).getTime() / 1e3) - this.timespan.year, this.endTimestamp = this.startTimestamp + 1, this.timestamps = [], this.data = {}, this.isPolling = !1, this.processMonitorData = neo4j.proxy(this.processMonitorData, this), this.beat = neo4j.proxy(this.beat, this), this.waitForPulse = neo4j.proxy(this.waitForPulse, this), neo4j.setInterval(this.beat, 2e3)
}, neo4j.GraphDatabaseHeartbeat.prototype.addListener = function (a) {
    return this.listenerCounter++, this.listeners[this.idCounter++] = a, this.idCounter
}, neo4j.GraphDatabaseHeartbeat.prototype.removeListener = function (a) {
    var b = !1;
    if (typeof a == "function") {
        for (var c in this.listeners) if (this.listeners[c] === a) {
            delete this.listeners[c], b;
            break
        }
    } else this.listeners[a] && (delete this.listeners[a], b = !0);
    b && this.listenerCounter--
}, neo4j.GraphDatabaseHeartbeat.prototype.getCachedData = function () {
    return {
        timestamps: this.timestamps,
        data: this.data,
        endTimestamp: this.endTimestamp,
        startTimestamp: this.startTimestamp
    }
}, neo4j.GraphDatabaseHeartbeat.prototype.beat = function () {
    this.listenerCounter > 0 && !this.isPolling && this.monitor.available && (this.isPolling = !0, this.monitor.getDataFrom(this.endTimestamp, this.processMonitorData))
}, neo4j.GraphDatabaseHeartbeat.prototype.processMonitorData = function (a) {
    this.isPolling = !1;
    if (a && !a.error) {
        var b = this.findDataBoundaries(a);
        if (b.dataEnd < 0) this.adjustRequestedTimespan();
        else {
            this.endTimestamp = a.timestamps[b.dataEnd];
            var c = a.timestamps.splice(b.dataStart, b.dataEnd - b.dataStart);
            this.timestamps = this.timestamps.concat(c);
            var d = {};
            for (var e in a.data) d[e] = a.data[e].splice(b.dataStart, b.dataEnd - b.dataStart), typeof this.data[e] == "undefined" && (this.data[e] = []), this.data[e] = this.data[e].concat(d[e]);
            var f = {
                server: this.server,
                newData: {
                    data: d,
                    timestamps: c,
                    end_time: this.endTimestamp,
                    start_time: a.start_time
                },
                allData: this.getCachedData()
            };
            this.callListeners(f)
        }
    }
}, neo4j.GraphDatabaseHeartbeat.prototype.waitForPulse = function (a) {
    if (!this.pulsePromise) {
        var b = this,
            c = this.db.get;
        this.pulsePromise = new neo4j.Promise(function (a) {
            var d = {
                interval: null
            };
            d.interval = neo4j.setInterval(function () {
                c("", function (c) {
                    c !== null && (neo4j.clearInterval(d.interval), b.pulsePromise = null, a(!0))
                })
            }, 4e3)
        })
    }
    return this.pulsePromise.addFulfilledHandler(a), this.pulsePromise
}, neo4j.GraphDatabaseHeartbeat.prototype.adjustRequestedTimespan = function (a) {
    var b = Math.round((new Date).getTime() / 1e3),
        c = b - this.endTimestamp;
    c < this.timespan.year ? c < this.timespan.month ? c < this.timespan.week ? c < this.timespan.day ? c >= this.timespan.day && (this.endTimestamp = b - this.timespan.minutes, this.beat()) : (this.endTimestamp = b - this.timespan.hours, this.beat()) : (this.endTimestamp = b - this.timespan.day, this.beat()) : (this.endTimestamp = b - this.timespan.week, this.beat()) : (this.endTimestamp = b - this.timespan.month, this.beat())
}, neo4j.GraphDatabaseHeartbeat.prototype.findDataBoundaries = function (a) {
    var b = this.getFirstKey(a),
        c = -1,
        d = -1;
    if (b) {
        for (c = a.timestamps.length - 1; c >= 0; c--) if (typeof a.data[b][c] == "number") break;
        for (d = 0; d <= c; d++) if (typeof a.data[b][d] == "number") break
    }
    return {
        dataStart: d,
        dataEnd: c
    }
}, neo4j.GraphDatabaseHeartbeat.prototype.callListeners = function (a) {
    for (var b in this.listeners) setTimeout(function (b) {
        return function () {
            b(a)
        }
    }(this.listeners[b]), 0)
}, neo4j.GraphDatabaseHeartbeat.prototype.getFirstKey = function (a) {
    if (typeof a == "object") for (var b in a.data) break;
    return b ? b : null
}, neo4j.models = neo4j.models || {}, neo4j.models.JMXBean = function (a) {
    this.parse(a)
}, neo4j.models.JMXBean.prototype.parse = function (a) {
    var b = this.parseName(a.name);
    this.domain = b.domain, delete b.domain, this.properties = b, this.attributes = a.attributes, this.description = a.description, this.jmxName = a.name
}, neo4j.models.JMXBean.prototype.getName = function (a) {
    if (this.properties.name) return this.properties.name;
    for (var b in this.properties) return this.properties[b];
    return this.jmxName
}, neo4j.models.JMXBean.prototype.parseName = function (a) {
    var b = a.split(":"),
        c, d, e = {};
    d = b[0], b = b[1].split(",");
    for (var f = 0, g = b.length; f < g; f++) c = b[f].split("="), e[c[0]] = c[1];
    return e.domain = d, e
}, neo4j.models.JMXBean.prototype.getAttribute = function (a) {
    var b = a.toLowerCase();
    for (var c = 0, d = this.attributes.length; c < d; c++) if (this.attributes[c].name.toLowerCase() === b) return this.attributes[c];
    return null
}, neo4j.models.PropertyContainer = function () {
    _.bindAll(this, "getSelf", "exists", "getProperty", "setProperty", "getProperties", "setProperties"), this._data = this._data || {}
}, _.extend(neo4j.models.PropertyContainer.prototype, {
    getSelf: function () {
        return typeof this._self != "undefined" ? this._self : null
    },
    getId: function () {
        var a = this.getSelf();
        return a == null ? null : a.substr(a.lastIndexOf("/") + 1)
    },
    exists: function () {
        return this.getSelf() !== null
    },
    hasProperty: function (a) {
        return a in this._data
    },
    getProperty: function (a) {
        return this._data[a] || null
    },
    setProperty: function (a, b) {
        this._data[a] = b
    },
    getProperties: function () {
        return this._data
    },
    setProperties: function (a) {
        this._data = _.extend(this._data, a)
    },
    removeProperty: function (a) {
        delete this._data[a]
    }
}), neo4j.models.Node = function (a, b) {
    neo4j.models.PropertyContainer.call(this), this.db = b, this._init(a), _.bindAll(this, "save", "fetch", "getRelationships", "_init")
}, neo4j.models.Node.IN = "in", neo4j.models.Node.OUT = "out", neo4j.models.Node.ALL = "all", neo4j.traverse = {}, neo4j.traverse.RETURN_NODES = "node", neo4j.traverse.RETURN_RELATIONSHIPS = "relationship", neo4j.traverse.RETURN_PATHS = "path", _.extend(neo4j.models.Node.prototype, neo4j.models.PropertyContainer.prototype, {
    save: function () {
        var a = this,
            b = this.db.web;
        return this.exists() ? new neo4j.Promise(function (c, d) {
            b.put(a._urls.properties, a.getProperties(), function () {
                c(a)
            }, d)
        }) : new neo4j.Promise(function (c, d) {
            a.db.getServiceDefinition().then(function (e) {
                b.post(e.node, a._data).then(function (b) {
                    a._init(b.data), c(a)
                }, d)
            }, d)
        })
    },
    fetch: function () {
        var a = this,
            b = this.db.web;
        return new neo4j.Promise(function (c, d) {
            b.get(a._self).then(function (b) {
                b.data && b.data.self ? (a._init(b.data), c(a)) : d(new neo4j.exceptions.InvalidDataException)
            }, function (b) {
                d(new neo4j.exceptions.NotFoundException(a._self))
            })
        })
    },
    remove: function () {
        var a = this,
            b = this.db.web,
            c = !1,
            d = this.db,
            e = a.getSelf();
        return new neo4j.Promise(function (f, g) {
            b.del(a.getSelf()).then(function () {
                d.getReferenceNodeUrl().then(function (a) {
                    a == e && d.forceRediscovery(), f(!0)
                }, g)
            }, function (b) {
                b.error.isConflict() && !c && a.getRelationships().then(function (b) {
                    _.each(b, function (a) {
                        a.remove()
                    }), c = !0, a.remove().then(function () {
                        f(!0)
                    }, g)
                }, g)
            })
        })
    },
    getCreateRelationshipUrl: function () {
        if (this.exists()) return this._urls.create_relationship;
        throw new Error("You can't get the create relationship url until you have saved the node!")
    },
    traverse: function (a, b) {
        b = b || neo4j.traverse.RETURN_NODES;
        var c = this.db.web.replace(this._urls.traverse, {
            returnType: b
        }),
            d = this,
            e;
        switch (b) {
            case neo4j.traverse.RETURN_RELATIONSHIPS:
                e = neo4j.models.Relationship;
                break;
            case neo4j.traverse.RETURN_PATHS:
                e = neo4j.models.Path;
                break;
            default:
                e = neo4j.models.Node
        }
        return new neo4j.Promise(function (b, f) {
            d.db.web.post(c, a).then(function (a) {
                var c = _.map(a.data, function (a) {
                    return new e(a, d.db)
                });
                b(c)
            }, f)
        })
    },
    getRelationships: function (a, b) {
        var a = a || neo4j.models.Node.ALL,
            b = b || null,
            c = this,
            d, e = b ? !0 : !1;
        _.isArray(b) && (b = b.join("&"));
        switch (a) {
            case neo4j.models.Node.IN:
                d = e ? this._urls.incoming_typed_relationships : this._urls.incoming_relationships;
                break;
            case neo4j.models.Node.OUT:
                d = e ? this._urls.outgoing_typed_relationships : this._urls.outgoing_relationships;
                break;
            default:
                d = e ? this._urls.all_typed_relationships : this._urls.all_relationships
        }
        return e && (d = this.db.web.replace(d, {
            "-list|&|types": b
        })), new neo4j.Promise(function (a, b) {
            c.db.web.get(d).then(function (b) {
                var d = _.map(b.data, function (a) {
                    return new neo4j.models.Relationship(a, c.db)
                });
                a(d)
            }, b)
        })
    },
    _init: function (a) {
        this._self = a.self || null, this._data = a.data || {}, this._urls = {
            properties: a.properties || "",
            traverse: a.traverse || "",
            create_relationship: a.create_relationship || "",
            all_relationships: a.all_relationships || "",
            all_typed_relationships: a.all_typed_relationships || "",
            incoming_relationships: a.incoming_relationships || "",
            incoming_typed_relationships: a.incoming_typed_relationships || "",
            outgoing_relationships: a.outgoing_relationships || "",
            outgoing_typed_relationships: a.outgoing_typed_relationships || ""
        }
    }
}), neo4j.models.Relationship = function (a, b) {
    neo4j.models.PropertyContainer.call(this), this.db = b, this._init(a), _.bindAll(this, "save", "fetch", "_init")
}, _.extend(neo4j.models.Relationship.prototype, neo4j.models.PropertyContainer.prototype, {
    save: function () {
        var a = this,
            b = this.db.web;
        return this.exists() ? new neo4j.Promise(function (c, d) {
            b.put(a._urls.properties, a.getProperties()).then(function () {
                c(a)
            }, d)
        }) : this.getStartNode().then(function (c, d, e) {
            var f = b.post(c.getCreateRelationshipUrl(), {
                to: a._endUrl,
                type: a.getType(),
                data: a.getProperties()
            });
            f.then(function (b) {
                a._init(b.data), d(a)
            }, function (b) {
                if (b.error && b.error.data && b.error.data.exception) {
                    var c = b.error.data.exception;
                    if (c.indexOf("EndNodeNotFoundException") > -1 || c.indexOf("BadInputException") > -1 && c.indexOf(a._endUrl) > -1) return e(new neo4j.exceptions.NotFoundException(a._endUrl));
                    if (c.indexOf("StartNodeSameAsEndNodeException") > -1) return e(new neo4j.exceptions.StartNodeSameAsEndNodeException(a._endUrl))
                }
                e(b)
            })
        })
    },
    fetch: function () {
        var a = this,
            b = this.db.web;
        return new neo4j.Promise(function (c, d) {
            b.get(a._self).then(function (b) {
                b.data && b.data.self && b.data.start && b.data.end ? (a._init(b.data), c(a)) : d(new neo4j.exceptions.InvalidDataException)
            }, d)
        })
    },
    remove: function () {
        var a = this,
            b = this.db.web;
        return new neo4j.Promise(function (c, d) {
            b.del(a.getSelf()).then(function () {
                c(!0)
            }, d)
        })
    },
    getType: function () {
        return this._type || null
    },
    getStartNode: function () {
        return this._getNode("_startNode", "_startUrl")
    },
    getStartNodeUrl: function () {
        return this._startUrl
    },
    isStartNode: function (a) {
        return a instanceof neo4j.models.Node ? this._startUrl === a.getSelf() : this._startUrl === a
    },
    getEndNode: function () {
        return this._getNode("_endNode", "_endUrl")
    },
    getEndNodeUrl: function () {
        return this._endUrl
    },
    isEndNode: function (a) {
        return a instanceof neo4j.models.Node ? this._endUrl === a.getSelf() : this._endUrl === a
    },
    getOtherNode: function (a) {
        return this.isStartNode(a) ? this.getEndNode() : this.getStartNode()
    },
    getOtherNodeUrl: function (a) {
        return this.isStartNode(a) ? this.getEndNodeUrl() : this.getStartNodeUrl()
    },
    _getNode: function (a, b) {
        if (typeof this[a] != "undefined") return neo4j.Promise.fulfilled(this[a]);
        var c = this;
        return this.db.node(this[b]).then(function (b, d) {
            c[a] = b, d(b)
        })
    },
    _init: function (a) {
        this._self = a.self || null, this._data = a.data || {}, this._type = a.type || null, this._urls = {
            properties: a.properties || ""
        }, typeof a.start != "undefined" && (a.start instanceof neo4j.models.Node ? (this._startNode = a.start, this._startUrl = a.start.getSelf()) : this._startUrl = a.start), typeof a.end != "undefined" && (a.end instanceof neo4j.models.Node ? (this._endNode = a.end, this._endUrl = a.end.getSelf()) : this._endUrl = a.end)
    }
}), neo4j.models.Path = function (a, b) {
    this.db = b, this._init(a), _.bindAll(this, "_init")
}, _.extend(neo4j.models.Path.prototype, {
    _init: function (a) {
        this._start = a.start, this._end = a.end, this._length = a.length, this._nodeUrls = a.nodes, this._relationshipUrls = a.relationships
    }
}), neo4j.cypher = neo4j.cypher || {}, neo4j.cypher.ExecutionEngine = function (a) {
    this.db = a
}, _.extend(neo4j.cypher.ExecutionEngine.prototype, {
    execute: function (a) {
        var b = this;
        return this.db.getServiceDefinition().then(function (c, d, e) {
            b.db.web.post(c.cypher, {
                query: a
            }, function (a) {
                d(new neo4j.cypher.QueryResult(b.db, a))
            }, e)
        })
    }
}), neo4j.cypher.ResultRow = function (a, b, c) {
    this.db = a, this.row = b, this.columnMap = c, this.pointer = 0
}, _.extend(neo4j.cypher.ResultRow.prototype, {
    size: function () {
        return this.row.length
    },
    getByIndex: function (a) {
        return this._convertValue(this.row[a])
    },
    get: function (a) {
        return this.getByIndex(this.columnMap[a])
    },
    next: function () {
        return this.getByIndex(this.pointer++)
    },
    hasNext: function () {
        return this.pointer < this.size()
    },
    reset: function () {
        this.pointer = 0
    },
    _convertValue: function (a) {
        return a === null ? null : typeof a.data != "undefined" ? typeof a.type != "undefined" ? new neo4j.models.Relationship(a, this.db) : typeof a.length != "undefined" ? JSON.stringify(a) : new neo4j.models.Node(a, this.db) : a
    }
}), neo4j.cypher.QueryResult = function (a, b) {
    this.db = a, this.data = b.data, this.columns = b.columns, this.pointer = 0, this.columnMap = {};
    for (var c = 0; c < this.columns.length; c++) this.columnMap[this.columns[c]] = c
}, _.extend(neo4j.cypher.QueryResult.prototype, {
    size: function () {
        return this.data.length
    },
    next: function () {
        return new neo4j.cypher.ResultRow(this.db, this.data[this.pointer++], this.columnMap)
    },
    hasNext: function () {
        return this.pointer < this.size()
    },
    reset: function () {
        this.pointer = 0
    }
}), neo4j.services.BackupService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.BackupService.prototype, neo4j.Service.prototype), neo4j.services.BackupService.prototype.triggerManual = neo4j.Service.resourceFactory({
    resource: "trigger_manual",
    method: "POST",
    errorHandler: function (a, b) {
        b.exception == "NoBackupFoundationException" && a(!1)
    }
}), neo4j.services.BackupService.prototype.triggerManualFoundation = neo4j.Service.resourceFactory({
    resource: "trigger_manual_foundation",
    method: "POST"
}), neo4j.services.BackupService.prototype.getJobs = neo4j.Service.resourceFactory({
    resource: "jobs",
    method: "GET"
}), neo4j.services.BackupService.prototype.getJob = function (a, b) {
    this.getJobs(function (c) {
        for (var d in c.jobList) if (c.jobList[d].id == a) {
            b(c.jobList[d]);
            return
        }
        b(null)
    })
}, neo4j.services.BackupService.prototype.deleteJob = neo4j.Service.resourceFactory({
    resource: "job",
    method: "DELETE",
    urlArgs: ["id"]
}), neo4j.services.BackupService.prototype.triggerJobFoundation = neo4j.Service.resourceFactory({
    resource: "trigger_job_foundation",
    method: "POST",
    urlArgs: ["id"]
}), neo4j.services.BackupService.prototype.setJob = neo4j.Service.resourceFactory({
    resource: "jobs",
    method: "PUT"
}), neo4j.services.ConfigService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.ConfigService.prototype, neo4j.Service.prototype), neo4j.services.ConfigService.prototype.getProperties = neo4j.Service.resourceFactory({
    resource: "properties",
    method: "GET",
    before: function (a, b) {
        var c = b[0];
        a(function (a) {
            var b = {};
            for (var d in a) b[a[d].key] = a[d];
            c(b)
        })
    }
}), neo4j.services.ConfigService.prototype.getProperty = function (a, b) {
    this.getProperties(function (c) {
        for (var d in c) if (d === a) {
            b(c[d]);
            return
        }
        b(null)
    })
}, neo4j.services.ConfigService.prototype.setProperties = neo4j.Service.resourceFactory({
    resource: "properties",
    method: "POST",
    before: function (a, b) {
        var c = [],
            d;
        for (var e in b[0]) d = {
            key: e,
            value: b[0][e]
        }, c.push(d), this.db.trigger("config.property.set", d);
        a(c, b[1])
    }
}), neo4j.services.ConfigService.prototype.setProperty = function (a, b, c) {
    var d = {};
    d[a] = b, this.setProperties(d, c)
}, neo4j.services.ImportService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.ImportService.prototype, neo4j.Service.prototype), neo4j.services.ImportService.prototype.fromUrl = neo4j.Service.resourceFactory({
    resource: "import_from_url",
    method: "POST",
    before: function (a, b) {
        a({
            url: b[0]
        }, b[1])
    }
}), neo4j.services.ImportService.prototype.getUploadUrl = function (a) {
    this.serviceMethodPreflight(function (a) {
        a(this.resources.import_from_file)
    }, arguments)
}, neo4j.services.ExportService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.ExportService.prototype, neo4j.Service.prototype), neo4j.services.ExportService.prototype.all = neo4j.Service.resourceFactory({
    resource: "export_all",
    method: "POST"
}), neo4j.services.ConsoleService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.ConsoleService.prototype, neo4j.Service.prototype), neo4j.services.ConsoleService.prototype.exec = neo4j.Service.resourceFactory({
    resource: "exec",
    method: "POST",
    before: function (a, b) {
        a({
            command: b[0],
            engine: b[1]
        }, b[2])
    }
}), neo4j.services.JmxService = function (a) {
    neo4j.Service.call(this, a), this.kernelInstance = neo4j.cachedFunction(this.kernelInstance, 0, 2e3)
}, _.extend(neo4j.services.JmxService.prototype, neo4j.Service.prototype), neo4j.services.JmxService.prototype.getDomains = neo4j.Service.resourceFactory({
    resource: "domains",
    method: "GET"
}), neo4j.services.JmxService.prototype.getDomain = neo4j.Service.resourceFactory({
    resource: "domain",
    method: "GET",
    urlArgs: ["domain"],
    after: function (a, b) {
        var c = [];
        for (var d = 0, e = a.beans; d < e; d++) c.push(new neo4j.models.JMXBean(a.beans[d]));
        a.beans = c, b(a)
    }
}), neo4j.services.JmxService.prototype.getBean = neo4j.Service.resourceFactory({
    resource: "bean",
    method: "GET",
    urlArgs: ["domain", "objectName"],
    before: function (a, b) {
        if (b[0] === "neo4j") {
            var c = this,
                d = b[1],
                e = b[2];
            this.kernelInstance(function (b) {
                var c = ["org.neo4j", escape(b + ",name=" + d), e];
                a.apply(this, c)
            })
        } else b[0] = escape(b[0]), b[1] = escape(b[1]), a.apply(this, b)
    },
    after: function (a, b) {
        a.length > 0 ? b(new neo4j.models.JMXBean(a[0])) : b(null)
    }
}), neo4j.services.JmxService.prototype.query = neo4j.Service.resourceFactory({
    resource: "query",
    method: "POST",
    after: function (a, b) {
        var c = [];
        for (var d = 0, e = a.length; d < e; d++) c.push(new neo4j.models.JMXBean(a[d]));
        b(c)
    }
}), neo4j.services.JmxService.prototype.kernelInstance = function (a) {
    var b = this.db.web;
    this.serviceMethodPreflight(function (a) {
        var c = this.resources.kernelquery;
        b.get(c, function (b) {
            var c = b ? b.split(":")[1].split(",")[0] : null;
            a(c)
        })
    }, [a])
}, neo4j.services.LifecycleService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.LifecycleService.prototype, neo4j.Service.prototype), neo4j.services.LifecycleService.prototype.getStatus = neo4j.Service.resourceFactory({
    resource: "status",
    method: "GET"
}), neo4j.services.LifecycleService.prototype.start = neo4j.Service.resourceFactory({
    resource: "start",
    method: "POST"
}), neo4j.services.LifecycleService.prototype.stop = neo4j.Service.resourceFactory({
    resource: "stop",
    method: "POST"
}), neo4j.services.LifecycleService.prototype.restart = neo4j.Service.resourceFactory({
    resource: "restart",
    method: "POST"
}), neo4j.services.MonitorService = function (a) {
    neo4j.Service.call(this, a)
}, _.extend(neo4j.services.MonitorService.prototype, neo4j.Service.prototype), neo4j.services.MonitorService.prototype.getData = neo4j.Service.resourceFactory({
    resource: "latest_data",
    method: "GET"
}), neo4j.services.MonitorService.prototype.getDataFrom = neo4j.Service.resourceFactory({
    resource: "data_from",
    method: "GET",
    urlArgs: ["start"]
}), neo4j.services.MonitorService.prototype.getDataBetween = neo4j.Service.resourceFactory({
    resource: "data_period",
    method: "GET",
    urlArgs: ["start", "stop"]
}), neo4j.index = neo4j.index || {}, neo4j.index.Index = function (a, b) {
    this.db = a, this.name = b, this.config = null, this.provider = "N/A", _.bindAll(this, "query", "exactQuery", "index", "unindex")
}, _.extend(neo4j.index.Index.prototype, {
    getUriFor: function (a) {
        return ""
    },
    getObjectFor: function (a) {
        return ""
    },
    getType: function () {
        return ""
    },
    createObjectFromDefinition: function (a) {},
    getIdFor: function (a) {
        return a.then(function (a, b) {
            b(a.getId())
        })
    },
    setConfig: function (a) {
        this.config = a
    },
    configAvailable: function () {
        return this.config !== null
    },
    getConfig: function () {
        return this.config
    },
    query: function (a) {
        var b = this;
        return this.db.getServiceDefinition().then(function (c, d, e) {
            b.db.web.get(c[b.getType()] + "/" + b.name, {
                query: a
            }, function (a) {
                var c = [];
                for (var e = 0, f = a.length; e < f; e++) c.push(b.createObjectFromDefinition(a[e]));
                d(c)
            }, e)
        })
    },
    exactQuery: function (a, b) {
        var c = this;
        return this.db.getServiceDefinition().then(function (d, e, f) {
            c.db.web.get(d[c.getType()] + "/" + c.name + "/" + a + "/" + b, function (a) {
                var b = [];
                for (var d = 0, f = a.length; d < f; d++) b.push(c.createObjectFromDefinition(a[d]));
                e(b)
            }, f)
        })
    },
    index: function (a, b, c) {
        var d = neo4j.Promise.wrap(a),
            e = neo4j.Promise.wrap(b),
            f = this.getUriFor(d),
            g = this.db.getServiceDefinition(),
            h = this;
        if (typeof c == "undefined") var i = this.getObjectFor(d).then(function (a, c) {
            c(a.getProperty(b))
        });
        else var i = neo4j.Promise.wrap(c);
        var j = neo4j.Promise.join.apply(this, [f, e, i, g]);
        return j.then(function (a, b, c) {
            var d = a[0],
                e = a[1],
                f = a[2],
                g = a[3];
            h.db.web.post(g[h.getType()] + "/" + h.name + "/" + e + "/" + f, d, function (a) {
                b(!0)
            }, c)
        })
    },
    unindex: function (a, b, c) {
        var d = neo4j.Promise.wrap(a),
            e = this.getIdFor(d),
            f = this.db.getServiceDefinition(),
            g = this,
            h = neo4j.Promise.join.apply(this, [e, f]);
        return h.then(function (a, d, e) {
            var f = a[0],
                h = a[1],
                i = h[g.getType()] + "/" + g.name;
            b && (i += "/" + b), c && (i += "/" + c), i += "/" + f, g.db.web.del(i, function (a) {
                d(!0)
            }, e)
        })
    }
}), neo4j.index.NodeIndex = function (a, b) {
    neo4j.index.Index.call(this, a, b)
}, _.extend(neo4j.index.NodeIndex.prototype, neo4j.index.Index.prototype, {
    getType: function () {
        return "node_index"
    },
    getUriFor: function (a) {
        var b = this.db;
        return a.then(function (a, c) {
            b.nodeUri(a).then(c)
        })
    },
    getObjectFor: function (a) {
        var b = this.db;
        return a.then(function (a, c) {
            typeof a.getSelf != "undefined" ? c(a) : b.node(a).then(function (a) {
                c(a)
            })
        })
    },
    createObjectFromDefinition: function (a) {
        return new neo4j.models.Node(a, this.db)
    }
}), neo4j.index.RelationshipIndex = function (a, b) {
    neo4j.index.Index.call(this, a, b)
}, _.extend(neo4j.index.RelationshipIndex.prototype, neo4j.index.Index.prototype, {
    getType: function () {
        return "relationship_index"
    },
    getUriFor: function (a) {
        var b = this.db;
        return a.then(function (a, c) {
            b.relUri(a).then(c)
        })
    },
    getObjectFor: function (a) {
        var b = this.db;
        return a.then(function (a, c) {
            typeof a.getSelf != "undefined" ? c(a) : b.rel(a).then(function (a) {
                c(a)
            })
        })
    },
    createObjectFromDefinition: function (a) {
        return new neo4j.models.Relationship(a, this.db)
    }
}), neo4j.index.Indexes = function (a) {
    this.db = a, this._cache = {}, _.bindAll(this, "getNodeIndex", "getRelationshipIndex", "createNodeIndex", "createRelationshipIndex", "removeNodeIndex", "removeRelationshipIndex")
}, _.extend(neo4j.index.Indexes.prototype, {
    getAllNodeIndexes: function () {
        return this._listAllIndexes("node_index")
    },
    getAllRelationshipIndexes: function () {
        return this._listAllIndexes("relationship_index")
    },
    getNodeIndex: function (a) {
        return this._getOrCreateLocalIndexObject("node_index", a)
    },
    getRelationshipIndex: function (a) {
        return this._getOrCreateLocalIndexObject("relationship_index", a)
    },
    createNodeIndex: function (a, b) {
        return this._createIndex("node_index", a, b)
    },
    createRelationshipIndex: function (a, b) {
        return this._createIndex("relationship_index", a, b)
    },
    removeNodeIndex: function (a) {
        return this._removeIndex("node_index", a)
    },
    removeRelationshipIndex: function (a) {
        return this._removeIndex("relationship_index", a)
    },
    _listAllIndexes: function (a) {
        var b = this.db,
            c = this;
        return this.db.getServiceDefinition().then(function (d, e, f) {
            b.web.get(d[a], function (b) {
                var d = [],
                    f = b === null ? [] : _(b).keys();
                for (var g = 0, h = f.length; g < h; g++) d.push(c._getOrCreateLocalIndexObject(a, f[g], b[f[g]]));
                e(d)
            }, f)
        })
    },
    _createIndex: function (a, b, c) {
        var c = c || {
            provider: "lucene",
            type: "exact"
        }, d = this.db,
            e = this;
        return this.db.getServiceDefinition().then(function (f, g, h) {
            d.web.post(f[a], {
                name: b,
                config: c
            }, function (d) {
                g(e._getOrCreateLocalIndexObject(a, b, c))
            }, h)
        })
    },
    _removeIndex: function (a, b) {
        var c = this.db;
        return this.db.getServiceDefinition().then(function (d, e, f) {
            c.web.del(d[a] + "/" + b, e, f)
        })
    },
    _getOrCreateLocalIndexObject: function (a, b, c) {
        var c = c || null;
        typeof this._cache[a] == "undefined" && (this._cache[a] = {});
        if (typeof this._cache[a][b] == "undefined") {
            if (a === "relationship_index") var d = new neo4j.index.RelationshipIndex(this.db, b);
            else var d = new neo4j.index.NodeIndex(this.db, b);
            this._cache[a][b] = d
        }
        return c != null && (c.provider && (this._cache[a][b].provider = c.provider, delete c.provider), c.template && delete c.template, this._cache[a][b].setConfig(c)), this._cache[a][b]
    }
}), neo4j.GraphDatabaseManager = function (a) {
    _.bindAll(this, "discoverServices"), this.db = a, this.backup = new neo4j.services.BackupService(a), this.config = new neo4j.services.ConfigService(a), this.importing = new neo4j.services.ImportService(a), this.exporting = new neo4j.services.ExportService(a), this.console = new neo4j.services.ConsoleService(a), this.jmx = new neo4j.services.JmxService(a), this.lifecycle = new neo4j.services.LifecycleService(a), this.monitor = new neo4j.services.MonitorService(a), this.db.getServiceDefinition().then(this.discoverServices)
}, _.extend(neo4j.GraphDatabaseManager.prototype, {
    servicesLoaded: function () {
        return this.services ? !0 : !1
    },
    availableServices: function () {
        if (this.services) {
            if (!this.serviceNames) {
                this.serviceNames = [];
                for (var a in this.services) this.serviceNames.push(a)
            }
            return this.serviceNames
        }
        throw new Error("Service definition has not been loaded yet.")
    },
    discoverServices: function () {
        var a = this;
        this.db.getDiscoveryDocument().then(function (b) {
            a.db.web.get(b.management, neo4j.proxy(function (a) {
                this.services = a.services;
                for (var b in a.services) this[b] && this[b].makeAvailable(a.services[b]);
                this.db.trigger("services.loaded")
            }, a), neo4j.proxy(function (a) {
                neo4j.log("Unable to fetch service descriptions for server " + this.url + ". Server management will be unavailable.")
            }, this))
        })
    }
}), neo4j.GraphDatabase = function (a, b) {
    this.url = a, this.events = new neo4j.Events({
        db: this
    }), this.bind = neo4j.proxy(this.events.bind, this.events), this.web = b || new neo4j.Web(null, this.events), this.trigger = neo4j.proxy(this.events.trigger, this.events), this.index = new neo4j.index.Indexes(this), this.cypher = new neo4j.cypher.ExecutionEngine(this), this.manage = new neo4j.GraphDatabaseManager(this), this.heartbeat = new neo4j.GraphDatabaseHeartbeat(this), this.rel = this.relationship, this.referenceNode = this.getReferenceNode, _.bindAll(this, "getServiceDefinition", "getReferenceNode", "node", "relationship", "getReferenceNodeUrl", "getAvailableRelationshipTypes", "get", "put", "post", "del", "forceRediscovery")
}, _.extend(neo4j.GraphDatabase.prototype, {
    node: function (a) {
        var b = this,
            c = neo4j.Promise.wrap(a);
        return c.then(function (a, c, d) {
            if (typeof a == "object") {
                var e = new neo4j.models.Node({
                    data: a
                }, b);
                e.save().then(function (a) {
                    c(a)
                }, d)
            } else {
                var f = b.promiseNodeOrNodeUrl(a);
                f.then(function (a) {
                    var e = new neo4j.models.Node({
                        self: a
                    }, b);
                    e.fetch().then(function (a) {
                        c(a)
                    }, function () {
                        d(new neo4j.exceptions.NotFoundException(a))
                    })
                })
            }
        })
    },
    relationship: function (a, b, c, d) {
        var e = this;
        if (typeof b == "undefined") {
            var f = this.promiseRelationshipOrRelationshipUrl(a);
            return f.then(function (a, b, c) {
                var d = new neo4j.models.Relationship({
                    self: a
                }, e);
                d.fetch().then(function (a) {
                    b(a)
                }, function () {
                    c(new neo4j.exceptions.NotFoundException(a))
                })
            })
        }
        var g = neo4j.Promise.wrap(d || {}),
            h = neo4j.Promise.wrap(b),
            i = this.promiseNodeOrNodeUrl(a),
            j = this.promiseNodeOrNodeUrl(c),
            k = neo4j.Promise.join(i, j, h, g);
        return k.then(function (a, b, c) {
            var d = new neo4j.models.Relationship({
                start: a[0],
                end: a[1],
                type: a[2],
                data: a[3]
            }, e);
            d.save().then(function (a) {
                b(a)
            }, c)
        })
    },
    query: function (a) {
        return this.cypher.execute(a)
    },
    getNodeOrRelationship: function (a) {
        var b = this;
        return this.isNodeUrl(a).then(function (c, d, e) {
            c ? b.node(a).then(function (a) {
                d(a)
            }, e) : b.rel(a).then(function (a) {
                d(a)
            }, e)
        })
    },
    getReferenceNode: function () {
        return this.node(this.getReferenceNodeUrl())
    },
    getAvailableRelationshipTypes: function () {
        var a = this;
        return this.getServiceDefinition().then(function (b, c, d) {
            a.web.get(b.relationship_types, function (a) {
                c(a)
            }, d)
        })
    },
    getReferenceNodeUrl: function () {
        return this.getServiceDefinition().then(function (a, b, c) {
            typeof a.reference_node != "undefined" ? b(a.reference_node) : c()
        })
    },
    nodeUri: function (a) {
        return typeof a.getSelf != "undefined" ? neo4j.Promise.wrap(a.getSelf()) : this.getServiceDefinition().then(function (b, c) {
            /^[0-9]+$/i.test(a) ? c(b.node + "/" + a) : c(a)
        })
    },
    relUri: function (a) {
        return typeof a.getSelf != "undefined" ? neo4j.Promise.wrap(a.getSelf()) : this.getDiscoveryDocument().then(function (b, c) {
            /^[0-9]+$/i.test(a) ? c(b.data + "relationship/" + a) : c(a)
        })
    },
    getServiceDefinition: function () {
        if (typeof this._serviceDefinitionPromise == "undefined") {
            var a = this;
            this._serviceDefinitionPromise = this.getDiscoveryDocument().then(function (b, c, d) {
                a.web.get(b.data, function (a) {
                    c(a)
                })
            })
        }
        return this._serviceDefinitionPromise
    },
    getDiscoveryDocument: function () {
        if (typeof this._discoveryDocumentPromise == "undefined") {
            var a = this;
            this._discoveryDocumentPromise = new neo4j.Promise(function (b, c) {
                a.web.get(a.url, function (a) {
                    b(a)
                })
            })
        }
        return this._discoveryDocumentPromise
    },
    get: function (a, b, c, d) {
        this.web.get(this.url + a, b, c, d)
    },
    del: function (a, b, c, d) {
        this.web.del(this.url + a, b, c, d)
    },
    post: function (a, b, c, d) {
        this.web.post(this.url + a, b, c, d)
    },
    put: function (a, b, c, d) {
        this.web.put(this.url + a, b, c, d)
    },
    stripUrlBase: function (a) {
        return typeof a == "undefined" || a.indexOf("://") == -1 ? a : a.indexOf(this.url) === 0 ? a.substring(this.url.length) : a.indexOf(this.manageUrl) === 0 ? a.substring(this.manageUrl.length) : a.substring(a.indexOf("/", 8))
    },
    isNodeUrl: function (a) {
        return this.getServiceDefinition().then(function (b, c) {
            c(a.indexOf(b.node) === 0)
        })
    },
    promiseNodeOrNodeUrl: function (a) {
        return typeof a == "object" || this.isUrl(a) ? neo4j.Promise.wrap(a) : this.nodeUri(a)
    },
    promiseRelationshipOrRelationshipUrl: function (a) {
        return typeof a == "object" || this.isUrl(a) ? neo4j.Promise.wrap(a) : this.relUri(a)
    },
    isUrl: function (a) {
        return typeof a == "object" ? !1 : (a += "", a.indexOf("://") !== -1)
    },
    toJSONString: function () {
        return {
            url: this.url
        }
    },
    forceRediscovery: function () {
        delete this._discoveryDocumentPromise, delete this._serviceDefinitionPromise
    }
}), define("lib/neo4js", function () {}),
function () {
    var a = this,
        b = a._,
        c = {}, d = Array.prototype,
        e = Object.prototype,
        f = Function.prototype,
        g = d.slice,
        h = d.unshift,
        i = e.toString,
        j = e.hasOwnProperty,
        k = d.forEach,
        l = d.map,
        m = d.reduce,
        n = d.reduceRight,
        o = d.filter,
        p = d.every,
        q = d.some,
        r = d.indexOf,
        s = d.lastIndexOf,
        t = Array.isArray,
        u = Object.keys,
        v = f.bind,
        w = function (a) {
            return new B(a)
        };
    typeof module != "undefined" && module.exports ? (module.exports = w, w._ = w) : a._ = w, w.VERSION = "1.1.7";
    var x = w.each = w.forEach = function (a, b, d) {
        if (a == null) return;
        if (k && a.forEach === k) a.forEach(b, d);
        else if (a.length === +a.length) {
            for (var e = 0, f = a.length; e < f; e++) if (e in a && b.call(d, a[e], e, a) === c) return
        } else for (var g in a) if (j.call(a, g) && b.call(d, a[g], g, a) === c) return
    };
    w.map = function (a, b, c) {
        var d = [];
        return a == null ? d : l && a.map === l ? a.map(b, c) : (x(a, function (a, e, f) {
            d[d.length] = b.call(c, a, e, f)
        }), d)
    }, w.reduce = w.foldl = w.inject = function (a, b, c, d) {
        var e = c !== void 0;
        a == null && (a = []);
        if (m && a.reduce === m) return d && (b = w.bind(b, d)), e ? a.reduce(b, c) : a.reduce(b);
        x(a, function (a, f, g) {
            e ? c = b.call(d, c, a, f, g) : (c = a, e = !0)
        });
        if (!e) throw new TypeError("Reduce of empty array with no initial value");
        return c
    }, w.reduceRight = w.foldr = function (a, b, c, d) {
        a == null && (a = []);
        if (n && a.reduceRight === n) return d && (b = w.bind(b, d)), c !== void 0 ? a.reduceRight(b, c) : a.reduceRight(b);
        var e = (w.isArray(a) ? a.slice() : w.toArray(a)).reverse();
        return w.reduce(e, b, c, d)
    }, w.find = w.detect = function (a, b, c) {
        var d;
        return y(a, function (a, e, f) {
            if (b.call(c, a, e, f)) return d = a, !0
        }), d
    }, w.filter = w.select = function (a, b, c) {
        var d = [];
        return a == null ? d : o && a.filter === o ? a.filter(b, c) : (x(a, function (a, e, f) {
            b.call(c, a, e, f) && (d[d.length] = a)
        }), d)
    }, w.reject = function (a, b, c) {
        var d = [];
        return a == null ? d : (x(a, function (a, e, f) {
            b.call(c, a, e, f) || (d[d.length] = a)
        }), d)
    }, w.every = w.all = function (a, b, d) {
        var e = !0;
        return a == null ? e : p && a.every === p ? a.every(b, d) : (x(a, function (a, f, g) {
            if (!(e = e && b.call(d, a, f, g))) return c
        }), e)
    };
    var y = w.some = w.any = function (a, b, d) {
        b = b || w.identity;
        var e = !1;
        return a == null ? e : q && a.some === q ? a.some(b, d) : (x(a, function (a, f, g) {
            if (e |= b.call(d, a, f, g)) return c
        }), !! e)
    };
    w.include = w.contains = function (a, b) {
        var c = !1;
        return a == null ? c : r && a.indexOf === r ? a.indexOf(b) != -1 : (y(a, function (a) {
            if (c = a === b) return !0
        }), c)
    }, w.invoke = function (a, b) {
        var c = g.call(arguments, 2);
        return w.map(a, function (a) {
            return (b.call ? b || a : a[b]).apply(a, c)
        })
    }, w.pluck = function (a, b) {
        return w.map(a, function (a) {
            return a[b]
        })
    }, w.max = function (a, b, c) {
        if (!b && w.isArray(a)) return Math.max.apply(Math, a);
        var d = {
            computed: -Infinity
        };
        return x(a, function (a, e, f) {
            var g = b ? b.call(c, a, e, f) : a;
            g >= d.computed && (d = {
                value: a,
                computed: g
            })
        }), d.value
    }, w.min = function (a, b, c) {
        if (!b && w.isArray(a)) return Math.min.apply(Math, a);
        var d = {
            computed: Infinity
        };
        return x(a, function (a, e, f) {
            var g = b ? b.call(c, a, e, f) : a;
            g < d.computed && (d = {
                value: a,
                computed: g
            })
        }), d.value
    }, w.sortBy = function (a, b, c) {
        return w.pluck(w.map(a, function (a, d, e) {
            return {
                value: a,
                criteria: b.call(c, a, d, e)
            }
        }).sort(function (a, b) {
            var c = a.criteria,
                d = b.criteria;
            return c < d ? -1 : c > d ? 1 : 0
        }), "value")
    }, w.groupBy = function (a, b) {
        var c = {};
        return x(a, function (a, d) {
            var e = b(a, d);
            (c[e] || (c[e] = [])).push(a)
        }), c
    }, w.sortedIndex = function (a, b, c) {
        c || (c = w.identity);
        var d = 0,
            e = a.length;
        while (d < e) {
            var f = d + e >> 1;
            c(a[f]) < c(b) ? d = f + 1 : e = f
        }
        return d
    }, w.toArray = function (a) {
        return a ? a.toArray ? a.toArray() : w.isArray(a) ? g.call(a) : w.isArguments(a) ? g.call(a) : w.values(a) : []
    }, w.size = function (a) {
        return w.toArray(a).length
    }, w.first = w.head = function (a, b, c) {
        return b != null && !c ? g.call(a, 0, b) : a[0]
    }, w.rest = w.tail = function (a, b, c) {
        return g.call(a, b == null || c ? 1 : b)
    }, w.last = function (a) {
        return a[a.length - 1]
    }, w.compact = function (a) {
        return w.filter(a, function (a) {
            return !!a
        })
    }, w.flatten = function (a) {
        return w.reduce(a, function (a, b) {
            return w.isArray(b) ? a.concat(w.flatten(b)) : (a[a.length] = b, a)
        }, [])
    }, w.without = function (a) {
        return w.difference(a, g.call(arguments, 1))
    }, w.uniq = w.unique = function (a, b, c) {
        var d = c ? w.map(a, c) : a,
            e = [];
        return w.reduce(d, function (c, d, f) {
            if (0 == f || (b === !0 ? w.last(c) != d : !w.include(c, d))) c[c.length] = d, e[e.length] = a[f];
            return c
        }, []), e
    }, w.union = function () {
        return w.uniq(w.flatten(arguments))
    }, w.intersection = w.intersect = function (a) {
        var b = g.call(arguments, 1);
        return w.filter(w.uniq(a), function (a) {
            return w.every(b, function (b) {
                return w.indexOf(b, a) >= 0
            })
        })
    }, w.difference = function (a, b) {
        return w.filter(a, function (a) {
            return !w.include(b, a)
        })
    }, w.zip = function () {
        var a = g.call(arguments),
            b = w.max(w.pluck(a, "length")),
            c = new Array(b);
        for (var d = 0; d < b; d++) c[d] = w.pluck(a, "" + d);
        return c
    }, w.indexOf = function (a, b, c) {
        if (a == null) return -1;
        var d, e;
        if (c) return d = w.sortedIndex(a, b), a[d] === b ? d : -1;
        if (r && a.indexOf === r) return a.indexOf(b);
        for (d = 0, e = a.length; d < e; d++) if (a[d] === b) return d;
        return -1
    }, w.lastIndexOf = function (a, b) {
        if (a == null) return -1;
        if (s && a.lastIndexOf === s) return a.lastIndexOf(b);
        var c = a.length;
        while (c--) if (a[c] === b) return c;
        return -1
    }, w.range = function (a, b, c) {
        arguments.length <= 1 && (b = a || 0, a = 0), c = arguments[2] || 1;
        var d = Math.max(Math.ceil((b - a) / c), 0),
            e = 0,
            f = new Array(d);
        while (e < d) f[e++] = a, a += c;
        return f
    }, w.bind = function (a, b) {
        if (a.bind === v && v) return v.apply(a, g.call(arguments, 1));
        var c = g.call(arguments, 2);
        return function () {
            return a.apply(b, c.concat(g.call(arguments)))
        }
    }, w.bindAll = function (a) {
        var b = g.call(arguments, 1);
        return b.length == 0 && (b = w.functions(a)), x(b, function (b) {
            a[b] = w.bind(a[b], a)
        }), a
    }, w.memoize = function (a, b) {
        var c = {};
        return b || (b = w.identity),
        function () {
            var d = b.apply(this, arguments);
            return j.call(c, d) ? c[d] : c[d] = a.apply(this, arguments)
        }
    }, w.delay = function (a, b) {
        var c = g.call(arguments, 2);
        return setTimeout(function () {
            return a.apply(a, c)
        }, b)
    }, w.defer = function (a) {
        return w.delay.apply(w, [a, 1].concat(g.call(arguments, 1)))
    };
    var z = function (a, b, c) {
        var d;
        return function () {
            var e = this,
                f = arguments,
                g = function () {
                    d = null, a.apply(e, f)
                };
            c && clearTimeout(d);
            if (c || !d) d = setTimeout(g, b)
        }
    };
    w.throttle = function (a, b) {
        return z(a, b, !1)
    }, w.debounce = function (a, b) {
        return z(a, b, !0)
    }, w.once = function (a) {
        var b = !1,
            c;
        return function () {
            return b ? c : (b = !0, c = a.apply(this, arguments))
        }
    }, w.wrap = function (a, b) {
        return function () {
            var c = [a].concat(g.call(arguments));
            return b.apply(this, c)
        }
    }, w.compose = function () {
        var a = g.call(arguments);
        return function () {
            var b = g.call(arguments);
            for (var c = a.length - 1; c >= 0; c--) b = [a[c].apply(this, b)];
            return b[0]
        }
    }, w.after = function (a, b) {
        return function () {
            if (--a < 1) return b.apply(this, arguments)
        }
    }, w.keys = u || function (a) {
        if (a !== Object(a)) throw new TypeError("Invalid object");
        var b = [];
        for (var c in a) j.call(a, c) && (b[b.length] = c);
        return b
    }, w.values = function (a) {
        return w.map(a, w.identity)
    }, w.functions = w.methods = function (a) {
        var b = [];
        for (var c in a) w.isFunction(a[c]) && b.push(c);
        return b.sort()
    }, w.extend = function (a) {
        return x(g.call(arguments, 1), function (b) {
            for (var c in b) b[c] !== void 0 && (a[c] = b[c])
        }), a
    }, w.defaults = function (a) {
        return x(g.call(arguments, 1), function (b) {
            for (var c in b) a[c] == null && (a[c] = b[c])
        }), a
    }, w.clone = function (a) {
        return w.isArray(a) ? a.slice() : w.extend({}, a)
    }, w.tap = function (a, b) {
        return b(a), a
    }, w.isEqual = function (a, b) {
        if (a === b) return !0;
        var c = typeof a,
            d = typeof b;
        if (c != d) return !1;
        if (a == b) return !0;
        if (!a && b || a && !b) return !1;
        a._chain && (a = a._wrapped), b._chain && (b = b._wrapped);
        if (a.isEqual) return a.isEqual(b);
        if (b.isEqual) return b.isEqual(a);
        if (w.isDate(a) && w.isDate(b)) return a.getTime() === b.getTime();
        if (w.isNaN(a) && w.isNaN(b)) return !1;
        if (w.isRegExp(a) && w.isRegExp(b)) return a.source === b.source && a.global === b.global && a.ignoreCase === b.ignoreCase && a.multiline === b.multiline;
        if (c !== "object") return !1;
        if (a.length && a.length !== b.length) return !1;
        var e = w.keys(a),
            f = w.keys(b);
        if (e.length != f.length) return !1;
        for (var g in a) if (!(g in b) || !w.isEqual(a[g], b[g])) return !1;
        return !0
    }, w.isEmpty = function (a) {
        if (w.isArray(a) || w.isString(a)) return a.length === 0;
        for (var b in a) if (j.call(a, b)) return !1;
        return !0
    }, w.isElement = function (a) {
        return !!a && a.nodeType == 1
    }, w.isArray = t || function (a) {
        return i.call(a) === "[object Array]"
    }, w.isObject = function (a) {
        return a === Object(a)
    }, w.isArguments = function (a) {
        return !!a && !! j.call(a, "callee")
    }, w.isFunction = function (a) {
        return !!(a && a.constructor && a.call && a.apply)
    }, w.isString = function (a) {
        return !!(a === "" || a && a.charCodeAt && a.substr)
    }, w.isNumber = function (a) {
        return !!(a === 0 || a && a.toExponential && a.toFixed)
    }, w.isNaN = function (a) {
        return a !== a
    }, w.isBoolean = function (a) {
        return a === !0 || a === !1
    }, w.isDate = function (a) {
        return !!(a && a.getTimezoneOffset && a.setUTCFullYear)
    }, w.isRegExp = function (a) {
        return !(!(a && a.test && a.exec) || !a.ignoreCase && a.ignoreCase !== !1)
    }, w.isNull = function (a) {
        return a === null
    }, w.isUndefined = function (a) {
        return a === void 0
    }, w.noConflict = function () {
        return a._ = b, this
    }, w.identity = function (a) {
        return a
    }, w.times = function (a, b, c) {
        for (var d = 0; d < a; d++) b.call(c, d)
    }, w.mixin = function (a) {
        x(w.functions(a), function (b) {
            D(b, w[b] = a[b])
        })
    };
    var A = 0;
    w.uniqueId = function (a) {
        var b = A++;
        return a ? a + b : b
    }, w.templateSettings = {
        evaluate: /<%([\s\S]+?)%>/g,
        interpolate: /<%=([\s\S]+?)%>/g
    }, w.template = function (a, b) {
        var c = w.templateSettings,
            d = "var __p=[],print=function(){__p.push.apply(__p,arguments);};with(obj||{}){__p.push('" + a.replace(/\\/g, "\\\\").replace(/'/g, "\\'").replace(c.interpolate, function (a, b) {
                return "'," + b.replace(/\\'/g, "'") + ",'"
            }).replace(c.evaluate || null, function (a, b) {
                return "');" + b.replace(/\\'/g, "'").replace(/[\r\n\t]/g, " ") + "__p.push('"
            }).replace(/\r/g, "\\r").replace(/\n/g, "\\n").replace(/\t/g, "\\t") + "');}return __p.join('');",
            e = new Function("obj", d);
        return b ? e(b) : e
    };
    var B = function (a) {
        this._wrapped = a
    };
    w.prototype = B.prototype;
    var C = function (a, b) {
        return b ? w(a).chain() : a
    }, D = function (a, b) {
        B.prototype[a] = function () {
            var a = g.call(arguments);
            return h.call(a, this._wrapped), C(b.apply(w, a), this._chain)
        }
    };
    w.mixin(w), x(["pop", "push", "reverse", "shift", "sort", "splice", "unshift"], function (a) {
        var b = d[a];
        B.prototype[a] = function () {
            return b.apply(this._wrapped, arguments), C(this._wrapped, this._chain)
        }
    }), x(["concat", "join", "slice"], function (a) {
        var b = d[a];
        B.prototype[a] = function () {
            return C(b.apply(this._wrapped, arguments), this._chain)
        }
    }), B.prototype.chain = function () {
        return this._chain = !0, this
    }, B.prototype.value = function () {
        return this._wrapped
    }
}(),
function () {
    var a = this,
        b = a.Backbone,
        c;
    typeof exports != "undefined" ? c = exports : c = a.Backbone = {}, c.VERSION = "0.5.3";
    var d = a._;
    !d && typeof require != "undefined" && (d = require("underscore")._);
    var e = a.jQuery || a.Zepto;
    c.noConflict = function () {
        return a.Backbone = b, this
    }, c.emulateHTTP = !1, c.emulateJSON = !1, c.Events = {
        bind: function (a, b, c) {
            var d = this._callbacks || (this._callbacks = {}),
                e = d[a] || (d[a] = []);
            return e.push([b, c]), this
        },
        unbind: function (a, b) {
            var c;
            if (!a) this._callbacks = {};
            else if (c = this._callbacks) if (!b) c[a] = [];
            else {
                var d = c[a];
                if (!d) return this;
                for (var e = 0, f = d.length; e < f; e++) if (d[e] && b === d[e][0]) {
                    d[e] = null;
                    break
                }
            }
            return this
        },
        trigger: function (a) {
            var b, c, d, e, f, g = 2;
            if (!(c = this._callbacks)) return this;
            while (g--) {
                d = g ? a : "all";
                if (b = c[d]) for (var h = 0, i = b.length; h < i; h++)(e = b[h]) ? (f = g ? Array.prototype.slice.call(arguments, 1) : arguments, e[0].apply(e[1] || this, f)) : (b.splice(h, 1), h--, i--)
            }
            return this
        }
    }, c.Model = function (a, b) {
        var c;
        a || (a = {});
        if (c = this.defaults) d.isFunction(c) && (c = c.call(this)), a = d.extend({}, c, a);
        this.attributes = {}, this._escapedAttributes = {}, this.cid = d.uniqueId("c"), this.set(a, {
            silent: !0
        }), this._changed = !1, this._previousAttributes = d.clone(this.attributes), b && b.collection && (this.collection = b.collection), this.initialize(a, b)
    }, d.extend(c.Model.prototype, c.Events, {
        _previousAttributes: null,
        _changed: !1,
        idAttribute: "id",
        initialize: function () {},
        toJSON: function () {
            return d.clone(this.attributes)
        },
        get: function (a) {
            return this.attributes[a]
        },
        escape: function (a) {
            var b;
            if (b = this._escapedAttributes[a]) return b;
            var c = this.attributes[a];
            return this._escapedAttributes[a] = w(c == null ? "" : "" + c)
        },
        has: function (a) {
            return this.attributes[a] != null
        },
        set: function (a, b) {
            b || (b = {});
            if (!a) return this;
            a.attributes && (a = a.attributes);
            var c = this.attributes,
                e = this._escapedAttributes;
            if (!b.silent && this.validate && !this._performValidation(a, b)) return !1;
            this.idAttribute in a && (this.id = a[this.idAttribute]);
            var f = this._changing;
            this._changing = !0;
            for (var g in a) {
                var h = a[g];
                d.isEqual(c[g], h) || (c[g] = h, delete e[g], this._changed = !0, b.silent || this.trigger("change:" + g, this, h, b))
            }
            return !f && !b.silent && this._changed && this.change(b), this._changing = !1, this
        },
        unset: function (a, b) {
            if (a in this.attributes) {
                b || (b = {});
                var c = this.attributes[a],
                    d = {};
                return d[a] = void 0, !b.silent && this.validate && !this._performValidation(d, b) ? !1 : (delete this.attributes[a], delete this._escapedAttributes[a], a == this.idAttribute && delete this.id, this._changed = !0, b.silent || (this.trigger("change:" + a, this, void 0, b), this.change(b)), this)
            }
            return this
        },
        clear: function (a) {
            a || (a = {});
            var b, c = this.attributes,
                d = {};
            for (b in c) d[b] = void 0;
            if (!a.silent && this.validate && !this._performValidation(d, a)) return !1;
            this.attributes = {}, this._escapedAttributes = {}, this._changed = !0;
            if (!a.silent) {
                for (b in c) this.trigger("change:" + b, this, void 0, a);
                this.change(a)
            }
            return this
        },
        fetch: function (a) {
            a || (a = {});
            var b = this,
                d = a.success;
            return a.success = function (c, e, f) {
                if (!b.set(b.parse(c, f), a)) return !1;
                d && d(b, c)
            }, a.error = v(a.error, b, a), (this.sync || c.sync).call(this, "read", this, a)
        },
        save: function (a, b) {
            b || (b = {});
            if (a && !this.set(a, b)) return !1;
            var d = this,
                e = b.success;
            b.success = function (a, c, f) {
                if (!d.set(d.parse(a, f), b)) return !1;
                e && e(d, a, f)
            }, b.error = v(b.error, d, b);
            var f = this.isNew() ? "create" : "update";
            return (this.sync || c.sync).call(this, f, this, b)
        },
        destroy: function (a) {
            a || (a = {});
            if (this.isNew()) return this.trigger("destroy", this, this.collection, a);
            var b = this,
                d = a.success;
            return a.success = function (c) {
                b.trigger("destroy", b, b.collection, a), d && d(b, c)
            }, a.error = v(a.error, b, a), (this.sync || c.sync).call(this, "delete", this, a)
        },
        url: function () {
            var a = t(this.collection) || this.urlRoot || u();
            return this.isNew() ? a : a + (a.charAt(a.length - 1) == "/" ? "" : "/") + encodeURIComponent(this.id)
        },
        parse: function (a, b) {
            return a
        },
        clone: function () {
            return new this.constructor(this)
        },
        isNew: function () {
            return this.id == null
        },
        change: function (a) {
            this.trigger("change", this, a), this._previousAttributes = d.clone(this.attributes), this._changed = !1
        },
        hasChanged: function (a) {
            return a ? this._previousAttributes[a] != this.attributes[a] : this._changed
        },
        changedAttributes: function (a) {
            a || (a = this.attributes);
            var b = this._previousAttributes,
                c = !1;
            for (var e in a) d.isEqual(b[e], a[e]) || (c = c || {}, c[e] = a[e]);
            return c
        },
        previous: function (a) {
            return !a || !this._previousAttributes ? null : this._previousAttributes[a]
        },
        previousAttributes: function () {
            return d.clone(this._previousAttributes)
        },
        _performValidation: function (a, b) {
            var c = this.validate(a);
            return c ? (b.error ? b.error(this, c, b) : this.trigger("error", this, c, b), !1) : !0
        }
    }), c.Collection = function (a, b) {
        b || (b = {}), b.comparator && (this.comparator = b.comparator), d.bindAll(this, "_onModelEvent", "_removeReference"), this._reset(), a && this.reset(a, {
            silent: !0
        }), this.initialize.apply(this, arguments)
    }, d.extend(c.Collection.prototype, c.Events, {
        model: c.Model,
        initialize: function () {},
        toJSON: function () {
            return this.map(function (a) {
                return a.toJSON()
            })
        },
        add: function (a, b) {
            if (d.isArray(a)) for (var c = 0, e = a.length; c < e; c++) this._add(a[c], b);
            else this._add(a, b);
            return this
        },
        remove: function (a, b) {
            if (d.isArray(a)) for (var c = 0, e = a.length; c < e; c++) this._remove(a[c], b);
            else this._remove(a, b);
            return this
        },
        get: function (a) {
            return a == null ? null : this._byId[a.id != null ? a.id : a]
        },
        getByCid: function (a) {
            return a && this._byCid[a.cid || a]
        },
        at: function (a) {
            return this.models[a]
        },
        sort: function (a) {
            a || (a = {});
            if (!this.comparator) throw new Error("Cannot sort a set without a comparator");
            return this.models = this.sortBy(this.comparator), a.silent || this.trigger("reset", this, a), this
        },
        pluck: function (a) {
            return d.map(this.models, function (b) {
                return b.get(a)
            })
        },
        reset: function (a, b) {
            return a || (a = []), b || (b = {}), this.each(this._removeReference), this._reset(), this.add(a, {
                silent: !0
            }), b.silent || this.trigger("reset", this, b), this
        },
        fetch: function (a) {
            a || (a = {});
            var b = this,
                d = a.success;
            return a.success = function (c, e, f) {
                b[a.add ? "add" : "reset"](b.parse(c, f), a), d && d(b, c)
            }, a.error = v(a.error, b, a), (this.sync || c.sync).call(this, "read", this, a)
        },
        create: function (a, b) {
            var c = this;
            b || (b = {}), a = this._prepareModel(a, b);
            if (!a) return !1;
            var d = b.success;
            return b.success = function (a, e, f) {
                c.add(a, b), d && d(a, e, f)
            }, a.save(null, b), a
        },
        parse: function (a, b) {
            return a
        },
        chain: function () {
            return d(this.models).chain()
        },
        _reset: function (a) {
            this.length = 0, this.models = [], this._byId = {}, this._byCid = {}
        },
        _prepareModel: function (a, b) {
            if (a instanceof c.Model) a.collection || (a.collection = this);
            else {
                var d = a;
                a = new this.model(d, {
                    collection: this
                }), a.validate && !a._performValidation(d, b) && (a = !1)
            }
            return a
        },
        _add: function (a, b) {
            b || (b = {}), a = this._prepareModel(a, b);
            if (!a) return !1;
            var c = this.getByCid(a);
            if (c) throw new Error(["Can't add the same model to a set twice", c.id]);
            this._byId[a.id] = a, this._byCid[a.cid] = a;
            var d = b.at != null ? b.at : this.comparator ? this.sortedIndex(a, this.comparator) : this.length;
            return this.models.splice(d, 0, a), a.bind("all", this._onModelEvent), this.length++, b.silent || a.trigger("add", a, this, b), a
        },
        _remove: function (a, b) {
            return b || (b = {}), a = this.getByCid(a) || this.get(a), a ? (delete this._byId[a.id], delete this._byCid[a.cid], this.models.splice(this.indexOf(a), 1), this.length--, b.silent || a.trigger("remove", a, this, b), this._removeReference(a), a) : null
        },
        _removeReference: function (a) {
            this == a.collection && delete a.collection, a.unbind("all", this._onModelEvent)
        },
        _onModelEvent: function (a, b, c, d) {
            if (a != "add" && a != "remove" || c == this) a == "destroy" && this._remove(b, d), b && a === "change:" + b.idAttribute && (delete this._byId[b.previous(b.idAttribute)], this._byId[b.id] = b), this.trigger.apply(this, arguments);
            else return
        }
    });
    var f = ["forEach", "each", "map", "reduce", "reduceRight", "find", "detect", "filter", "select", "reject", "every", "all", "some", "any", "include", "contains", "invoke", "max", "min", "sortBy", "sortedIndex", "toArray", "size", "first", "rest", "last", "without", "indexOf", "lastIndexOf", "isEmpty", "groupBy"];
    d.each(f, function (a) {
        c.Collection.prototype[a] = function () {
            return d[a].apply(d, [this.models].concat(d.toArray(arguments)))
        }
    }), c.Router = function (a) {
        a || (a = {}), a.routes && (this.routes = a.routes), this._bindRoutes(), this.initialize.apply(this, arguments)
    };
    var g = /:([\w\d]+)/g,
        h = /\*([\w\d]+)/g,
        i = /[-[\]{}()+?.,\\^$|#\s]/g;
    d.extend(c.Router.prototype, c.Events, {
        initialize: function () {},
        route: function (a, b, e) {
            c.history || (c.history = new c.History), d.isRegExp(a) || (a = this._routeToRegExp(a)), c.history.route(a, d.bind(function (c) {
                var d = this._extractParameters(a, c);
                e.apply(this, d), this.trigger.apply(this, ["route:" + b].concat(d))
            }, this))
        },
        navigate: function (a, b) {
            c.history.navigate(a, b)
        },
        _bindRoutes: function () {
            if (!this.routes) return;
            var a = [];
            for (var b in this.routes) a.unshift([b, this.routes[b]]);
            for (var c = 0, d = a.length; c < d; c++) this.route(a[c][0], a[c][1], this[a[c][1]])
        },
        _routeToRegExp: function (a) {
            return a = a.replace(i, "\\$&").replace(g, "([^/]*)").replace(h, "(.*?)"), new RegExp("^" + a + "$")
        },
        _extractParameters: function (a, b) {
            return a.exec(b).slice(1)
        }
    }), c.History = function () {
        this.handlers = [], d.bindAll(this, "checkUrl")
    };
    var j = /^#*/,
        k = /msie [\w.]+/,
        l = !1;
    d.extend(c.History.prototype, {
        interval: 50,
        getFragment: function (a, b) {
            if (a == null) if (this._hasPushState || b) {
                a = window.location.pathname;
                var c = window.location.search;
                c && (a += c), a.indexOf(this.options.root) == 0 && (a = a.substr(this.options.root.length))
            } else a = window.location.hash;
            return decodeURIComponent(a.replace(j, ""))
        },
        start: function (a) {
            if (l) throw new Error("Backbone.history has already been started");
            this.options = d.extend({}, {
                root: "/"
            }, this.options, a), this._wantsPushState = !! this.options.pushState, this._hasPushState = !! (this.options.pushState && window.history && window.history.pushState);
            var b = this.getFragment(),
                c = document.documentMode,
                f = k.exec(navigator.userAgent.toLowerCase()) && (!c || c <= 7);
            f && (this.iframe = e('<iframe src="javascript:0" tabindex="-1" />').hide().appendTo("body")[0].contentWindow, this.navigate(b)), this._hasPushState ? e(window).bind("popstate", this.checkUrl) : "onhashchange" in window && !f ? e(window).bind("hashchange", this.checkUrl) : setInterval(this.checkUrl, this.interval), this.fragment = b, l = !0;
            var g = window.location,
                h = g.pathname == this.options.root;
            if (this._wantsPushState && !this._hasPushState && !h) return this.fragment = this.getFragment(null, !0), window.location.replace(this.options.root + "#" + this.fragment), !0;
            this._wantsPushState && this._hasPushState && h && g.hash && (this.fragment = g.hash.replace(j, ""), window.history.replaceState({}, document.title, g.protocol + "//" + g.host + this.options.root + this.fragment));
            if (!this.options.silent) return this.loadUrl()
        },
        route: function (a, b) {
            this.handlers.unshift({
                route: a,
                callback: b
            })
        },
        checkUrl: function (a) {
            var b = this.getFragment();
            b == this.fragment && this.iframe && (b = this.getFragment(this.iframe.location.hash));
            if (b == this.fragment || b == decodeURIComponent(this.fragment)) return !1;
            this.iframe && this.navigate(b), this.loadUrl() || this.loadUrl(window.location.hash)
        },
        loadUrl: function (a) {
            var b = this.fragment = this.getFragment(a),
                c = d.any(this.handlers, function (a) {
                    if (a.route.test(b)) return a.callback(b), !0
                });
            return c
        },
        navigate: function (a, b) {
            var c = (a || "").replace(j, "");
            if (this.fragment == c || this.fragment == decodeURIComponent(c)) return;
            if (this._hasPushState) {
                var d = window.location;
                c.indexOf(this.options.root) != 0 && (c = this.options.root + c), this.fragment = c, window.history.pushState({}, document.title, d.protocol + "//" + d.host + c)
            } else window.location.hash = this.fragment = c, this.iframe && c != this.getFragment(this.iframe.location.hash) && (this.iframe.document.open().close(), this.iframe.location.hash = c);
            b && this.loadUrl(a)
        }
    }), c.View = function (a) {
        this.cid = d.uniqueId("view"), this._configure(a || {}), this._ensureElement(), this.delegateEvents(), this.initialize.apply(this, arguments)
    };
    var m = function (a) {
        return e(a, this.el)
    }, n = /^(\S+)\s*(.*)$/,
        o = ["model", "collection", "el", "id", "attributes", "className", "tagName"];
    d.extend(c.View.prototype, c.Events, {
        tagName: "div",
        $: m,
        initialize: function () {},
        render: function () {
            return this
        },
        remove: function () {
            return e(this.el).remove(), this
        },
        make: function (a, b, c) {
            var d = document.createElement(a);
            return b && e(d).attr(b), c && e(d).html(c), d
        },
        delegateEvents: function (a) {
            if (!a && !(a = this.events)) return;
            d.isFunction(a) && (a = a.call(this)), e(this.el).unbind(".delegateEvents" + this.cid);
            for (var b in a) {
                var c = this[a[b]];
                if (!c) throw new Error('Event "' + a[b] + '" does not exist');
                var f = b.match(n),
                    g = f[1],
                    h = f[2];
                c = d.bind(c, this), g += ".delegateEvents" + this.cid, h === "" ? e(this.el).bind(g, c) : e(this.el).delegate(h, g, c)
            }
        },
        _configure: function (a) {
            this.options && (a = d.extend({}, this.options, a));
            for (var b = 0, c = o.length; b < c; b++) {
                var e = o[b];
                a[e] && (this[e] = a[e])
            }
            this.options = a
        },
        _ensureElement: function () {
            if (!this.el) {
                var a = this.attributes || {};
                this.id && (a.id = this.id), this.className && (a["class"] = this.className), this.el = this.make(this.tagName, a)
            } else d.isString(this.el) && (this.el = e(this.el).get(0))
        }
    });
    var p = function (a, b) {
        var c = s(this, a, b);
        return c.extend = this.extend, c
    };
    c.Model.extend = c.Collection.extend = c.Router.extend = c.View.extend = p;
    var q = {
        create: "POST",
        update: "PUT",
        "delete": "DELETE",
        read: "GET"
    };
    c.sync = function (a, b, f) {
        var g = q[a],
            h = d.extend({
                type: g,
                dataType: "json"
            }, f);
        return h.url || (h.url = t(b) || u()), !h.data && b && (a == "create" || a == "update") && (h.contentType = "application/json", h.data = JSON.stringify(b.toJSON())), c.emulateJSON && (h.contentType = "application/x-www-form-urlencoded", h.data = h.data ? {
            model: h.data
        } : {}), c.emulateHTTP && (g === "PUT" || g === "DELETE") && (c.emulateJSON && (h.data._method = g), h.type = "POST", h.beforeSend = function (a) {
            a.setRequestHeader("X-HTTP-Method-Override", g)
        }), h.type !== "GET" && !c.emulateJSON && (h.processData = !1), e.ajax(h)
    };
    var r = function () {}, s = function (a, b, c) {
        var e;
        return b && b.hasOwnProperty("constructor") ? e = b.constructor : e = function () {
            return a.apply(this, arguments)
        }, d.extend(e, a), r.prototype = a.prototype, e.prototype = new r, b && d.extend(e.prototype, b), c && d.extend(e, c), e.prototype.constructor = e, e.__super__ = a.prototype, e
    }, t = function (a) {
        return !a || !a.url ? null : d.isFunction(a.url) ? a.url() : a.url
    }, u = function () {
        throw new Error('A "url" property or function must be specified')
    }, v = function (a, b, c) {
        return function (d) {
            a ? a(b, d, c) : b.trigger("error", b, d, c)
        }
    }, w = function (a) {
        return a.replace(/&(?!\w+;|#\d+;|#x[\da-f]+;)/gi, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#x27;").replace(/\//g, "&#x2F;")
    }
}.call(this), define("lib/backbone", function () {}),
function (define) {
    define("neo4j/webadmin/modules/dashboard/views/base", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><h1 class="pad">Neo4j web administration</h1><ul class="info_list"><li><h3>Server url</h3><p>' + server.url + "</p></li><li><h3>Kernel version</h3><p>" + server.version + '</p></li></ul><p class="pad">For more information, help and examples, please visit <a href="http://neo4j.org">the Neo4j community site</a>.</p><div class="foldout"><h2><a href="#" class="foldout_trigger">More about Charts</a></h2><div class="foldout_content pad"><p>To the right are charts that show the total number of primitive entities in the database over time.</p><p>You can select the timespan to show with the links in the top right corner of the chart.</p><p>To get specific info of some point in any chart, simply hover the mouse over the chart line.</p><p>The charts are automatically updated every three seconds.</p></div></div><div class="foldout"><h2><a href="#" class="foldout_trigger">More about KPIs</a></h2><div class="foldout_content pad"><p>Above the charts are one to three boxes showing different sets of Key Point of Interests (KPIs). These are updated every three seconds.</p><p>Depending on what neo4j distribution you are running, the number of KPIs available will differ.</p><h3>A note on primitive counts</h3><p>The number of nodes, properties and relations are estimates based on file sizes. Actually counting the nodes is not done for performance reasons. These are usually very accurate, but in certain cases if you have experienced a database crash and subsequent recovery, they may be very wrong.</p><p>How wrong the numbers are is entirely based on the operations you were doing right before the crash. There is currently no automated way to remedy this problem.</p><h3>A note on disk size</h3><p>Disk size KPIs are only available in neo4j enterprise edition.</p><p>Because of the way neo4j stores data, the different storage files (nodestore, relationshipstore etc.) will sometimes be very small before the server has recieved any requests.</p><p>It may be surprising to see the storage files grow rapidly when server first recieves requests, but the measurements will actually be correctly reflecting the disk size of the storage files.</p><p>This is only occurs if you are working on a relatively small graph.</p></div></div></div><div class="workarea with-sidebar"><div class="pad" id="dashboard-info"></div><div class="break"></div><div id="dashboard-charts"></div></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/dashboard/views/info", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="pad"><table cellspacing="0" class="info-table"><tbody>' + function () {
                return primitives.isDataAvailable() ? '<tr><td><div class="box"><div class="value">' + htmlEscape(fancyNumber(primitives.get("primitives").NumberOfNodeIdsInUse)) + '</div><div class="title">nodes</div></div></td><td><div class="box"><div class="value">' + htmlEscape(fancyNumber(primitives.get("primitives").NumberOfPropertyIdsInUse)) + '</div><div class="title">properties</div></div></td><td><div class="box"><div class="value">' + htmlEscape(fancyNumber(primitives.get("primitives").NumberOfRelationshipIdsInUse)) + '</div><div class="title">relationships</div></div></td><td><div class="box"><div class="value">' + htmlEscape(fancyNumber(primitives.get("primitives").NumberOfRelationshipTypeIdsInUse)) + '</div><div class="title">relationship types</div></div></td></tr>' : ""
            }.call(this) + function () {
                return diskUsage.isDataAvailable() ? '<tr><td></td><td><div class="box"><div class="value">' + htmlEscape(fancyNumber(Math.round(diskUsage.getDatabaseSize() / 1048576)) + " MB") + '</div><div class="title">database disk usage</div></div></td><td><div class="box"><div class="value">' + htmlEscape(fancyNumber(Math.round(diskUsage.getLogicalLogSize() / 1048576)) + " MB") + '</div><div class="title">logical log disk usage</div></div></td><td></td></tr>' : ""
            }.call(this) + '</tbody></table><div class="break"></div></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    define("ribcage/ui/NumberFormatter", [], function () {
        var a;
        return a = function () {
            function a() {}
            return a.fancy = function (a) {
                var b, c, d;
                a = "" + a, c = [], b = a.length, d = 0;
                while (--b >= 0) d++ % 3 === 0 && c.push(" "), c.push(a[b]);
                return c.reverse().join("")
            }, a
        }()
    })
}.call(this), define("lib/DateFormat", [], function () {
    var a = function () {
        var b = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
            c = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
            d = /[^-+\dA-Z]/g,
            e = function (a, b) {
                a = String(a), b = b || 2;
                while (a.length < b) a = "0" + a;
                return a
            };
        return function (f, g, h) {
            var i = a;
            arguments.length == 1 && Object.prototype.toString.call(f) == "[object String]" && !/\d/.test(f) && (g = f, f = undefined), f = f ? new Date(f) : new Date;
            if (isNaN(f)) throw SyntaxError("invalid date");
            g = String(i.masks[g] || g || i.masks["default"]), g.slice(0, 4) == "UTC:" && (g = g.slice(4), h = !0);
            var j = h ? "getUTC" : "get",
                k = f[j + "Date"](),
                l = f[j + "Day"](),
                m = f[j + "Month"](),
                n = f[j + "FullYear"](),
                o = f[j + "Hours"](),
                p = f[j + "Minutes"](),
                q = f[j + "Seconds"](),
                r = f[j + "Milliseconds"](),
                s = h ? 0 : f.getTimezoneOffset(),
                t = {
                    d: k,
                    dd: e(k),
                    ddd: i.i18n.dayNames[l],
                    dddd: i.i18n.dayNames[l + 7],
                    m: m + 1,
                    mm: e(m + 1),
                    mmm: i.i18n.monthNames[m],
                    mmmm: i.i18n.monthNames[m + 12],
                    yy: String(n).slice(2),
                    yyyy: n,
                    h: o % 12 || 12,
                    hh: e(o % 12 || 12),
                    H: o,
                    HH: e(o),
                    M: p,
                    MM: e(p),
                    s: q,
                    ss: e(q),
                    l: e(r, 3),
                    L: e(r > 99 ? Math.round(r / 10) : r),
                    t: o < 12 ? "a" : "p",
                    tt: o < 12 ? "am" : "pm",
                    T: o < 12 ? "A" : "P",
                    TT: o < 12 ? "AM" : "PM",
                    Z: h ? "UTC" : (String(f).match(c) || [""]).pop().replace(d, ""),
                    o: (s > 0 ? "-" : "+") + e(Math.floor(Math.abs(s) / 60) * 100 + Math.abs(s) % 60, 4),
                    S: ["th", "st", "nd", "rd"][k % 10 > 3 ? 0 : (k % 100 - k % 10 != 10) * k % 10]
                };
            return g.replace(b, function (a) {
                return a in t ? t[a] : a.slice(1, a.length - 1)
            })
        }
    }();
    return a.masks = {
        "default": "ddd mmm dd yyyy HH:MM:ss",
        shortDate: "m/d/yy",
        mediumDate: "mmm d, yyyy",
        longDate: "mmmm d, yyyy",
        fullDate: "dddd, mmmm d, yyyy",
        shortTime: "h:MM TT",
        mediumTime: "h:MM:ss TT",
        longTime: "h:MM:ss TT Z",
        isoDate: "yyyy-mm-dd",
        isoTime: "HH:MM:ss",
        isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
        isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
    }, a.i18n = {
        dayNames: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        monthNames: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    }, {
        format: a
    }
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("ribcage/security/HtmlEscaper", [], function () {
        var b;
        return b = function () {
            function b() {
                this.replaceAll = a(this.replaceAll, this), this.escape = a(this.escape, this)
            }
            return b.prototype.escape = function (a, b) {
                return b == null && (b = !1), a = this.replaceAll(a, [
                    [/&/g, "&amp;"],
                    [/</g, "&lt;"],
                    [/>/g, "&gt;"],
                    [/"/g, "&quot;"],
                    [/'/g, "&#x27;"],
                    [/\//g, "&#x2F;"]
                ]), b ? this.replaceAll(a, [
                    [/\ /g, "&nbsp;"]
                ]) : a
            }, b.prototype.replaceAll = function (a, b) {
                var c, d, e;
                a += "";
                for (d = 0, e = b.length; d < e; d++) c = b[d], a = a.replace(c[0], c[1]);
                return a
            }, b
        }()
    })
}.call(this),
function () {
    function i(a) {
        var b = a.currentTarget || a.srcElement,
            c, g, h;
        if (a.type === "load" || d.test(b.readyState)) {
            g = b.getAttribute("data-requiremodule"), f[g] = !0;
            for (c = 0; h = e[c]; c++) if (f[h.name]) h.req([h.name], h.onLoad);
            else break;
            c > 0 && e.splice(0, c), setTimeout(function () {
                b.parentNode.removeChild(b)
            }, 15)
        }
    }
    function j(a) {
        var b, c, d;
        a.setAttribute("data-orderloaded", "loaded");
        for (b = 0; d = h[b]; b++) {
            c = g[d];
            if (c && c.getAttribute("data-orderloaded") === "loaded") delete g[d], require.addScriptToDom(c);
            else break
        }
        b > 0 && h.splice(0, b)
    }
    var a = typeof document != "undefined" && typeof window != "undefined" && document.createElement("script"),
        b = a && (a.async || window.opera && Object.prototype.toString.call(window.opera) === "[object Opera]" || "MozAppearance" in document.documentElement.style),
        c = a && a.readyState === "uninitialized",
        d = /^(complete|loaded)$/,
        e = [],
        f = {}, g = {}, h = [];
    a = null, define("order", {
        version: "0.27.1",
        load: function (a, d, f, k) {
            var l = d.nameToUrl(a, null),
                m, n;
            require.s.skipAsync[l] = !0, b || k.isBuild ? d([a], f) : c && require.resourcesReady ? (n = require.s.contexts._, !n.urlFetched[l] && !n.loaded[a] && (n.urlFetched[l] = !0, require.resourcesReady(!1), n.scriptCount += 1, m = require.attach(l, n, a, null, null, j), g[a] = m, h.push(a)), d([a], f)) : d.isDefined && d.isDefined(a) ? d([a], function (a) {
                f(function () {
                    return a
                })
            }) : (e.push({
                name: a,
                req: d,
                onLoad: f
            }), require.attach(l, null, a, i, "script/cache"))
        }
    })
}(),
function (a) {
    a.color = {}, a.color.make = function (b, c, d, e) {
        var f = {};
        return f.r = b || 0, f.g = c || 0, f.b = d || 0, f.a = e != null ? e : 1, f.add = function (a, b) {
            for (var c = 0; c < a.length; ++c) f[a.charAt(c)] += b;
            return f.normalize()
        }, f.scale = function (a, b) {
            for (var c = 0; c < a.length; ++c) f[a.charAt(c)] *= b;
            return f.normalize()
        }, f.toString = function () {
            return f.a < 1 ? "rgba(" + [f.r, f.g, f.b, f.a].join(",") + ")" : "rgb(" + [f.r, f.g, f.b].join(",") + ")"
        }, f.normalize = function () {
            function a(a, b, c) {
                return b < a ? a : b > c ? c : b
            }
            return f.r = a(0, parseInt(f.r), 255), f.g = a(0, parseInt(f.g), 255), f.b = a(0, parseInt(f.b), 255), f.a = a(0, f.a, 1), f
        }, f.clone = function () {
            return a.color.make(f.r, f.b, f.g, f.a)
        }, f.normalize()
    }, a.color.extract = function (b, c) {
        var d;
        do {
            d = b.css(c).toLowerCase();
            if (d != "" && d != "transparent") break;
            b = b.parent()
        } while (!a.nodeName(b.get(0), "body"));
        return d == "rgba(0, 0, 0, 0)" && (d = "transparent"), a.color.parse(d)
    }, a.color.parse = function (c) {
        var d, e = a.color.make;
        if (d = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(c)) return e(parseInt(d[1], 10), parseInt(d[2], 10), parseInt(d[3], 10));
        if (d = /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]+(?:\.[0-9]+)?)\s*\)/.exec(c)) return e(parseInt(d[1], 10), parseInt(d[2], 10), parseInt(d[3], 10), parseFloat(d[4]));
        if (d = /rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(c)) return e(parseFloat(d[1]) * 2.55, parseFloat(d[2]) * 2.55, parseFloat(d[3]) * 2.55);
        if (d = /rgba\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\s*\)/.exec(c)) return e(parseFloat(d[1]) * 2.55, parseFloat(d[2]) * 2.55, parseFloat(d[3]) * 2.55, parseFloat(d[4]));
        if (d = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(c)) return e(parseInt(d[1], 16), parseInt(d[2], 16), parseInt(d[3], 16));
        if (d = /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(c)) return e(parseInt(d[1] + d[1], 16), parseInt(d[2] + d[2], 16), parseInt(d[3] + d[3], 16));
        var f = a.trim(c).toLowerCase();
        return f == "transparent" ? e(255, 255, 255, 0) : (d = b[f] || [0, 0, 0], e(d[0], d[1], d[2]))
    };
    var b = {
        aqua: [0, 255, 255],
        azure: [240, 255, 255],
        beige: [245, 245, 220],
        black: [0, 0, 0],
        blue: [0, 0, 255],
        brown: [165, 42, 42],
        cyan: [0, 255, 255],
        darkblue: [0, 0, 139],
        darkcyan: [0, 139, 139],
        darkgrey: [169, 169, 169],
        darkgreen: [0, 100, 0],
        darkkhaki: [189, 183, 107],
        darkmagenta: [139, 0, 139],
        darkolivegreen: [85, 107, 47],
        darkorange: [255, 140, 0],
        darkorchid: [153, 50, 204],
        darkred: [139, 0, 0],
        darksalmon: [233, 150, 122],
        darkviolet: [148, 0, 211],
        fuchsia: [255, 0, 255],
        gold: [255, 215, 0],
        green: [0, 128, 0],
        indigo: [75, 0, 130],
        khaki: [240, 230, 140],
        lightblue: [173, 216, 230],
        lightcyan: [224, 255, 255],
        lightgreen: [144, 238, 144],
        lightgrey: [211, 211, 211],
        lightpink: [255, 182, 193],
        lightyellow: [255, 255, 224],
        lime: [0, 255, 0],
        magenta: [255, 0, 255],
        maroon: [128, 0, 0],
        navy: [0, 0, 128],
        olive: [128, 128, 0],
        orange: [255, 165, 0],
        pink: [255, 192, 203],
        purple: [128, 0, 128],
        violet: [128, 0, 128],
        red: [255, 0, 0],
        silver: [192, 192, 192],
        white: [255, 255, 255],
        yellow: [255, 255, 0]
    }
}(jQuery),
function (a) {
    function b(b, d, e, f) {
        function x(a, b) {
            b = [w].concat(b);
            for (var c = 0; c < a.length; ++c) a[c].apply(this, b)
        }
        function y() {
            for (var b = 0; b < f.length; ++b) {
                var c = f[b];
                c.init(w), c.options && a.extend(!0, h, c.options)
            }
        }
        function z(b) {
            var c;
            a.extend(!0, h, b), h.xaxis.color == null && (h.xaxis.color = h.grid.color), h.yaxis.color == null && (h.yaxis.color = h.grid.color), h.xaxis.tickColor == null && (h.xaxis.tickColor = h.grid.tickColor), h.yaxis.tickColor == null && (h.yaxis.tickColor = h.grid.tickColor), h.grid.borderColor == null && (h.grid.borderColor = h.grid.color), h.grid.tickColor == null && (h.grid.tickColor = a.color.parse(h.grid.color).scale("a", .22).toString());
            for (c = 0; c < Math.max(1, h.xaxes.length); ++c) h.xaxes[c] = a.extend(!0, {}, h.xaxis, h.xaxes[c]);
            for (c = 0; c < Math.max(1, h.yaxes.length); ++c) h.yaxes[c] = a.extend(!0, {}, h.yaxis, h.yaxes[c]);
            h.xaxis.noTicks && h.xaxis.ticks == null && (h.xaxis.ticks = h.xaxis.noTicks), h.yaxis.noTicks && h.yaxis.ticks == null && (h.yaxis.ticks = h.yaxis.noTicks), h.x2axis && (h.xaxes[1] = a.extend(!0, {}, h.xaxis, h.x2axis), h.xaxes[1].position = "top"), h.y2axis && (h.yaxes[1] = a.extend(!0, {}, h.yaxis, h.y2axis), h.yaxes[1].position = "right"), h.grid.coloredAreas && (h.grid.markings = h.grid.coloredAreas), h.grid.coloredAreasColor && (h.grid.markingsColor = h.grid.coloredAreasColor), h.lines && a.extend(!0, h.series.lines, h.lines), h.points && a.extend(!0, h.series.points, h.points), h.bars && a.extend(!0, h.series.bars, h.bars), h.shadowSize != null && (h.series.shadowSize = h.shadowSize);
            for (c = 0; c < h.xaxes.length; ++c) G(o, c + 1).options = h.xaxes[c];
            for (c = 0; c < h.yaxes.length; ++c) G(p, c + 1).options = h.yaxes[c];
            for (var d in v) h.hooks[d] && h.hooks[d].length && (v[d] = v[d].concat(h.hooks[d]));
            x(v.processOptions, [h])
        }
        function A(a) {
            g = B(a), H(), I()
        }
        function B(b) {
            var c = [];
            for (var d = 0; d < b.length; ++d) {
                var e = a.extend(!0, {}, h.series);
                b[d].data != null ? (e.data = b[d].data, delete b[d].data, a.extend(!0, e, b[d]), b[d].data = e.data) : e.data = b[d], c.push(e)
            }
            return c
        }
        function C(a, b) {
            var c = a[b + "axis"];
            return typeof c == "object" && (c = c.n), typeof c != "number" && (c = 1), c
        }
        function D() {
            return a.grep(o.concat(p), function (a) {
                return a
            })
        }
        function E(a) {
            var b = {}, c, d;
            for (c = 0; c < o.length; ++c) d = o[c], d && d.used && (b["x" + d.n] = d.c2p(a.left));
            for (c = 0; c < p.length; ++c) d = p[c], d && d.used && (b["y" + d.n] = d.c2p(a.top));
            return b.x1 !== undefined && (b.x = b.x1), b.y1 !== undefined && (b.y = b.y1), b
        }
        function F(a) {
            var b = {}, c, d, e;
            for (c = 0; c < o.length; ++c) {
                d = o[c];
                if (d && d.used) {
                    e = "x" + d.n, a[e] == null && d.n == 1 && (e = "x");
                    if (a[e] != null) {
                        b.left = d.p2c(a[e]);
                        break
                    }
                }
            }
            for (c = 0; c < p.length; ++c) {
                d = p[c];
                if (d && d.used) {
                    e = "y" + d.n, a[e] == null && d.n == 1 && (e = "y");
                    if (a[e] != null) {
                        b.top = d.p2c(a[e]);
                        break
                    }
                }
            }
            return b
        }
        function G(b, c) {
            return b[c - 1] || (b[c - 1] = {
                n: c,
                direction: b == o ? "x" : "y",
                options: a.extend(!0, {}, b == o ? h.xaxis : h.yaxis)
            }), b[c - 1]
        }
        function H() {
            var b, c = g.length,
                d = [],
                e = [];
            for (b = 0; b < g.length; ++b) {
                var f = g[b].color;
                f != null && (--c, typeof f == "number" ? e.push(f) : d.push(a.color.parse(g[b].color)))
            }
            for (b = 0; b < e.length; ++b) c = Math.max(c, e[b] + 1);
            var i = [],
                j = 0;
            b = 0;
            while (i.length < c) {
                var k;
                h.colors.length == b ? k = a.color.make(100, 100, 100) : k = a.color.parse(h.colors[b]);
                var l = j % 2 == 1 ? -1 : 1;
                k.scale("rgb", 1 + l * Math.ceil(j / 2) * .2), i.push(k), ++b, b >= h.colors.length && (b = 0, ++j)
            }
            var m = 0,
                n;
            for (b = 0; b < g.length; ++b) {
                n = g[b], n.color == null ? (n.color = i[m].toString(), ++m) : typeof n.color == "number" && (n.color = i[n.color].toString());
                if (n.lines.show == null) {
                    var q, r = !0;
                    for (q in n) if (n[q] && n[q].show) {
                        r = !1;
                        break
                    }
                    r && (n.lines.show = !0)
                }
                n.xaxis = G(o, C(n, "x")), n.yaxis = G(p, C(n, "y"))
            }
        }
        function I() {
            function t(a, b, c) {
                b < a.datamin && b != -d && (a.datamin = b), c > a.datamax && c != d && (a.datamax = c)
            }
            var b = Number.POSITIVE_INFINITY,
                c = Number.NEGATIVE_INFINITY,
                d = Number.MAX_VALUE,
                e, f, h, i, j, k, l, m, n, o, p, q, r, s;
            a.each(D(), function (a, d) {
                d.datamin = b, d.datamax = c, d.used = !1
            });
            for (e = 0; e < g.length; ++e) k = g[e], k.datapoints = {
                points: []
            }, x(v.processRawData, [k, k.data, k.datapoints]);
            for (e = 0; e < g.length; ++e) {
                k = g[e];
                var u = k.data,
                    w = k.datapoints.format;
                if (!w) {
                    w = [], w.push({
                        x: !0,
                        number: !0,
                        required: !0
                    }), w.push({
                        y: !0,
                        number: !0,
                        required: !0
                    });
                    if (k.bars.show || k.lines.show && k.lines.fill) w.push({
                        y: !0,
                        number: !0,
                        required: !1,
                        defaultValue: 0
                    }), k.bars.horizontal && (delete w[w.length - 1].y, w[w.length - 1].x = !0);
                    k.datapoints.format = w
                }
                if (k.datapoints.pointsize != null) continue;
                k.datapoints.pointsize = w.length, m = k.datapoints.pointsize, l = k.datapoints.points, insertSteps = k.lines.show && k.lines.steps, k.xaxis.used = k.yaxis.used = !0;
                for (f = h = 0; f < u.length; ++f, h += m) {
                    s = u[f];
                    var y = s == null;
                    if (!y) for (i = 0; i < m; ++i) q = s[i], r = w[i], r && (r.number && q != null && (q = +q, isNaN(q) ? q = null : q == Infinity ? q = d : q == -Infinity && (q = -d)), q == null && (r.required && (y = !0), r.defaultValue != null && (q = r.defaultValue))), l[h + i] = q;
                    if (y) for (i = 0; i < m; ++i) q = l[h + i], q != null && (r = w[i], r.x && t(k.xaxis, q, q), r.y && t(k.yaxis, q, q)), l[h + i] = null;
                    else if (insertSteps && h > 0 && l[h - m] != null && l[h - m] != l[h] && l[h - m + 1] != l[h + 1]) {
                        for (i = 0; i < m; ++i) l[h + m + i] = l[h + i];
                        l[h + 1] = l[h - m + 1], h += m
                    }
                }
            }
            for (e = 0; e < g.length; ++e) k = g[e], x(v.processDatapoints, [k, k.datapoints]);
            for (e = 0; e < g.length; ++e) {
                k = g[e], l = k.datapoints.points, m = k.datapoints.pointsize, w = k.datapoints.format;
                var z = b,
                    A = b,
                    B = c,
                    C = c;
                for (f = 0; f < l.length; f += m) {
                    if (l[f] == null) continue;
                    for (i = 0; i < m; ++i) {
                        q = l[f + i], r = w[i];
                        if (!r || q == d || q == -d) continue;
                        r.x && (q < z && (z = q), q > B && (B = q)), r.y && (q < A && (A = q), q > C && (C = q))
                    }
                }
                if (k.bars.show) {
                    var E = k.bars.align == "left" ? 0 : -k.bars.barWidth / 2;
                    k.bars.horizontal ? (A += E, C += E + k.bars.barWidth) : (z += E, B += E + k.bars.barWidth)
                }
                t(k.xaxis, z, B), t(k.yaxis, A, C)
            }
            a.each(D(), function (a, d) {
                d.datamin == b && (d.datamin = null), d.datamax == c && (d.datamax = null)
            })
        }
        function J(c, d) {
            var e = document.createElement("canvas");
            return e.className = d, e.width = r, e.height = s, c || a(e).css({
                position: "absolute",
                left: 0,
                top: 0
            }), a(e).appendTo(b), e.getContext || (e = window.G_vmlCanvasManager.initElement(e)), e.getContext("2d").save(), e
        }
        function K() {
            r = b.width(), s = b.height();
            if (r <= 0 || s <= 0) throw "Invalid dimensions for plot, width = " + r + ", height = " + s
        }
        function L(a) {
            a.width != r && (a.width = r), a.height != s && (a.height = s);
            var b = a.getContext("2d");
            b.restore(), b.save()
        }
        function M() {
            var c, d = b.children("canvas.flot-base"),
                e = b.children("canvas.flot-overlay");
            d.length == 0 || e == 0 ? (b.html(""), b.css({
                padding: 0
            }), b.css("position") == "static" && b.css("position", "relative"), K(), j = J(!0, "flot-base"), k = J(!1, "flot-overlay"), c = !1) : (j = d.get(0), k = e.get(0), c = !0), m = j.getContext("2d"), n = k.getContext("2d"), l = a(k), c && (b.data("plot").shutdown(), w.resize(), n.clearRect(0, 0, r, s), l.unbind(), b.children().not([j, k]).remove()), b.data("plot", w)
        }
        function N() {
            h.grid.hoverable && (l.mousemove(bn), l.mouseleave(bo)), h.grid.clickable && l.click(bp), x(v.bindEvents, [l])
        }
        function O() {
            bl && clearTimeout(bl), l.unbind("mousemove", bn), l.unbind("mouseleave", bo), l.unbind("click", bp), x(v.shutdown, [l])
        }
        function P(a) {
            function b(a) {
                return a
            }
            var c, d, e = a.options.transform || b,
                f = a.options.inverseTransform;
            a.direction == "x" ? (c = a.scale = t / Math.abs(e(a.max) - e(a.min)), d = Math.min(e(a.max), e(a.min))) : (c = a.scale = u / Math.abs(e(a.max) - e(a.min)), c = -c, d = Math.max(e(a.max), e(a.min))), e == b ? a.p2c = function (a) {
                return (a - d) * c
            } : a.p2c = function (a) {
                return (e(a) - d) * c
            }, f ? a.c2p = function (a) {
                return f(d + a / c)
            } : a.c2p = function (a) {
                return d + a / c
            }
        }
        function Q(a) {
            var b = a.options,
                c = a.ticks || [],
                d = b.labelWidth || 0,
                e = b.labelHeight || 0,
                f = a.font;
            m.save(), m.font = f.style + " " + f.variant + " " + f.weight + " " + f.size + "px '" + f.family + "'";
            for (var g = 0; g < c.length; ++g) {
                var h = c[g];
                h.lines = [], h.width = h.height = 0;
                if (!h.label) continue;
                var i = h.label.replace(/<br ?\/?>|\r\n|\r/g, "\n").split("\n");
                for (var j = 0; j < i.length; ++j) {
                    var k = {
                        text: i[j]
                    }, l = m.measureText(k.text);
                    k.width = l.width, k.height = l.height != null ? l.height : f.size, k.height += Math.round(f.size * .15), h.width = Math.max(k.width, h.width), h.height += k.height, h.lines.push(k)
                }
                b.labelWidth == null && (d = Math.max(d, h.width)), b.labelHeight == null && (e = Math.max(e, h.height))
            }
            m.restore(), a.labelWidth = Math.ceil(d), a.labelHeight = Math.ceil(e)
        }
        function R(b) {
            var c = b.labelWidth,
                d = b.labelHeight,
                e = b.options.position,
                f = b.options.tickLength,
                g = h.grid.axisMargin,
                i = h.grid.labelMargin,
                j = b.direction == "x" ? o : p,
                k, l = a.grep(j, function (a) {
                    return a && a.options.position == e && a.reserveSpace
                });
            a.inArray(b, l) == l.length - 1 && (g = 0);
            if (f == null) {
                var m = a.grep(j, function (a) {
                    return a && a.reserveSpace
                }),
                    n = a.inArray(b, m) == 0;
                n ? f = "full" : f = 5
            }
            isNaN(+f) || (i += +f), b.direction == "x" ? (d += i, e == "bottom" ? (q.bottom += d + g, b.box = {
                top: s - q.bottom,
                height: d
            }) : (b.box = {
                top: q.top + g,
                height: d
            }, q.top += d + g)) : (c += i, e == "left" ? (b.box = {
                left: q.left + g,
                width: c
            }, q.left += c + g) : (q.right += c + g, b.box = {
                left: r - q.right,
                width: c
            })), b.position = e, b.tickLength = f, b.box.padding = i, b.innermost = n
        }
        function S(a) {
            a.direction == "x" ? (a.box.left = q.left - a.labelWidth / 2, a.box.width = r - q.left - q.right + a.labelWidth) : (a.box.top = q.top - a.labelHeight / 2, a.box.height = s - q.bottom - q.top + a.labelHeight)
        }
        function T() {
            var b = h.grid.minBorderMargin,
                c = {
                    x: 0,
                    y: 0
                }, d, e;
            if (b == null) {
                b = 0;
                for (d = 0; d < g.length; ++d) b = Math.max(b, 2 * (g[d].points.radius + g[d].points.lineWidth / 2))
            }
            c.x = c.y = Math.ceil(b), a.each(D(), function (a, b) {
                var d = b.direction;
                b.reserveSpace && (c[d] = Math.ceil(Math.max(c[d], (d == "x" ? b.labelWidth : b.labelHeight) / 2)))
            }), q.left = Math.max(c.x, q.left), q.right = Math.max(c.x, q.right), q.top = Math.max(c.y, q.top), q.bottom = Math.max(c.y, q.bottom)
        }
        function U() {
            var c, d = D(),
                e = h.grid.show;
            for (var f in q) q[f] = e ? h.grid.borderWidth : 0;
            a.each(d, function (a, b) {
                b.show = b.options.show, b.show == null && (b.show = b.used), b.reserveSpace = b.show || b.options.reserveSpace, V(b)
            });
            if (e) {
                var g = {
                    style: b.css("font-style"),
                    size: Math.round(.8 * (+b.css("font-size").replace("px", "") || 13)),
                    variant: b.css("font-variant"),
                    weight: b.css("font-weight"),
                    family: b.css("font-family")
                }, i = a.grep(d, function (a) {
                    return a.reserveSpace
                });
                a.each(i, function (b, c) {
                    W(c), X(c), Y(c, c.ticks), c.font = a.extend({}, g, c.options.font), Q(c)
                });
                for (c = i.length - 1; c >= 0; --c) R(i[c]);
                T(), a.each(i, function (a, b) {
                    S(b)
                })
            }
            t = r - q.left - q.right, u = s - q.bottom - q.top, a.each(d, function (a, b) {
                P(b)
            }), bj()
        }
        function V(a) {
            var b = a.options,
                c = +(b.min != null ? b.min : a.datamin),
                d = +(b.max != null ? b.max : a.datamax),
                e = d - c;
            if (e == 0) {
                var f = d == 0 ? 1 : .01;
                b.min == null && (c -= f);
                if (b.max == null || b.min != null) d += f
            } else {
                var g = b.autoscaleMargin;
                g != null && (b.min == null && (c -= e * g, c < 0 && a.datamin != null && a.datamin >= 0 && (c = 0)), b.max == null && (d += e * g, d > 0 && a.datamax != null && a.datamax <= 0 && (d = 0)))
            }
            a.min = c, a.max = d
        }
        function W(b) {
            var d = b.options,
                e;
            typeof d.ticks == "number" && d.ticks > 0 ? e = d.ticks : e = .3 * Math.sqrt(b.direction == "x" ? r : s);
            var f = (b.max - b.min) / e,
                g, h, i, j, k, l, m;
            if (d.mode == "time") {
                var n = {
                    second: 1e3,
                    minute: 6e4,
                    hour: 36e5,
                    day: 864e5,
                    month: 2592e6,
                    year: 525949.2 * 60 * 1e3
                }, q = [
                    [1, "second"],
                    [2, "second"],
                    [5, "second"],
                    [10, "second"],
                    [30, "second"],
                    [1, "minute"],
                    [2, "minute"],
                    [5, "minute"],
                    [10, "minute"],
                    [30, "minute"],
                    [1, "hour"],
                    [2, "hour"],
                    [4, "hour"],
                    [8, "hour"],
                    [12, "hour"],
                    [1, "day"],
                    [2, "day"],
                    [3, "day"],
                    [.25, "month"],
                    [.5, "month"],
                    [1, "month"],
                    [2, "month"],
                    [3, "month"],
                    [6, "month"],
                    [1, "year"]
                ],
                    t = 0;
                d.minTickSize != null && (typeof d.tickSize == "number" ? t = d.tickSize : t = d.minTickSize[0] * n[d.minTickSize[1]]);
                for (var k = 0; k < q.length - 1; ++k) if (f < (q[k][0] * n[q[k][1]] + q[k + 1][0] * n[q[k + 1][1]]) / 2 && q[k][0] * n[q[k][1]] >= t) break;
                g = q[k][0], i = q[k][1], i == "year" && (l = Math.pow(10, Math.floor(Math.log(f / n.year) / Math.LN10)), m = f / n.year / l, m < 1.5 ? g = 1 : m < 3 ? g = 2 : m < 7.5 ? g = 5 : g = 10, g *= l), b.tickSize = d.tickSize || [g, i], h = function (a) {
                    var b = [],
                        d = a.tickSize[0],
                        e = a.tickSize[1],
                        f = new Date(a.min),
                        g = d * n[e];
                    e == "second" && f.setUTCSeconds(c(f.getUTCSeconds(), d)), e == "minute" && f.setUTCMinutes(c(f.getUTCMinutes(), d)), e == "hour" && f.setUTCHours(c(f.getUTCHours(), d)), e == "month" && f.setUTCMonth(c(f.getUTCMonth(), d)), e == "year" && f.setUTCFullYear(c(f.getUTCFullYear(), d)), f.setUTCMilliseconds(0), g >= n.minute && f.setUTCSeconds(0), g >= n.hour && f.setUTCMinutes(0), g >= n.day && f.setUTCHours(0), g >= n.day * 4 && f.setUTCDate(1), g >= n.year && f.setUTCMonth(0);
                    var h = 0,
                        i = Number.NaN,
                        j;
                    do {
                        j = i, i = f.getTime(), b.push(i);
                        if (e == "month") if (d < 1) {
                            f.setUTCDate(1);
                            var k = f.getTime();
                            f.setUTCMonth(f.getUTCMonth() + 1);
                            var l = f.getTime();
                            f.setTime(i + h * n.hour + (l - k) * d), h = f.getUTCHours(), f.setUTCHours(0)
                        } else f.setUTCMonth(f.getUTCMonth() + d);
                        else e == "year" ? f.setUTCFullYear(f.getUTCFullYear() + d) : f.setTime(i + g)
                    } while (i < a.max && i != j);
                    return b
                }, j = function (b, c) {
                    var e = new Date(b);
                    if (d.timeformat != null) return a.plot.formatDate(e, d.timeformat, d.monthNames);
                    var f = c.tickSize[0] * n[c.tickSize[1]],
                        g = c.max - c.min,
                        h = d.twelveHourClock ? " %p" : "";
                    return f < n.minute ? fmt = "%h:%M:%S" + h : f < n.day ? g < 2 * n.day ? fmt = "%h:%M" + h : fmt = "%b %d %h:%M" + h : f < n.month ? fmt = "%b %d" : f < n.year ? g < n.year ? fmt = "%b" : fmt = "%b %y" : fmt = "%y", a.plot.formatDate(e, fmt, d.monthNames)
                }
            } else {
                var u = d.tickDecimals,
                    v = -Math.floor(Math.log(f) / Math.LN10);
                u != null && v > u && (v = u), l = Math.pow(10, -v), m = f / l, m < 1.5 ? g = 1 : m < 3 ? (g = 2, m > 2.25 && (u == null || v + 1 <= u) && (g = 2.5, ++v)) : m < 7.5 ? g = 5 : g = 10, g *= l, d.minTickSize != null && g < d.minTickSize && (g = d.minTickSize), b.tickDecimals = Math.max(0, u != null ? u : v), b.tickSize = d.tickSize || g, h = function (a) {
                    var b = [],
                        d = c(a.min, a.tickSize),
                        e = 0,
                        f = Number.NaN,
                        g;
                    do g = f, f = d + e * a.tickSize, b.push(f), ++e;
                    while (f < a.max && f != g);
                    return b
                }, j = function (a, b) {
                    return a.toFixed(b.tickDecimals)
                }
            }
            if (d.alignTicksWithAxis != null) {
                var w = (b.direction == "x" ? o : p)[d.alignTicksWithAxis - 1];
                if (w && w.used && w != b) {
                    var x = h(b);
                    x.length > 0 && (d.min == null && (b.min = Math.min(b.min, x[0])), d.max == null && x.length > 1 && (b.max = Math.max(b.max, x[x.length - 1]))), h = function (a) {
                        var b = [],
                            c, d;
                        for (d = 0; d < w.ticks.length; ++d) c = (w.ticks[d].v - w.min) / (w.max - w.min), c = a.min + c * (a.max - a.min), b.push(c);
                        return b
                    };
                    if (!b.mode && d.tickDecimals == null) {
                        var y = Math.max(0, -Math.floor(Math.log(f) / Math.LN10) + 1),
                            z = h(b);
                        if (z.length <= 1 || !/\..*0$/.test((z[1] - z[0]).toFixed(y))) b.tickDecimals = y
                    }
                }
            }
            b.tickGenerator = h, a.isFunction(d.tickFormatter) ? b.tickFormatter = function (a, b) {
                return "" + d.tickFormatter(a, b)
            } : b.tickFormatter = j
        }
        function X(b) {
            var c = b.options.ticks,
                d = [];
            c == null || typeof c == "number" && c > 0 ? d = b.tickGenerator(b) : c && (a.isFunction(c) ? d = c(b) : d = c);
            var e, f;
            b.ticks = [];
            for (e = 0; e < d.length; ++e) {
                var g = null,
                    h = d[e];
                typeof h == "object" ? (f = +h[0], h.length > 1 && (g = h[1])) : f = +h, g == null && (g = b.tickFormatter(f, b)), isNaN(f) || b.ticks.push({
                    v: f,
                    label: g
                })
            }
        }
        function Y(a, b) {
            a.options.autoscaleMargin && b.length > 0 && (a.options.min == null && (a.min = Math.min(a.min, b[0].v)), a.options.max == null && b.length > 1 && (a.max = Math.max(a.max, b[b.length - 1].v)))
        }
        function Z() {
            m.clearRect(0, 0, r, s);
            var a = h.grid;
            a.show && a.backgroundColor && ba(), a.show && !a.aboveData && (bb(), bc());
            for (var b = 0; b < g.length; ++b) x(v.drawSeries, [m, g[b]]), bd(g[b]);
            x(v.draw, [m]), a.show && a.aboveData && (bb(), bc())
        }
        function _(a, b) {
            var c, d, e, f, g = D();
            for (i = 0; i < g.length; ++i) {
                c = g[i];
                if (c.direction == b) {
                    f = b + c.n + "axis", !a[f] && c.n == 1 && (f = b + "axis");
                    if (a[f]) {
                        d = a[f].from, e = a[f].to;
                        break
                    }
                }
            }
            a[f] || (c = b == "x" ? o[0] : p[0], d = a[b + "1"], e = a[b + "2"]);
            if (d != null && e != null && d > e) {
                var h = d;
                d = e, e = h
            }
            return {
                from: d,
                to: e,
                axis: c
            }
        }
        function ba() {
            m.save(), m.translate(q.left, q.top), m.fillStyle = by(h.grid.backgroundColor, u, 0, "rgba(255, 255, 255, 0)"), m.fillRect(0, 0, t, u), m.restore()
        }
        function bb() {
            var b;
            m.save(), m.translate(q.left, q.top);
            var c = h.grid.markings;
            if (c) {
                if (a.isFunction(c)) {
                    var d = w.getAxes();
                    d.xmin = d.xaxis.min, d.xmax = d.xaxis.max, d.ymin = d.yaxis.min, d.ymax = d.yaxis.max, c = c(d)
                }
                for (b = 0; b < c.length; ++b) {
                    var e = c[b],
                        f = _(e, "x"),
                        g = _(e, "y");
                    f.from == null && (f.from = f.axis.min), f.to == null && (f.to = f.axis.max), g.from == null && (g.from = g.axis.min), g.to == null && (g.to = g.axis.max);
                    if (f.to < f.axis.min || f.from > f.axis.max || g.to < g.axis.min || g.from > g.axis.max) continue;
                    f.from = Math.max(f.from, f.axis.min), f.to = Math.min(f.to, f.axis.max), g.from = Math.max(g.from, g.axis.min), g.to = Math.min(g.to, g.axis.max);
                    if (f.from == f.to && g.from == g.to) continue;
                    f.from = f.axis.p2c(f.from), f.to = f.axis.p2c(f.to), g.from = g.axis.p2c(g.from), g.to = g.axis.p2c(g.to), f.from == f.to || g.from == g.to ? (m.beginPath(), m.strokeStyle = e.color || h.grid.markingsColor, m.lineWidth = e.lineWidth || h.grid.markingsLineWidth, m.moveTo(f.from, g.from), m.lineTo(f.to, g.to), m.stroke()) : (m.fillStyle = e.color || h.grid.markingsColor, m.fillRect(f.from, g.to, f.to - f.from, g.from - g.to))
                }
            }
            var d = D(),
                i = h.grid.borderWidth;
            for (var j = 0; j < d.length; ++j) {
                var k = d[j],
                    l = k.box,
                    n = k.tickLength,
                    o, p, r, s;
                if (!k.show || k.ticks.length == 0) continue;
                m.strokeStyle = k.options.tickColor || a.color.parse(k.options.color).scale("a", .22).toString(), m.lineWidth = 1, k.direction == "x" ? (o = 0, n == "full" ? p = k.position == "top" ? 0 : u : p = l.top - q.top + (k.position == "top" ? l.height : 0)) : (p = 0, n == "full" ? o = k.position == "left" ? 0 : t : o = l.left - q.left + (k.position == "left" ? l.width : 0)), k.innermost || (m.beginPath(), r = s = 0, k.direction == "x" ? r = t : s = u, m.lineWidth == 1 && (o = Math.floor(o) + .5, p = Math.floor(p) + .5), m.moveTo(o, p), m.lineTo(o + r, p + s), m.stroke()), m.beginPath();
                for (b = 0; b < k.ticks.length; ++b) {
                    var v = k.ticks[b].v;
                    r = s = 0;
                    if (v < k.min || v > k.max || n == "full" && i > 0 && (v == k.min || v == k.max)) continue;
                    k.direction == "x" ? (o = k.p2c(v), s = n == "full" ? -u : n, k.position == "top" && (s = -s)) : (p = k.p2c(v), r = n == "full" ? -t : n, k.position == "left" && (r = -r)), m.lineWidth == 1 && (k.direction == "x" ? o = Math.floor(o) + .5 : p = Math.floor(p) + .5), m.moveTo(o, p), m.lineTo(o + r, p + s)
                }
                m.stroke()
            }
            i && (m.lineWidth = i, m.strokeStyle = h.grid.borderColor, m.strokeRect(-i / 2, -i / 2, t + i, u + i)), m.restore()
        }
        function bc() {
            m.save(), a.each(D(), function (b, c) {
                if (!c.show || c.ticks.length == 0) return;
                var d = c.box,
                    e = c.font;
                m.fillStyle = c.options.color, m.font = e.style + " " + e.variant + " " + e.weight + " " + e.size + "px " + e.family, m.textAlign = "start", m.textBaseline = "middle";
                for (var f = 0; f < c.ticks.length; ++f) {
                    var g = c.ticks[f];
                    if (!g.label || g.v < c.min || g.v > c.max) continue;
                    var h, i, j = 0,
                        k;
                    for (var l = 0; l < g.lines.length; ++l) k = g.lines[l], c.direction == "x" ? (h = q.left + c.p2c(g.v) - k.width / 2, c.position == "bottom" ? i = d.top + d.padding : i = d.top + d.height - d.padding - g.height) : (i = q.top + c.p2c(g.v) - g.height / 2, c.position == "left" ? h = d.left + d.width - d.padding - k.width : h = d.left + d.padding), i += k.height / 2 + j, j += k.height, a.browser.opera && (h = Math.floor(h), i = Math.ceil(i - 2)), m.fillText(k.text, h, i)
                }
            }), m.restore()
        }
        function bd(a) {
            a.lines.show && be(a), a.bars.show && bh(a), a.points.show && bf(a)
        }
        function be(a) {
            function b(a, b, c, d, e) {
                var f = a.points,
                    g = a.pointsize,
                    h = null,
                    i = null;
                m.beginPath();
                for (var j = g; j < f.length; j += g) {
                    var k = f[j - g],
                        l = f[j - g + 1],
                        n = f[j],
                        o = f[j + 1];
                    if (k == null || n == null) continue;
                    if (l <= o && l < e.min) {
                        if (o < e.min) continue;
                        k = (e.min - l) / (o - l) * (n - k) + k, l = e.min
                    } else if (o <= l && o < e.min) {
                        if (l < e.min) continue;
                        n = (e.min - l) / (o - l) * (n - k) + k, o = e.min
                    }
                    if (l >= o && l > e.max) {
                        if (o > e.max) continue;
                        k = (e.max - l) / (o - l) * (n - k) + k, l = e.max
                    } else if (o >= l && o > e.max) {
                        if (l > e.max) continue;
                        n = (e.max - l) / (o - l) * (n - k) + k, o = e.max
                    }
                    if (k <= n && k < d.min) {
                        if (n < d.min) continue;
                        l = (d.min - k) / (n - k) * (o - l) + l, k = d.min
                    } else if (n <= k && n < d.min) {
                        if (k < d.min) continue;
                        o = (d.min - k) / (n - k) * (o - l) + l, n = d.min
                    }
                    if (k >= n && k > d.max) {
                        if (n > d.max) continue;
                        l = (d.max - k) / (n - k) * (o - l) + l, k = d.max
                    } else if (n >= k && n > d.max) {
                        if (k > d.max) continue;
                        o = (d.max - k) / (n - k) * (o - l) + l, n = d.max
                    }(k != h || l != i) && m.moveTo(d.p2c(k) + b, e.p2c(l) + c), h = n, i = o, m.lineTo(d.p2c(n) + b, e.p2c(o) + c)
                }
                m.stroke()
            }
            function c(a, b, c) {
                var d = a.points,
                    e = a.pointsize,
                    f = Math.min(Math.max(0, c.min), c.max),
                    g = 0,
                    h, i = !1,
                    j = 1,
                    k = 0,
                    l = 0;
                for (;;) {
                    if (e > 0 && g > d.length + e) break;
                    g += e;
                    var n = d[g - e],
                        o = d[g - e + j],
                        p = d[g],
                        q = d[g + j];
                    if (i) {
                        if (e > 0 && n != null && p == null) {
                            l = g, e = -e, j = 2;
                            continue
                        }
                        if (e < 0 && g == k + e) {
                            m.fill(), i = !1, e = -e, j = 1, g = k = l + e;
                            continue
                        }
                    }
                    if (n == null || p == null) continue;
                    if (n <= p && n < b.min) {
                        if (p < b.min) continue;
                        o = (b.min - n) / (p - n) * (q - o) + o, n = b.min
                    } else if (p <= n && p < b.min) {
                        if (n < b.min) continue;
                        q = (b.min - n) / (p - n) * (q - o) + o, p = b.min
                    }
                    if (n >= p && n > b.max) {
                        if (p > b.max) continue;
                        o = (b.max - n) / (p - n) * (q - o) + o, n = b.max
                    } else if (p >= n && p > b.max) {
                        if (n > b.max) continue;
                        q = (b.max - n) / (p - n) * (q - o) + o, p = b.max
                    }
                    i || (m.beginPath(), m.moveTo(b.p2c(n), c.p2c(f)), i = !0);
                    if (o >= c.max && q >= c.max) {
                        m.lineTo(b.p2c(n), c.p2c(c.max)), m.lineTo(b.p2c(p), c.p2c(c.max));
                        continue
                    }
                    if (o <= c.min && q <= c.min) {
                        m.lineTo(b.p2c(n), c.p2c(c.min)), m.lineTo(b.p2c(p), c.p2c(c.min));
                        continue
                    }
                    var r = n,
                        s = p;
                    o > q || o >= c.min || q < c.min ? q <= o && q < c.min && o >= c.min && (p = (c.min - o) / (q - o) * (p - n) + n, q = c.min) : (n = (c.min - o) / (q - o) * (p - n) + n, o = c.min), o < q || o <= c.max || q > c.max ? q >= o && q > c.max && o <= c.max && (p = (c.max - o) / (q - o) * (p - n) + n, q = c.max) : (n = (c.max - o) / (q - o) * (p - n) + n, o = c.max), n != r && m.lineTo(b.p2c(r), c.p2c(o)), m.lineTo(b.p2c(n), c.p2c(o)), m.lineTo(b.p2c(p), c.p2c(q)), p != s && (m.lineTo(b.p2c(p), c.p2c(q)), m.lineTo(b.p2c(s), c.p2c(q)))
                }
            }
            m.save(), m.translate(q.left, q.top), m.lineJoin = "round";
            var d = a.lines.lineWidth,
                e = a.shadowSize;
            if (d > 0 && e > 0) {
                m.lineWidth = e, m.strokeStyle = "rgba(0,0,0,0.1)";
                var f = Math.PI / 18;
                b(a.datapoints, Math.sin(f) * (d / 2 + e / 2), Math.cos(f) * (d / 2 + e / 2), a.xaxis, a.yaxis), m.lineWidth = e / 2, b(a.datapoints, Math.sin(f) * (d / 2 + e / 4), Math.cos(f) * (d / 2 + e / 4), a.xaxis, a.yaxis)
            }
            m.lineWidth = d, m.strokeStyle = a.color;
            var g = bi(a.lines, a.color, 0, u);
            g && (m.fillStyle = g, c(a.datapoints, a.xaxis, a.yaxis)), d > 0 && b(a.datapoints, 0, 0, a.xaxis, a.yaxis), m.restore()
        }
        function bf(a) {
            function b(a, b, c, d, e, f, g, h) {
                var i = a.points,
                    j = a.pointsize;
                for (var k = 0; k < i.length; k += j) {
                    var l = i[k],
                        n = i[k + 1];
                    if (l == null || l < f.min || l > f.max || n < g.min || n > g.max) continue;
                    m.beginPath(), l = f.p2c(l), n = g.p2c(n) + d, h == "circle" ? m.arc(l, n, b, 0, e ? Math.PI : Math.PI * 2, !1) : h(m, l, n, b, e), m.closePath(), c && (m.fillStyle = c, m.fill()), m.stroke()
                }
            }
            m.save(), m.translate(q.left, q.top);
            var c = a.points.lineWidth,
                d = a.shadowSize,
                e = a.points.radius,
                f = a.points.symbol;
            if (c > 0 && d > 0) {
                var g = d / 2;
                m.lineWidth = g, m.strokeStyle = "rgba(0,0,0,0.1)", b(a.datapoints, e, null, g + g / 2, !0, a.xaxis, a.yaxis, f), m.strokeStyle = "rgba(0,0,0,0.2)", b(a.datapoints, e, null, g / 2, !0, a.xaxis, a.yaxis, f)
            }
            m.lineWidth = c, m.strokeStyle = a.color, b(a.datapoints, e, bi(a.points, a.color), 0, !1, a.xaxis, a.yaxis, f), m.restore()
        }
        function bg(a, b, c, d, e, f, g, h, i, j, k, l) {
            var m, n, o, p, q, r, s, t, u;
            k ? (t = r = s = !0, q = !1, m = c, n = a, p = b + d, o = b + e, n < m && (u = n, n = m, m = u, q = !0, r = !1)) : (q = r = s = !0, t = !1, m = a + d, n = a + e, o = c, p = b, p < o && (u = p, p = o, o = u, t = !0, s = !1));
            if (n < h.min || m > h.max || p < i.min || o > i.max) return;
            m < h.min && (m = h.min, q = !1), n > h.max && (n = h.max, r = !1), o < i.min && (o = i.min, t = !1), p > i.max && (p = i.max, s = !1), m = h.p2c(m), o = i.p2c(o), n = h.p2c(n), p = i.p2c(p), g && (j.beginPath(), j.moveTo(m, o), j.lineTo(m, p), j.lineTo(n, p), j.lineTo(n, o), j.fillStyle = g(o, p), j.fill()), l > 0 && (q || r || s || t) && (j.beginPath(), j.moveTo(m, o + f), q ? j.lineTo(m, p + f) : j.moveTo(m, p + f), s ? j.lineTo(n, p + f) : j.moveTo(n, p + f), r ? j.lineTo(n, o + f) : j.moveTo(n, o + f), t ? j.lineTo(m, o + f) : j.moveTo(m, o + f), j.stroke())
        }
        function bh(a) {
            function b(b, c, d, e, f, g, h) {
                var i = b.points,
                    j = b.pointsize;
                for (var k = 0; k < i.length; k += j) {
                    if (i[k] == null) continue;
                    bg(i[k], i[k + 1], i[k + 2], c, d, e, f, g, h, m, a.bars.horizontal, a.bars.lineWidth)
                }
            }
            m.save(), m.translate(q.left, q.top), m.lineWidth = a.bars.lineWidth, m.strokeStyle = a.color;
            var c = a.bars.align == "left" ? 0 : -a.bars.barWidth / 2,
                d = a.bars.fill ? function (b, c) {
                    return bi(a.bars, a.color, b, c)
                } : null;
            b(a.datapoints, c, c + a.bars.barWidth, 0, d, a.xaxis, a.yaxis), m.restore()
        }
        function bi(b, c, d, e) {
            var f = b.fill;
            if (!f) return null;
            if (b.fillColor) return by(b.fillColor, d, e, c);
            var g = a.color.parse(c);
            return g.a = typeof f == "number" ? f : .4, g.normalize(), g.toString()
        }
        function bj() {
            b.find(".legend").remove();
            if (!h.legend.show) return;
            var c = [],
                d = !1,
                e = h.legend.labelFormatter,
                f, i;
            for (var j = 0; j < g.length; ++j) {
                f = g[j], i = f.label;
                if (!i) continue;
                j % h.legend.noColumns == 0 && (d && c.push("</tr>"), c.push("<tr>"), d = !0), e && (i = e(i, f)), c.push('<td class="legendColorBox"><div style="border:1px solid ' + h.legend.labelBoxBorderColor + ';padding:1px"><div style="width:4px;height:0;border:5px solid ' + f.color + ';overflow:hidden"></div></div></td>' + '<td class="legendLabel">' + i + "</td>")
            }
            d && c.push("</tr>");
            if (c.length == 0) return;
            var k = '<table style="font-size:smaller;color:' + h.grid.color + '">' + c.join("") + "</table>";
            if (h.legend.container != null) a(h.legend.container).html(k);
            else {
                var l = "",
                    m = h.legend.position,
                    n = h.legend.margin;
                n[0] == null && (n = [n, n]), m.charAt(0) == "n" ? l += "top:" + (n[1] + q.top) + "px;" : m.charAt(0) == "s" && (l += "bottom:" + (n[1] + q.bottom) + "px;"), m.charAt(1) == "e" ? l += "right:" + (n[0] + q.right) + "px;" : m.charAt(1) == "w" && (l += "left:" + (n[0] + q.left) + "px;");
                var o = a('<div class="legend">' + k.replace('style="', 'style="position:absolute;' + l + ";") + "</div>").appendTo(b);
                if (h.legend.backgroundOpacity != 0) {
                    var p = h.legend.backgroundColor;
                    p == null && (p = h.grid.backgroundColor, p && typeof p == "string" ? p = a.color.parse(p) : p = a.color.extract(o, "background-color"), p.a = 1, p = p.toString());
                    var r = o.children();
                    a('<div style="position:absolute;width:' + r.width() + "px;height:" + r.height() + "px;" + l + "background-color:" + p + ';"> </div>').prependTo(o).css("opacity", h.legend.backgroundOpacity)
                }
            }
        }
        function bm(a, b, c) {
            var d = h.grid.mouseActiveRadius,
                e = d * d + 1,
                f = null,
                i = !1,
                j, k;
            for (j = g.length - 1; j >= 0; --j) {
                if (!c(g[j])) continue;
                var l = g[j],
                    m = l.xaxis,
                    n = l.yaxis,
                    o = l.datapoints.points,
                    p = l.datapoints.pointsize,
                    q = m.c2p(a),
                    r = n.c2p(b),
                    s = d / m.scale,
                    t = d / n.scale;
                m.options.inverseTransform && (s = Number.MAX_VALUE), n.options.inverseTransform && (t = Number.MAX_VALUE);
                if (l.lines.show || l.points.show) for (k = 0; k < o.length; k += p) {
                    var u = o[k],
                        v = o[k + 1];
                    if (u == null) continue;
                    if (u - q > s || u - q < -s || v - r > t || v - r < -t) continue;
                    var w = Math.abs(m.p2c(u) - a),
                        x = Math.abs(n.p2c(v) - b),
                        y = w * w + x * x;
                    y < e && (e = y, f = [j, k / p])
                }
                if (l.bars.show && !f) {
                    var z = l.bars.align == "left" ? 0 : -l.bars.barWidth / 2,
                        A = z + l.bars.barWidth;
                    for (k = 0; k < o.length; k += p) {
                        var u = o[k],
                            v = o[k + 1],
                            B = o[k + 2];
                        if (u == null) continue;
                        if (g[j].bars.horizontal ? q <= Math.max(B, u) && q >= Math.min(B, u) && r >= v + z && r <= v + A : q >= u + z && q <= u + A && r >= Math.min(B, v) && r <= Math.max(B, v)) f = [j, k / p]
                    }
                }
            }
            return f ? (j = f[0], k = f[1], p = g[j].datapoints.pointsize, {
                datapoint: g[j].datapoints.points.slice(k * p, (k + 1) * p),
                dataIndex: k,
                series: g[j],
                seriesIndex: j
            }) : null
        }
        function bn(a) {
            h.grid.hoverable && bq("plothover", a, function (a) {
                return a["hoverable"] != 0
            })
        }
        function bo(a) {
            h.grid.hoverable && bq("plothover", a, function (a) {
                return !1
            })
        }
        function bp(a) {
            bq("plotclick", a, function (a) {
                return a["clickable"] != 0
            })
        }
        function bq(a, c, d) {
            var e = l.offset(),
                f = c.pageX - e.left - q.left,
                g = c.pageY - e.top - q.top,
                i = E({
                    left: f,
                    top: g
                });
            i.pageX = c.pageX, i.pageY = c.pageY;
            var j = bm(f, g, d);
            j && (j.pageX = parseInt(j.series.xaxis.p2c(j.datapoint[0]) + e.left + q.left), j.pageY = parseInt(j.series.yaxis.p2c(j.datapoint[1]) + e.top + q.top));
            if (h.grid.autoHighlight) {
                for (var k = 0; k < bk.length; ++k) {
                    var m = bk[k];
                    m.auto == a && (!j || m.series != j.series || m.point[0] != j.datapoint[0] || m.point[1] != j.datapoint[1]) && bu(m.series, m.point)
                }
                j && bt(j.series, j.datapoint, a)
            }
            b.trigger(a, [i, j])
        }
        function br() {
            var a = h.interaction.redrawOverlayInterval;
            if (a == -1) {
                bs();
                return
            }
            bl || (bl = setTimeout(bs, a))
        }
        function bs() {
            bl = null, n.save(), n.clearRect(0, 0, r, s), n.translate(q.left, q.top);
            var a, b;
            for (a = 0; a < bk.length; ++a) b = bk[a], b.series.bars.show ? bx(b.series, b.point) : bw(b.series, b.point);
            n.restore(), x(v.drawOverlay, [n])
        }
        function bt(a, b, c) {
            typeof a == "number" && (a = g[a]);
            if (typeof b == "number") {
                var d = a.datapoints.pointsize;
                b = a.datapoints.points.slice(d * b, d * (b + 1))
            }
            var e = bv(a, b);
            e == -1 ? (bk.push({
                series: a,
                point: b,
                auto: c
            }), br()) : c || (bk[e].auto = !1)
        }
        function bu(a, b) {
            a == null && b == null && (bk = [], br()), typeof a == "number" && (a = g[a]), typeof b == "number" && (b = a.data[b]);
            var c = bv(a, b);
            c != -1 && (bk.splice(c, 1), br())
        }
        function bv(a, b) {
            for (var c = 0; c < bk.length; ++c) {
                var d = bk[c];
                if (d.series == a && d.point[0] == b[0] && d.point[1] == b[1]) return c
            }
            return -1
        }
        function bw(b, c) {
            var d = c[0],
                e = c[1],
                f = b.xaxis,
                g = b.yaxis;
            if (d < f.min || d > f.max || e < g.min || e > g.max) return;
            var h = b.points.radius + b.points.lineWidth / 2;
            n.lineWidth = h, n.strokeStyle = a.color.parse(b.color).scale("a", .5).toString();
            var i = 1.5 * h,
                d = f.p2c(d),
                e = g.p2c(e);
            n.beginPath(), b.points.symbol == "circle" ? n.arc(d, e, i, 0, 2 * Math.PI, !1) : b.points.symbol(n, d, e, i, !1), n.closePath(), n.stroke()
        }
        function bx(b, c) {
            n.lineWidth = b.bars.lineWidth, n.strokeStyle = a.color.parse(b.color).scale("a", .5).toString();
            var d = a.color.parse(b.color).scale("a", .5).toString(),
                e = b.bars.align == "left" ? 0 : -b.bars.barWidth / 2;
            bg(c[0], c[1], c[2] || 0, e, e + b.bars.barWidth, 0, function () {
                return d
            }, b.xaxis, b.yaxis, n, b.bars.horizontal, b.bars.lineWidth)
        }
        function by(b, c, d, e) {
            if (typeof b == "string") return b;
            var f = m.createLinearGradient(0, d, 0, c);
            for (var g = 0, h = b.colors.length; g < h; ++g) {
                var i = b.colors[g];
                if (typeof i != "string") {
                    var j = a.color.parse(e);
                    i.brightness != null && (j = j.scale("rgb", i.brightness)), i.opacity != null && (j.a *= i.opacity), i = j.toString()
                }
                f.addColorStop(g / (h - 1), i)
            }
            return f
        }
        var g = [],
            h = {
                colors: ["#edc240", "#afd8f8", "#cb4b4b", "#4da74d", "#9440ed"],
                legend: {
                    show: !0,
                    noColumns: 1,
                    labelFormatter: null,
                    labelBoxBorderColor: "#ccc",
                    container: null,
                    position: "ne",
                    margin: 5,
                    backgroundColor: null,
                    backgroundOpacity: .85
                },
                xaxis: {
                    show: null,
                    position: "bottom",
                    mode: null,
                    font: null,
                    color: null,
                    tickColor: null,
                    transform: null,
                    inverseTransform: null,
                    min: null,
                    max: null,
                    autoscaleMargin: null,
                    ticks: null,
                    tickFormatter: null,
                    labelWidth: null,
                    labelHeight: null,
                    reserveSpace: null,
                    tickLength: null,
                    alignTicksWithAxis: null,
                    tickDecimals: null,
                    tickSize: null,
                    minTickSize: null,
                    monthNames: null,
                    timeformat: null,
                    twelveHourClock: !1
                },
                yaxis: {
                    autoscaleMargin: .02,
                    position: "left"
                },
                xaxes: [],
                yaxes: [],
                series: {
                    points: {
                        show: !1,
                        radius: 3,
                        lineWidth: 2,
                        fill: !0,
                        fillColor: "#ffffff",
                        symbol: "circle"
                    },
                    lines: {
                        lineWidth: 2,
                        fill: !1,
                        fillColor: null,
                        steps: !1
                    },
                    bars: {
                        show: !1,
                        lineWidth: 2,
                        barWidth: 1,
                        fill: !0,
                        fillColor: null,
                        align: "left",
                        horizontal: !1
                    },
                    shadowSize: 3
                },
                grid: {
                    show: !0,
                    aboveData: !1,
                    color: "#545454",
                    backgroundColor: null,
                    borderColor: null,
                    tickColor: null,
                    labelMargin: 5,
                    axisMargin: 8,
                    borderWidth: 2,
                    minBorderMargin: null,
                    markings: null,
                    markingsColor: "#f4f4f4",
                    markingsLineWidth: 2,
                    clickable: !1,
                    hoverable: !1,
                    autoHighlight: !0,
                    mouseActiveRadius: 10
                },
                interaction: {
                    redrawOverlayInterval: 1e3 / 60
                },
                hooks: {}
            }, j = null,
            k = null,
            l = null,
            m = null,
            n = null,
            o = [],
            p = [],
            q = {
                left: 0,
                right: 0,
                top: 0,
                bottom: 0
            }, r = 0,
            s = 0,
            t = 0,
            u = 0,
            v = {
                processOptions: [],
                processRawData: [],
                processDatapoints: [],
                drawSeries: [],
                draw: [],
                bindEvents: [],
                drawOverlay: [],
                shutdown: []
            }, w = this;
        w.setData = A, w.setupGrid = U, w.draw = Z, w.getPlaceholder = function () {
            return b
        }, w.getCanvas = function () {
            return j
        }, w.getPlotOffset = function () {
            return q
        }, w.width = function () {
            return t
        }, w.height = function () {
            return u
        }, w.offset = function () {
            var a = l.offset();
            return a.left += q.left, a.top += q.top, a
        }, w.getData = function () {
            return g
        }, w.getAxes = function () {
            var b = {}, c;
            return a.each(o.concat(p), function (a, c) {
                c && (b[c.direction + (c.n != 1 ? c.n : "") + "axis"] = c)
            }), b
        }, w.getXAxes = function () {
            return o
        }, w.getYAxes = function () {
            return p
        }, w.c2p = E, w.p2c = F, w.getOptions = function () {
            return h
        }, w.highlight = bt, w.unhighlight = bu, w.triggerRedrawOverlay = br, w.pointOffset = function (a) {
            return {
                left: parseInt(o[C(a, "x") - 1].p2c(+a.x) + q.left),
                top: parseInt(p[C(a, "y") - 1].p2c(+a.y) + q.top)
            }
        }, w.shutdown = O, w.resize = function () {
            K(), L(j), L(k)
        }, w.hooks = v, y(w), z(e), M(), A(d), U(), Z(), N();
        var bk = [],
            bl = null
    }
    function c(a, b) {
        return b * Math.floor(a / b)
    }
    a.plot = function (c, d, e) {
        var f = new b(a(c), d, e, a.plot.plugins);
        return f
    }, a.plot.version = "0.7", a.plot.plugins = [], a.plot.formatDate = function (a, b, c) {
        var d = function (a) {
            return a = "" + a, a.length == 1 ? "0" + a : a
        }, e = [],
            f = !1,
            g = !1,
            h = a.getUTCHours(),
            i = h < 12;
        c == null && (c = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]), b.search(/%p|%P/) != -1 && (h > 12 ? h -= 12 : h == 0 && (h = 12));
        for (var j = 0; j < b.length; ++j) {
            var k = b.charAt(j);
            if (f) {
                switch (k) {
                    case "h":
                        k = "" + h;
                        break;
                    case "H":
                        k = d(h);
                        break;
                    case "M":
                        k = d(a.getUTCMinutes());
                        break;
                    case "S":
                        k = d(a.getUTCSeconds());
                        break;
                    case "d":
                        k = "" + a.getUTCDate();
                        break;
                    case "m":
                        k = "" + (a.getUTCMonth() + 1);
                        break;
                    case "y":
                        k = "" + a.getUTCFullYear();
                        break;
                    case "b":
                        k = "" + c[a.getUTCMonth()];
                        break;
                    case "p":
                        k = i ? "am" : "pm";
                        break;
                    case "P":
                        k = i ? "AM" : "PM";
                        break;
                    case "0":
                        k = "", g = !0
                }
                k && g && (k = d(k), g = !1), e.push(k), g || (f = !1)
            } else k == "%" ? f = !0 : e.push(k)
        }
        return e.join("")
    }
}(jQuery), define("lib/jquery.flot", function () {}),
function (define) {
    define("neo4j/webadmin/modules/dashboard/views/charts", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="headline-bar"><ul class="dashboard-zoom-tabs button-bar grouped"><li><button value="year" class="button switch-dashboard-zoom">Year</button></li><li><button value="month" class="button switch-dashboard-zoom">One month</button></li><li><button value="week" class="button switch-dashboard-zoom">One week</button></li><li><button value="day" class="button switch-dashboard-zoom">One day</button></li><li><button value="six_hours" class="button switch-dashboard-zoom">6 hours</button></li><li><button value="thirty_minutes" class="button switch-dashboard-zoom">30 minutes</button></li></ul><div class="break"></div></div><div id="monitor-chart-wrap"><div id="monitor-chart"></div></div><div class="footer-bar"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    define("lib/amd/jQuery", ["lib/jquery"], function () {
        return jQuery
    })
}.call(this),
function () {
    define("lib/amd/Flot", ["order!lib/amd/jQuery", "order!lib/jquery.flot"], function () {
        return jQuery
    })
}.call(this),
function () {
    define("lib/amd/Backbone", ["order!lib/amd/jQuery", "order!lib/backbone"], function () {
        return Backbone
    })
}.call(this),
function () {
    define("lib/amd/Underscore", ["lib/amd/Backbone"], function (a) {
        return _
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("ribcage/ui/Tooltip", ["lib/amd/jQuery", "lib/amd/Underscore"], function (b, c) {
        var d;
        return d = function () {
            function d(d) {
                d == null && (d = {}), this.getTooltipPositionFor = a(this.getTooltipPositionFor, this), this.getPosition = a(this.getPosition, this), this.onWindowResized = a(this.onWindowResized, this), this.remove = a(this.remove, this), this.hide = a(this.hide, this), this.show = a(this.show, this), this._tooltip = b("<div class='tooltip-wrap'></div>"), this._tooltipContent = b("<div class='tooltip'></div>"), this._closeButton = b("<div class='tooltip-close'></div>"), this._currentPos = [0, 0], this._currentContent = "", this._visible = !1, this.settings = c.extend(this.defaultSettings, d), this.settings.hideOnMouseOut && this._tooltip.bind("mouseout", this.hide), this._tooltip.addClass("tooltip-pos-" + this.settings.position), this._tooltip.css(this.settings.css), this._tooltip.append(this._tooltipContent), this.settings.closeButton && (this._tooltip.append(this._closeButton), this._closeButton.bind("click", this.hide)), this._tooltip.appendTo("body"), b(window).resize(this.onWindowResized)
            }
            return d.prototype.defaultSettings = {
                hideOnMouseOut: !0,
                css: {},
                position: "above",
                closeButton: !0
            }, d.prototype.show = function (a, b, c) {
                this._currentPos = b, this._currentContent = a, this._tooltipContent.html(a), b = this.getTooltipPositionFor(this.getPosition(b)), this._tooltip.css({
                    left: b[0],
                    top: b[1]
                }).show(), this._visible = !0;
                if (c) return setTimeout(this.hide, c)
            }, d.prototype.hide = function () {
                return this._tooltip.hide(), this._visible = !1
            }, d.prototype.remove = function () {
                return this._tooltip.unbind("mouseout", this.hide), this._closeButton.unbind("click", this.hide), this._tooltip.remove()
            }, d.prototype.onWindowResized = function () {
                var b;
                if (this._visible) return b = a(function () {
                    return this.show(this._currentContent, this._currentPos)
                }, this), setTimeout(b, 0)
            }, d.prototype.getPosition = function (a) {
                var d;
                if (c.isArray(a)) return a;
                d = b(a), a = d.offset();
                switch (this.settings.position) {
                    case "right":
                        return [a.left + d.width(), a.top + d.height() / 2];
                    case "left":
                        return [a.left, a.top + d.height() / 2];
                    case "above":
                        return [a.left + d.width() / 2, a.top];
                    default:
                        return [a.left + d.width() / 2, a.top - d.height()]
                }
            }, d.prototype.getTooltipPositionFor = function (a) {
                switch (this.settings.position) {
                    case "right":
                        return [a[0] + 10, a[1] - this._tooltip.height() / 2];
                    case "left":
                        return [a[0] - (this._tooltip.width() + 10), a[1] - this._tooltip.height() / 2];
                    case "above":
                        return [a[0] - this._tooltip.width() / 2, a[1] - (this._tooltip.height() + 10)];
                    default:
                        return [a[0] - this._tooltip.width() / 2, a[1] + this._tooltip.height()]
                }
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("ribcage/ui/LineChart", ["lib/DateFormat", "ribcage/ui/Tooltip", "ribcage/security/HtmlEscaper", "lib/amd/Flot", "lib/amd/Underscore"], function (b, c, d, e, f) {
        var g;
        return g = function () {
            function g(b) {
                this.remove = a(this.remove, this), this.render = a(this.render, this), this.mouseOverPlot = a(this.mouseOverPlot, this), this.el = $(b), this.settings = this.defaultSettings, this.htmlEscaper = new d, this.tooltip = new c({
                    closeButton: !1
                }), this.el.bind("plothover", this.mouseOverPlot)
            }
            var e;
            return e = (new Date).getTimezoneOffset() * 60, g.prototype.defaultSettings = {
                label: "",
                xaxis: {
                    mode: "time",
                    timeformat: "%H:%M:%S",
                    min: 0
                },
                yaxis: {},
                legend: {
                    position: "nw"
                },
                series: {
                    points: {
                        show: !0
                    },
                    lines: {
                        show: !0
                    }
                },
                grid: {
                    hoverable: !0
                },
                colors: ["#490A3D", "#BD1550", "#E97F02", "#F8CA00", "#8A9B0F"],
                tooltipYFormatter: function (a) {
                    return Math.round(a)
                },
                tooltipXFormatter: function (a) {
                    return b.format(new Date((a + e) * 1e3))
                }
            }, g.prototype.mouseOverPlot = function (a, b, c) {
                var d, e;
                if (!c) return this.tooltip.hide();
                if (this.previousHoverPoint !== c.datapoint) return this.previousHoverPoint = c.datapoint, d = this.settings.tooltipXFormatter(c.datapoint[0]), e = this.settings.tooltipYFormatter(c.datapoint[1]), this.tooltip.show("<b>" + this.htmlEscaper.escape(c.series.label) + "</b><span class='chart-y'>" + this.htmlEscaper.escape(e) + "</span><span class='chart-x'>" + this.htmlEscaper.escape(d) + "</span>", [c.pageX, c.pageY])
            }, g.prototype.render = function (a, b) {
                return this.settings = f.extend({}, this.defaultSettings, b), $.plot(this.el, a, this.settings)
            }, g.prototype.remove = function () {
                return this.el.unbind("plothover", this.mouseOverPlot), this.tooltip.remove(), this.el.remove()
            }, g
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("ribcage/View", ["lib/amd/Backbone"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.attach = a(this.attach, this), this.detach = a(this.detach, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b.View), d.prototype.detach = function () {
                return $(this.el).detach()
            }, d.prototype.attach = function (a) {
                return $(a).append(this.el)
            }, d.prototype.height = function (a) {
                return $(this.el).height(a)
            }, d.prototype.width = function (a) {
                return $(this.el).width(a)
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/views/DashboardInfoView", ["./info", "ribcage/View", "ribcage/ui/NumberFormatter", "lib/amd/jQuery"], function (b, d, e, f) {
        var g;
        return g = function () {
            function g() {
                this.remove = a(this.remove, this), this.render = a(this.render, this), this.initialize = a(this.initialize, this), g.__super__.constructor.apply(this, arguments)
            }
            return c(g, d), g.prototype.template = b, g.prototype.initialize = function (a) {
                return this.primitives = a.primitives, this.diskUsage = a.diskUsage, this.primitives.bind("change", this.render), this.diskUsage.bind("change", this.render)
            }, g.prototype.render = function () {
                return f(this.el).html(this.template({
                    primitives: this.primitives,
                    diskUsage: this.diskUsage,
                    fancyNumber: e.fancy
                })), this
            }, g.prototype.remove = function () {
                return this.primitives.unbind("change", this.render), this.diskUsage.unbind("change", this.render), g.__super__.remove.call(this)
            }, g
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/views/DashboardChartsView", ["ribcage/ui/LineChart", "ribcage/View", "./charts", "lib/amd/jQuery"], function (b, d, e, f) {
        var g;
        return g = function () {
            function g() {
                this.unbind = a(this.unbind, this), this.bind = a(this.bind, this), this.remove = a(this.remove, this), this.highlightZoomTab = a(this.highlightZoomTab, this), this.highlightChartSwitchTab = a(this.highlightChartSwitchTab, this), this.switchZoomClicked = a(this.switchZoomClicked, this), this.switchChartClicked = a(this.switchChartClicked, this), this.redrawChart = a(this.redrawChart, this), this.redrawAllCharts = a(this.redrawAllCharts, this), this.render = a(this.render, this), this.initialize = a(this.initialize, this), g.__super__.constructor.apply(this, arguments)
            }
            return c(g, d), g.prototype.template = e, g.prototype.events = {
                "click .switch-dashboard-chart": "switchChartClicked",
                "click .switch-dashboard-zoom": "switchZoomClicked"
            }, g.prototype.initialize = function (a) {
                return this.statistics = a.statistics, this.dashboardState = a.dashboardState, this.bind()
            }, g.prototype.render = function () {
                return f(this.el).html(this.template()), this.monitorChart = new b(f("#monitor-chart")), this.redrawAllCharts(), this.highlightChartSwitchTab(this.dashboardState.getChartKey()), this.highlightZoomTab(this.dashboardState.getZoomLevelKey()), this
            }, g.prototype.redrawAllCharts = function () {
                return this.redrawChart(this.monitorChart, "primitives")
            }, g.prototype.redrawChart = function (a, b) {
                var c, d, e, g, h, i, j, k, l;
                if (a != null) return c = this.dashboardState.getChart(b), l = this.dashboardState.getZoomLevel(), g = function () {
                    var a, b, d, e;
                    d = c.layers, e = [];
                    for (a = 0, b = d.length; a < b; a++) k = d[a], e.push(k.key);
                    return e
                }(), j = Math.round((new Date).getTime() / 1e3 - l.xSpan), h = this.statistics.getMetrics(g, j, l.granularity), d = function () {
                    var a, b;
                    b = [];
                    for (e = 0, a = h.length; 0 > a ? e > a : e < a; 0 > a ? e-- : e++) b.push(_.extend({
                        data: h[e]
                    }, c.layers[e]));
                    return b
                }(), i = {
                    xaxis: {
                        min: j - this.statistics.timezoneOffset,
                        mode: "time",
                        timeformat: l.timeformat,
                        tickFormatter: function (a) {
                            return f.plot.formatDate(new Date(a * 1e3), l.timeformat)
                        }
                    }
                }, a.render(d, _.extend(c.chartSettings || {}, i))
            }, g.prototype.switchChartClicked = function (a) {
                return this.highlightChartSwitchTab(f(a.target).val()), this.dashboardState.setChartByKey(f(a.target).val())
            }, g.prototype.switchZoomClicked = function (a) {
                return this.highlightZoomTab(f(a.target).val()), this.dashboardState.setZoomLevelByKey(f(a.target).val())
            }, g.prototype.highlightChartSwitchTab = function (a) {
                return f("button.switch-dashboard-chart", this.el).removeClass("current"), f("button.switch-dashboard-chart[value='" + a + "']", this.el).addClass("current")
            }, g.prototype.highlightZoomTab = function (a) {
                return f("button.switch-dashboard-zoom", this.el).removeClass("current"), f("button.switch-dashboard-zoom[value='" + a + "']", this.el).addClass("current")
            }, g.prototype.remove = function () {
                return this.unbind(), this.monitorChart != null && this.monitorChart.remove(), g.__super__.remove.call(this)
            }, g.prototype.bind = function () {
                return this.dashboardState.bind("change:chart", this.redrawAllCharts), this.dashboardState.bind("change:zoomLevel", this.redrawAllCharts), this.statistics.bind("change:metrics", this.redrawAllCharts)
            }, g.prototype.unbind = function () {
                return this.dashboardState.unbind("change:chart", this.redrawAllCharts), this.dashboardState.unbind("change:zoomLevel", this.redrawAllCharts), this.statistics.unbind("change:metrics", this.redrawAllCharts)
            }, g
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/views/DashboardView", ["./base", "./DashboardInfoView", "./DashboardChartsView", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function h() {
                this.detach = a(this.detach, this), this.remove = a(this.remove, this), this.render = a(this.render, this), this.initialize = a(this.initialize, this), h.__super__.constructor.apply(this, arguments)
            }
            return c(h, f), h.prototype.template = b, h.prototype.initialize = function (a) {
                return this.opts = a, this.appState = a.state, this.server = this.appState.getServer(), this.kernelBean = a.kernelBean, this.kernelBean.bind("change", this.render)
            }, h.prototype.render = function () {
                var a;
                return a = this.kernelBean.get("kernel") != null ? this.kernelBean.get("kernel").KernelVersion : "N/A", g(this.el).html(this.template({
                    server: {
                        url: this.server.url,
                        version: a
                    }
                })), this.infoView != null && this.infoView.remove(), this.chartsView != null && this.chartsView.remove(), this.infoView = new d(this.opts), this.chartsView = new e(this.opts), this.infoView.attach(g("#dashboard-info", this.el)), this.chartsView.attach(g("#dashboard-charts", this.el)), this.infoView.render(), this.chartsView.render(), this
            }, h.prototype.remove = function () {
                return this.kernelBean.unbind("change", this.render), this.infoView.remove(), this.chartsView.remove(), h.__super__.remove.call(this)
            }, h.prototype.detach = function () {
                return this.chartsView.unbind(), h.__super__.detach.call(this)
            }, h
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("ribcage/Model", ["lib/amd/Backbone"], function (a) {
        var c;
        return c = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a.Model), c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/models/JmxBackedModel", ["ribcage/Model"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.fetch = a(this.fetch, this), this.setPollingInterval = a(this.setPollingInterval, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.initialize = function (a) {
                this.server = a.server, this.jmx = this.server.manage.jmx, this.dataAvailable = !1;
                if (a.pollingInterval != null && a.pollingInterval > 0) return this.fetch(), this.setPollingInterval(a.pollingInterval)
            }, d.prototype.isDataAvailable = function () {
                return this.dataAvailable
            }, d.prototype.setPollingInterval = function (a) {
                return this.interval != null && clearInterval(this.interval), this.interval = setInterval(this.fetch, a)
            }, d.prototype.fetch = function () {
                var a, b, c, d;
                c = this.beans, d = [];
                for (b in c) a = c[b], d.push(this.jmx.getBean(a.domain, a.name, this.beanParser(b)));
                return d
            }, d.prototype.beanParser = function (b) {
                return a(function (a) {
                    var c, d, e, f, g, h;
                    if (a != null && a.attributes != null) {
                        this.setBeanDataAvailable(b), e = {}, h = a.attributes;
                        for (f = 0, g = h.length; f < g; f++) c = h[f], e[c.name] = c.value;
                        return d = {}, d[b] = e, this.set(d)
                    }
                }, this)
            }, d.prototype.setBeanDataAvailable = function (a) {
                var b, c, d, e;
                this.beans[a].dataAvailable = !0, c = !0, e = this.beans;
                for (d in e) {
                    b = e[d];
                    if (!b.dataAvailable) {
                        c = !1;
                        break
                    }
                }
                return this.dataAvailable = c
            }, d
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/dashboard/models/ServerPrimitives", ["./JmxBackedModel"], function (a) {
        var c;
        return c = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a), c.prototype.beans = {
                primitives: {
                    domain: "neo4j",
                    name: "Primitive count"
                }
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/models/DiskUsage", ["./JmxBackedModel"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.getLogicalLogPercentage = a(this.getLogicalLogPercentage, this), this.getLogicalLogSize = a(this.getLogicalLogSize, this), this.getDatabasePercentage = a(this.getDatabasePercentage, this), this.getDatabaseSize = a(this.getDatabaseSize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.beans = {
                diskUsage: {
                    domain: "neo4j",
                    name: "Store file sizes"
                }
            }, d.prototype.getDatabaseSize = function () {
                return this.get("diskUsage").TotalStoreSize - this.get("diskUsage").LogicalLogSize
            }, d.prototype.getDatabasePercentage = function () {
                return Math.round((this.get("diskUsage").TotalStoreSize - this.get("diskUsage").LogicalLogSize) / this.get("diskUsage").TotalStoreSize * 100)
            }, d.prototype.getLogicalLogSize = function () {
                return this.get("diskUsage").LogicalLogSize
            }, d.prototype.getLogicalLogPercentage = function () {
                return Math.round(this.get("diskUsage").LogicalLogSize / this.get("diskUsage").TotalStoreSize * 100)
            }, d
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/dashboard/models/CacheUsage", ["./JmxBackedModel"], function (a) {
        var c;
        return c = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a), c.prototype.beans = {
                node: {
                    domain: "neo4j",
                    name: "Cache,name0=NodeCache"
                },
                relationship: {
                    domain: "neo4j",
                    name: "Cache,name0=RelationshipCache"
                }
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/models/ServerStatistics", ["lib/DateFormat", "ribcage/Model"], function (b, d) {
        var e;
        return e = function () {
            function b() {
                this.toLocalTimestamps = a(this.toLocalTimestamps, this), this.addTimestampsToArray = a(this.addTimestampsToArray, this), this.getMetrics = a(this.getMetrics, this), this.setMonitorData = a(this.setMonitorData, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, d), b.prototype.initialize = function (b) {
                return this.timezoneOffset = (new Date).getTimezoneOffset() * 60, this.server = b.server, this.heartbeat = this.server.heartbeat, this.setMonitorData(this.heartbeat.getCachedData()), this.heartbeat.addListener(a(function (a) {
                    return this.setMonitorData(a.allData)
                }, this))
            }, b.prototype.setMonitorData = function (a) {
                var b, c, d, e;
                this.timestamps = this.toLocalTimestamps(a.timestamps), this.indexesToSave = this.getTimestampIndexes(0, 30), this.timestamps = this.pickFromArray(this.timestamps, this.indexesToSave), d = {}, e = a.data;
                for (c in e) b = e[c], d["metric:" + c] = this.addTimestampsToArray(this.pickFromArray(b, this.indexesToSave), this.timestamps);
                return this.set(d), this.trigger("change:metrics")
            }, b.prototype.getMetrics = function (a, b, c) {
                var d, e, f, g, h, i, j;
                b == null && (b = 0), c == null && (c = 1e4), f = this.getClosestPreceedingTimestampIndex(b), f === -1 && (f = 0), d = this.getTimestampIndexes(f, c), j = [];
                for (h = 0, i = a.length; h < i; h++) e = a[h], g = this.get("metric:" + e), j.push(g && f < g.length ? this.pickFromArray(g, d) : []);
                return j
            }, b.prototype.getClosestPreceedingTimestampIndex = function (a) {
                var b, c, d;
                c = a - this.timezoneOffset;
                for (b = 0, d = this.timestamps.length; 0 > d ? b >= d : b <= d; 0 > d ? b-- : b++) if (this.timestamps[b] >= c) return b > 0 ? b - 1 : b;
                return 0
            }, b.prototype.pickFromArray = function (a, b) {
                var c, d, e, f;
                f = [];
                for (d = 0, e = b.length; d < e; d++) c = b[d], f.push(a[c]);
                return f
            }, b.prototype.getTimestampIndexes = function (a, b) {
                var c, d, e, f, g;
                e = [], f = 0;
                for (d = a, g = this.timestamps.length; a > g ? d >= g : d <= g; a > g ? d-- : d++) c = this.timestamps[d] - f, c > b && (f = this.timestamps[d], e.push(d));
                return e
            }, b.prototype.addTimestampsToArray = function (a, b) {
                var c, d, e;
                e = [];
                for (c = 0, d = a.length - 1; 0 > d ? c >= d : c <= d; 0 > d ? c-- : c++) e.push([b[c], a[c]]);
                return e
            }, b.prototype.toLocalTimestamps = function (a) {
                var b, c, d, e;
                e = [];
                for (c = 0, d = a.length; c < d; c++) b = a[c], e.push(b - this.timezoneOffset);
                return e
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/models/DashboardState", ["ribcage/Model"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.setChart = a(this.setChart, this), this.setChartByKey = a(this.setChartByKey, this), this.setZoomLevel = a(this.setZoomLevel, this), this.setZoomLevelByKey = a(this.setZoomLevelByKey, this), this.getZoomLevelKey = a(this.getZoomLevelKey, this), this.getZoomLevel = a(this.getZoomLevel, this), this.getChartKey = a(this.getChartKey, this), this.getChart = a(this.getChart, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.charts = {
                primitives: {
                    layers: [{
                        label: "Nodes",
                        key: "node_count"
                    }, {
                        label: "Properties",
                        key: "property_count"
                    }, {
                        label: "Relationships",
                        key: "relationship_count"
                    }],
                    chartSettings: {
                        yaxis: {
                            min: 0
                        }
                    }
                },
                usageRequests: {
                    layers: [{
                        label: "Count",
                        key: "request_count"
                    }],
                    chartSettings: {
                        yaxis: {
                            min: 0
                        }
                    }
                },
                usageTimes: {
                    layers: [{
                        label: "Time Mean",
                        key: "request_mean_time"
                    }, {
                        label: "Time Median",
                        key: "request_median_time"
                    }, {
                        label: "Time Max",
                        key: "request_max_time"
                    }, {
                        label: "Time Min",
                        key: "request_min_time"
                    }],
                    chartSettings: {
                        yaxis: {
                            min: 0
                        }
                    }
                },
                usageBytes: {
                    layers: [{
                        label: "Bytes",
                        key: "request_bytes"
                    }],
                    chartSettings: {
                        yaxis: {
                            min: 0
                        }
                    }
                },
                memory: {
                    layers: [{
                        label: "Memory usage",
                        key: "memory_usage_percent",
                        lines: {
                            show: !0,
                            fill: !0,
                            fillColor: "#4f848f"
                        }
                    }],
                    chartSettings: {
                        yaxis: {
                            min: 0,
                            max: 100
                        },
                        tooltipYFormatter: function (a) {
                            return Math.floor(a) + "%"
                        }
                    }
                }
            }, d.prototype.zoomLevels = {
                year: {
                    xSpan: 31536e3,
                    granularity: 1036800,
                    timeformat: "%d/%m %y"
                },
                month: {
                    xSpan: 2592e3,
                    granularity: 86400,
                    timeformat: "%d/%m"
                },
                week: {
                    xSpan: 604800,
                    granularity: 21600,
                    timeformat: "%d/%m"
                },
                day: {
                    xSpan: 86400,
                    granularity: 2880,
                    timeformat: "%H:%M"
                },
                six_hours: {
                    xSpan: 21600,
                    granularity: 720,
                    timeformat: "%H:%M"
                },
                thirty_minutes: {
                    xSpan: 1800,
                    granularity: 60,
                    timeformat: "%H:%M"
                }
            }, d.prototype.initialize = function (a) {
                return this.setChartByKey("primitives"), this.setZoomLevelByKey("six_hours")
            }, d.prototype.getChart = function (a) {
                return this.get("chart" + a)
            }, d.prototype.getChartKey = function () {
                return this.get("chartKey")
            }, d.prototype.getZoomLevel = function () {
                return this.get("zoomLevel")
            }, d.prototype.getZoomLevelKey = function () {
                return this.get("zoomLevelKey")
            }, d.prototype.setZoomLevelByKey = function (a) {
                return this.set({
                    zoomLevelKey: a
                }), this.setZoomLevel(this.zoomLevels[a])
            }, d.prototype.setZoomLevel = function (a) {
                return this.set({
                    zoomLevel: a
                })
            }, d.prototype.setChartByKey = function (a) {
                return this.set({
                    chartKey: a
                }), this.setChart(a, this.charts[a])
            }, d.prototype.setChart = function (a, b) {
                var c;
                return c = {}, c["chart" + a] = b, this.set(c)
            }, d
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/dashboard/models/KernelBean", ["./JmxBackedModel"], function (a) {
        var c;
        return c = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a), c.prototype.beans = {
                kernel: {
                    domain: "neo4j",
                    name: "Kernel"
                }
            }, c
        }()
    })
}.call(this),
function (a) {
    function b(b) {
        if (typeof b.data != "string") return;
        var c = b.handler,
            d = b.data.toLowerCase().split(" ");
        b.handler = function (b) {
            if (!(this === b.target || !/textarea|select/i.test(b.target.nodeName) && b.target.type !== "text")) return;
            var e = b.type !== "keypress" && a.hotkeys.specialKeys[b.which],
                f = String.fromCharCode(b.which).toLowerCase(),
                g, h = "",
                i = {};
            b.altKey && e !== "alt" && (h += "alt+"), b.ctrlKey && e !== "ctrl" && (h += "ctrl+"), b.metaKey && !b.ctrlKey && e !== "meta" && (h += "meta+"), b.shiftKey && e !== "shift" && (h += "shift+"), e ? i[h + e] = !0 : (i[h + f] = !0, i[h + a.hotkeys.shiftNums[f]] = !0, h === "shift+" && (i[a.hotkeys.shiftNums[f]] = !0));
            for (var j = 0, k = d.length; j < k; j++) if (i[d[j]]) return c.apply(this, arguments)
        }
    }
    a.hotkeys = {
        version: "0.8",
        specialKeys: {
            8: "backspace",
            9: "tab",
            13: "return",
            16: "shift",
            17: "ctrl",
            18: "alt",
            19: "pause",
            20: "capslock",
            27: "esc",
            32: "space",
            33: "pageup",
            34: "pagedown",
            35: "end",
            36: "home",
            37: "left",
            38: "up",
            39: "right",
            40: "down",
            45: "insert",
            46: "del",
            96: "0",
            97: "1",
            98: "2",
            99: "3",
            100: "4",
            101: "5",
            102: "6",
            103: "7",
            104: "8",
            105: "9",
            106: "*",
            107: "+",
            109: "-",
            110: ".",
            111: "/",
            112: "f1",
            113: "f2",
            114: "f3",
            115: "f4",
            116: "f5",
            117: "f6",
            118: "f7",
            119: "f8",
            120: "f9",
            121: "f10",
            122: "f11",
            123: "f12",
            144: "numlock",
            145: "scroll",
            191: "/",
            224: "meta"
        },
        shiftNums: {
            "`": "~",
            1: "!",
            2: "@",
            3: "#",
            4: "$",
            5: "%",
            6: "^",
            7: "&",
            8: "*",
            9: "(",
            0: ")",
            "-": "_",
            "=": "+",
            ";": ": ",
            "'": '"',
            ",": "<",
            ".": ">",
            "/": "?",
            "\\": "|"
        }
    }, a.each(["keydown", "keyup", "keypress"], function () {
        a.event.special[this] = {
            add: b
        }
    })
}(jQuery), define("lib/jquery.hotkeys", function () {}),
function () {
    define("lib/amd/HotKeys", ["order!lib/amd/jQuery", "order!lib/jquery.hotkeys"], function () {
        return jQuery
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("ribcage/Router", ["lib/amd/Backbone", "lib/amd/HotKeys"], function (a) {
        var c;
        return c = function () {
            function c() {
                var a, b, d;
                c.__super__.constructor.call(this), d = this.shortcuts;
                for (a in d) b = d[a], $(document).bind("keyup", a, this[b])
            }
            return b(c, a.Router), c.prototype.routes = {}, c.prototype.shortcuts = {}, c.prototype.saveLocation = function () {
                return this.navigate(location.hash, !1)
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/dashboard/DashboardRouter", ["./views/DashboardView", "./models/ServerPrimitives", "./models/DiskUsage", "./models/CacheUsage", "./models/ServerStatistics", "./models/DashboardState", "./models/KernelBean", "ribcage/Router"], function (b, d, e, f, g, h, i, j) {
        var k;
        return k = function () {
            function f() {
                this.getDashboardState = a(this.getDashboardState, this), this.getServerStatistics = a(this.getServerStatistics, this), this.getDiskUsage = a(this.getDiskUsage, this), this.getKernelBean = a(this.getKernelBean, this), this.getServerPrimitives = a(this.getServerPrimitives, this), this.getDashboardView = a(this.getDashboardView, this), this.dashboard = a(this.dashboard, this), this.init = a(this.init, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, j), f.prototype.routes = {
                "": "dashboard"
            }, f.prototype.init = function (a) {
                return this.appState = a
            }, f.prototype.dashboard = function () {
                return this.saveLocation(), this.appState.set({
                    mainView: this.getDashboardView()
                })
            }, f.prototype.getDashboardView = function () {
                var a;
                return (a = this.view) != null ? a : this.view = new b({
                    state: this.appState,
                    dashboardState: this.getDashboardState(),
                    primitives: this.getServerPrimitives(),
                    diskUsage: this.getDiskUsage(),
                    statistics: this.getServerStatistics(),
                    kernelBean: this.getKernelBean()
                })
            }, f.prototype.getServerPrimitives = function () {
                var a;
                return (a = this.serverPrimitives) != null ? a : this.serverPrimitives = new d({
                    server: this.appState.getServer(),
                    pollingInterval: 5e3
                })
            }, f.prototype.getKernelBean = function () {
                var a;
                return (a = this.kernelBean) != null ? a : this.kernelBean = new i({
                    server: this.appState.getServer(),
                    pollingInterval: 1e4
                })
            }, f.prototype.getDiskUsage = function () {
                var a;
                return (a = this.diskUsage) != null ? a : this.diskUsage = new e({
                    server: this.appState.getServer(),
                    pollingInterval: 5e3
                })
            }, f.prototype.getServerStatistics = function () {
                var a;
                return (a = this.serverStatistics) != null ? a : this.serverStatistics = new g({
                    server: this.appState.getServer()
                })
            }, f.prototype.getDashboardState = function () {
                var a;
                return (a = this.dashboardState) != null ? a : this.dashboardState = new h({
                    server: this.appState.getServer()
                })
            }, f
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/search/Queue", ["ribcage/Model"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.hasMoreItems = a(this.hasMoreItems, this), this.push = a(this.push, this), this.pull = a(this.pull, this), this.queue = []
            }
            return c(d, b), d.prototype.pull = function () {
                var a;
                return a = this.queue.shift(), this.trigger("item:pulled", this, a), a
            }, d.prototype.push = function (a) {
                return this.queue.push(a), this.trigger("item:pushed", this, a)
            }, d.prototype.hasMoreItems = function () {
                return this.queue.length > 0
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/UrlSearcher", [], function () {
        var b;
        return b = function () {
            function b(b) {
                this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = b, this.pattern = /^https?:\/\/(.+)$/i
            }
            return b.prototype.match = function (a) {
                return this.pattern.test(a)
            }, b.prototype.exec = function (a) {
                return this.server.getNodeOrRelationship(a)
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/utils/ItemUrlResolver", [], function () {
        var b;
        return b = function () {
            function b(b) {
                this.extractLastUrlSegment = a(this.extractLastUrlSegment, this), this.extractRelationshipId = a(this.extractRelationshipId, this), this.extractNodeId = a(this.extractNodeId, this), this.getNodeIndexHitsUrl = a(this.getNodeIndexHitsUrl, this), this.getRelationshipUrl = a(this.getRelationshipUrl, this), this.getNodeUrl = a(this.getNodeUrl, this), this.server = b
            }
            return b.prototype.getNodeUrl = function (a) {
                return this.server.url + "/db/data/node/" + a
            }, b.prototype.getRelationshipUrl = function (a) {
                return this.server.url + "/db/data/relationship/" + a
            }, b.prototype.getNodeIndexHitsUrl = function (a, b, c) {
                return this.server.url + "/db/data/index" + a + "/" + b + "/" + c
            }, b.prototype.extractNodeId = function (a) {
                return this.extractLastUrlSegment(a)
            }, b.prototype.extractRelationshipId = function (a) {
                return this.extractLastUrlSegment(a)
            }, b.prototype.extractLastUrlSegment = function (a) {
                return a.substr(-1) === "/" && (a = a.substr(0, a.length - 1)), a.substr(a.lastIndexOf("/") + 1)
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/NodeSearcher", ["neo4j/webadmin/utils/ItemUrlResolver"], function (b) {
        var c;
        return c = function () {
            function c(c) {
                this.extractNodeId = a(this.extractNodeId, this), this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = c, this.urlResolver = new b(c), this.pattern = /^(node:)?([0-9]+)$/i
            }
            return c.prototype.match = function (a) {
                return this.pattern.test(a)
            }, c.prototype.exec = function (a) {
                return this.server.node(this.urlResolver.getNodeUrl(this.extractNodeId(a)))
            }, c.prototype.extractNodeId = function (a) {
                var b;
                return b = this.pattern.exec(a), b[2]
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/NodeIndexSearcher", ["neo4j/webadmin/utils/ItemUrlResolver"], function (b) {
        var c;
        return c = function () {
            function c(c) {
                this.extractData = a(this.extractData, this), this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = c, this.urlResolver = new b(c), this.pattern = /^node:index:"?(\w+)"?:(.+)$/i
            }
            return c.prototype.match = function (a) {
                return this.pattern.test(a)
            }, c.prototype.exec = function (a) {
                var b;
                return b = this.extractData(a), this.server.index.getNodeIndex(b.index).query(b.query)
            }, c.prototype.extractData = function (a) {
                var b, c, d;
                return c = this.pattern.exec(a), b = c[1], d = c[2], {
                    index: b,
                    query: d
                }
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/RelationshipSearcher", ["neo4j/webadmin/utils/ItemUrlResolver"], function (b) {
        var c;
        return c = function () {
            function c(c) {
                this.extractRelId = a(this.extractRelId, this), this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = c, this.urlResolver = new b(c), this.pattern = /^((rel)|(relationship)):([0-9]+)$/i
            }
            return c.prototype.match = function (a) {
                return this.pattern.test(a)
            }, c.prototype.exec = function (a) {
                return this.server.rel(this.urlResolver.getRelationshipUrl(this.extractRelId(a)))
            }, c.prototype.extractRelId = function (a) {
                var b;
                return b = this.pattern.exec(a), b[4]
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/RelationshipsForNodeSearcher", ["neo4j/webadmin/utils/ItemUrlResolver"], function (b) {
        var c;
        return c = function () {
            function c(c) {
                this.extractNodeId = a(this.extractNodeId, this), this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = c, this.urlResolver = new b(c), this.pattern = /^((rels)|(relationships)):([0-9]+)$/i
            }
            return c.prototype.match = function (a) {
                return this.pattern.test(a)
            }, c.prototype.exec = function (a) {
                return this.server.node(this.urlResolver.getNodeUrl(this.extractNodeId(a))).then(function (a, b) {
                    return a.getRelationships().then(function (a) {
                        return b(a)
                    })
                })
            }, c.prototype.extractNodeId = function (a) {
                var b;
                return b = this.pattern.exec(a), b[4]
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/RelationshipIndexSearcher", ["neo4j/webadmin/utils/ItemUrlResolver"], function (b) {
        var c;
        return c = function () {
            function c(c) {
                this.extractData = a(this.extractData, this), this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = c, this.urlResolver = new b(c), this.pattern = /^((rel)|(relationship)):index:"?(\w+)"?:(.+)$/i
            }
            return c.prototype.match = function (a) {
                return this.pattern.test(a)
            }, c.prototype.exec = function (a) {
                var b;
                return b = this.extractData(a), this.server.index.getRelationshipIndex(b.index).query(b.query)
            }, c.prototype.extractData = function (a) {
                var b, c, d;
                return c = this.pattern.exec(a), b = c[4], d = c[5], {
                    index: b,
                    query: d
                }
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/CypherSearcher", ["neo4j/webadmin/utils/ItemUrlResolver"], function (b) {
        var c;
        return c = function () {
            function c(c) {
                this.exec = a(this.exec, this), this.match = a(this.match, this), this.server = c, this.urlResolver = new b(c), this.pattern = /^(start|cypher)(.+)return(.+)$/i
            }
            return c.prototype.match = function (a) {
                return this.pattern.test(a)
            }, c.prototype.exec = function (a) {
                return this.server.query(a)
            }, c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/search/Search", ["./UrlSearcher", "./NodeSearcher", "./NodeIndexSearcher", "./RelationshipSearcher", "./RelationshipsForNodeSearcher", "./RelationshipIndexSearcher", "./CypherSearcher"], function (b, c, d, e, f, g, h) {
        var i;
        return i = function () {
            function i(i) {
                this.server = i, this.pickSearcher = a(this.pickSearcher, this), this.exec = a(this.exec, this), this.searchers = [new b(i), new c(i), new d(i), new e(i), new f(i), new g(i), new h(i)]
            }
            return i.prototype.exec = function (a) {
                var b;
                return b = this.pickSearcher(a), b != null ? b.exec(a) : neo4j.Promise.fulfilled(null)
            }, i.prototype.pickSearcher = function (a) {
                var b, c, d, e;
                e = this.searchers;
                for (c = 0, d = e.length; c < d; c++) {
                    b = e[c];
                    if (b.match(a)) return b
                }
            }, i
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/search/QueuedSearch", ["./Queue", "./Search"], function (b, d) {
        var e;
        return e = function () {
            function e(c) {
                this.executeNextJob = a(this.executeNextJob, this), this.jobDone = a(this.jobDone, this), this.jobAdded = a(this.jobAdded, this), this.exec = a(this.exec, this), e.__super__.constructor.call(this, c), this.queue = new b, this.queue.bind("item:pushed", this.jobAdded), this.isSearching = !1
            }
            return c(e, d), e.prototype.exec = function (a) {
                var b;
                return b = new neo4j.Promise, this.queue.push({
                    statement: a,
                    promise: b
                }), b
            }, e.prototype.jobAdded = function () {
                if (!this.isSearching) return this.executeNextJob()
            }, e.prototype.jobDone = function () {
                this.isSearching = !1;
                if (this.queue.hasMoreItems()) return this.executeNextJob()
            }, e.prototype.executeNextJob = function () {
                var b, c;
                return b = this.queue.pull(), this.isSearching = !0, c = a(function (a) {
                    return this.jobDone(), b.promise.fulfill(a)
                }, this), e.__super__.exec.call(this, b.statement).then(c, c)
            }, e
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/node", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="headline-bar pad"><div class="title"><h3>Node ' + htmlEscape(item.getId()) + '</h3><p class="small">' + htmlEscape(item.getSelf()) + '</p></div><ul class="button-bar item-controls"><li><a title="Show a list of relationships for this node" href="#/data/search/rels:' + htmlEscape(item.getId()) + '/" class="data-show-relationships button">Show relationships</a></li><li><div disabled="true" class="data-save-properties button">Saved</div></li><li><div class="data-delete-item bad-button">Delete</div></li></ul><div class="break"></div></div><div class="properties"></div><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/models/Property", ["ribcage/security/HtmlEscaper", "ribcage/Model"], function (b, d) {
        var e, f;
        return f = new b, e = function () {
            function b() {
                this.hasValueError = a(this.hasValueError, this), this.hasKeyError = a(this.hasKeyError, this), this.setKey = a(this.setKey, this), this.setValue = a(this.setValue, this), this.setValueError = a(this.setValueError, this), this.setKeyError = a(this.setKeyError, this), this.getKeyAsHtml = a(this.getKeyAsHtml, this), this.getTruncatedHtmlValue = a(this.getTruncatedHtmlValue, this), this.getValueAsHtml = a(this.getValueAsHtml, this), this.getValueAsJSON = a(this.getValueAsJSON, this), this.getKeyError = a(this.getKeyError, this), this.getValueError = a(this.getValueError, this), this.getValue = a(this.getValue, this), this.getKey = a(this.getKey, this), this.getLocalId = a(this.getLocalId, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, d), b.prototype.defaults = {
                key: "",
                value: "",
                keyError: !1,
                valueError: !1
            }, b.prototype.getLocalId = function () {
                return this.get("localId")
            }, b.prototype.getKey = function () {
                return this.get("key")
            }, b.prototype.getValue = function () {
                return this.get("value")
            }, b.prototype.getValueError = function () {
                return this.get("valueError")
            }, b.prototype.getKeyError = function () {
                return this.get("keyError")
            }, b.prototype.getValueAsJSON = function () {
                return this.hasValueError() ? this.getValue() : JSON.stringify(this.getValue())
            }, b.prototype.getValueAsHtml = function () {
                return f.escape(this.getValueAsJSON())
            }, b.prototype.getTruncatedHtmlValue = function (a) {
                var b;
                return a == null && (a = 100), b = this.getValueAsJSON(), b.length > a && (b = b.substr(0, a - 3) + ".."), f.escape(b)
            }, b.prototype.getKeyAsHtml = function () {
                return f.escape(this.getKey())
            }, b.prototype.setKeyError = function (a) {
                return this.set({
                    keyError: a
                })
            }, b.prototype.setValueError = function (a) {
                return this.set({
                    valueError: a
                })
            }, b.prototype.setValue = function (a) {
                return this.set({
                    value: a
                })
            }, b.prototype.setKey = function (a) {
                return this.set({
                    key: a
                })
            }, b.prototype.hasKeyError = function () {
                return this.getKeyError() !== !1
            }, b.prototype.hasValueError = function () {
                return this.getValueError() !== !1
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/models/PropertyContainer", ["neo4j/webadmin/utils/ItemUrlResolver", "./Property", "ribcage/Model"], function (b, d, e) {
        var f, g;
        return f = 0, g = function () {
            function g() {
                this.generatePropertyId = a(this.generatePropertyId, this), this.isValidArrayValue = a(this.isValidArrayValue, this), this.isMap = a(this.isMap, this), this.cleanPropertyValue = a(this.cleanPropertyValue, this), this.noErrors = a(this.noErrors, this), this.setSaveState = a(this.setSaveState, this), this.getSaveState = a(this.getSaveState, this), this.isNotSaved = a(this.isNotSaved, this), this.isSaved = a(this.isSaved, this), this.setNotSaved = a(this.setNotSaved, this), this.setSaved = a(this.setSaved, this), this.saveFailed = a(this.saveFailed, this), this.save = a(this.save, this), this.updatePropertyList = a(this.updatePropertyList, this), this.hasKey = a(this.hasKey, this), this.getPropertyByKey = a(this.getPropertyByKey, this), this.getProperty = a(this.getProperty, this), this.addProperty = a(this.addProperty, this), this.deleteProperty = a(this.deleteProperty, this), this.setValue = a(this.setValue, this), this.setKey = a(this.setKey, this), this.getId = a(this.getId, this), this.getSelf = a(this.getSelf, this), this.getItem = a(this.getItem, this), this.initialize = a(this.initialize, this), g.__super__.constructor.apply(this, arguments)
            }
            return c(g, e), g.prototype.defaults = {
                status: "saved"
            }, g.prototype.initialize = function (a, c) {
                var d, e, f;
                this.properties = {}, this.urlResolver = new b, this.item = a, this.properties = {}, f = this.getItem().getProperties();
                for (d in f) e = f[d], this.addProperty(d, e, {
                    silent: !0
                });
                return this.setSaved(), this.updatePropertyList()
            }, g.prototype.getItem = function () {
                return this.item
            }, g.prototype.getSelf = function () {
                return this.getItem().getSelf()
            }, g.prototype.getId = function () {
                return this.item instanceof neo4j.models.Node ? this.urlResolver.extractNodeId(this.getSelf()) : this.urlResolver.extractRelationshipId(this.getSelf())
            }, g.prototype.setKey = function (a, b, c) {
                var d, e, f;
                return c == null && (c = {}), d = this.hasKey(b, a), f = this.getProperty(a), e = f.getKey(), f.set({
                    key: b
                }), this.setNotSaved(), this.getItem().removeProperty(e), d ? f.setKeyError("This key is already used, please choose a different one.") : (f.setKeyError(!1), this.getItem().setProperty(b, f.getValue())), this.updatePropertyList(c)
            }, g.prototype.setValue = function (a, b, c) {
                var d, e;
                return c == null && (c = {}), e = this.getProperty(a), d = this.cleanPropertyValue(b), this.setNotSaved(), d.value != null ? (e.set({
                    valueError: !1
                }), e.set({
                    value: d.value
                }), this.getItem().setProperty(e.getKey(), d.value)) : (e.set({
                    value: b
                }), e.set({
                    valueError: d.error
                })), this.updatePropertyList(c), e
            }, g.prototype.deleteProperty = function (a, b) {
                var c, d;
                return b == null && (b = {}), this.setNotSaved(), d = this.getProperty(a), delete this.properties[a], this.getItem().removeProperty(d.getKey()), c = this.getPropertyByKey(d.getKey()), c && this.setKey(c.getLocalId(), c.getKey(), b), this.updatePropertyList(b), this.trigger("remove:property")
            }, g.prototype.addProperty = function (a, b, c) {
                var e;
                return a == null && (a = ""), b == null && (b = ""), c == null && (c = {}), e = this.generatePropertyId(), this.properties[e] = new d({
                    key: a,
                    value: b,
                    localId: e
                }), this.updatePropertyList(c), this.trigger("add:property")
            }, g.prototype.getProperty = function (a) {
                return this.properties[a]
            }, g.prototype.getPropertyByKey = function (a, b) {
                var c, d, e;
                b == null && (b = null), e = this.properties;
                for (c in e) {
                    d = e[c];
                    if (d.getKey() === a && parseInt(c) !== parseInt(b)) return d
                }
                return null
            }, g.prototype.hasKey = function (a, b) {
                return b == null && (b = null), this.getPropertyByKey(a, b) !== null
            }, g.prototype.updatePropertyList = function (a) {
                var b, c, d, e, f;
                a == null && (a = {}), b = [], f = this.properties;
                for (c in f) d = f[c], b.push(d);
                e = a.silent != null && a.silent === !0, a.silent = !0, this.set({
                    propertyList: b
                }, a);
                if (!e) return this.trigger("change:propertyList")
            }, g.prototype.save = function () {
                if (this.noErrors()) return this.setSaveState("saving"), this.getItem().save().then(this.setSaved, this.saveFailed)
            }, g.prototype.saveFailed = function (a) {
                return this.setNotSaved()
            }, g.prototype.setSaved = function () {
                return this.setSaveState("saved")
            }, g.prototype.setNotSaved = function () {
                return this.setSaveState("notSaved")
            }, g.prototype.isSaved = function () {
                return this.getSaveState() === "saved"
            }, g.prototype.isNotSaved = function () {
                return this.getSaveState() === "notSaved"
            }, g.prototype.getSaveState = function () {
                return this.get("status")
            }, g.prototype.setSaveState = function (a, b) {
                return b == null && (b = {}), this.set({
                    status: a
                })
            }, g.prototype.noErrors = function (a) {
                var b, c, d;
                a == null && (a = {}), d = this.properties;
                for (b in d) {
                    c = d[b];
                    if (a.ignore == null || a.ignore !== b) if (c.hasKeyError() || c.hasValueError()) return !1
                }
                return !0
            }, g.prototype.cleanPropertyValue = function (a) {
                var b;
                try {
                    return b = JSON.parse(a), b === null ? {
                        error: "Null values are not allowed. Please use strings, numbers or arrays."
                    } : this.isMap(b) ? {
                        error: "Maps are not supported property values. Please use strings, numbers or arrays."
                    } : _(b).isArray() && !this.isValidArrayValue(b) ? {
                        error: "Only arrays with one type of values, and only primitive types, is allowed."
                    } : {
                        value: b
                    }
                } catch (c) {
                    return {
                        error: 'This does not appear to be a valid JSON value. Valid values are JSON strings, numbers or arrays. For instance 1.2, "bob" and [1,2,3].'
                    }
                }
            }, g.prototype.isMap = function (a) {
                return JSON.stringify(a).indexOf("{") === 0
            }, g.prototype.isValidArrayValue = function (a) {
                var b, c, d, e, f;
                if (a.length === 0) return !0;
                b = a[0];
                if (_.isString(b)) c = _.isString;
                else if (_.isNumber(b)) c = _.isNumber;
                else if (_.isBoolean(b)) c = _.isBoolean;
                else return !1;
                for (e = 0, f = a.length; e < f; e++) {
                    d = a[e];
                    if (!c(d)) return !1
                }
                return !0
            }, g.prototype.generatePropertyId = function () {
                return f++
            }, g
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/propertyEditor", [], function () {
        return function (vars) {
            with(vars || {}) return '<ul class="property-list">' + function () {
                var a = [],
                    b, c;
                for (b in properties) properties.hasOwnProperty(b) && (c = properties[b], a.push('<li><ul class="property-row"><li class="property-key-wrap"><div class="property-input-wrap"><input type="hidden" value="' + htmlEscape(c.getLocalId()) + '" class="property-id" />' + function () {
                    return c.hasKeyError() ? '<div class="form-error">' + htmlEscape(c.getKeyError()) + "</div>" : ""
                }.call(this) + function () {
                    return c.hasKeyError() ? "" : '<div style="display:none;" class="form-error"></div>'
                }.call(this) + '<input type="text" value="' + htmlEscape(c.getKey()) + '" class="property-key" /></div></li><li class="property-value-wrap"><div class="property-input-wrap">' + function () {
                    return c.hasValueError() ? '<div class="form-error">' + htmlEscape(c.getValueError()) + "</div>" : ""
                }.call(this) + function () {
                    return c.hasValueError() ? "" : '<div style="display:none;" class="form-error"></div>'
                }.call(this) + '<input type="text" value="' + c.getValueAsHtml() + '" class="property-value" /></div></li><li class="property-actions-wrap"><div class="property-input-wrap"><div class="delete-property bad-button">Remove</div></div></li></ul><div class="break"></div></li>'));
                return a.join("")
            }.call(this) + '<li class="property-controls"><div title="Add a new property" class="add-property text-icon-button"><span class="icon"></span>Add property</div><div class="break"></div></li></ul>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/PropertyContainerView", ["neo4j/webadmin/modules/databrowser/models/PropertyContainer", "ribcage/View", "./propertyEditor", "lib/amd/jQuery"], function (b, d, e, f) {
        var g;
        return g = function () {
            function b() {
                this.shouldBeConvertedToString = a(this.shouldBeConvertedToString, this), this.unbind = a(this.unbind, this), this.remove = a(this.remove, this), this.renderProperties = a(this.renderProperties, this), this.render = a(this.render, this), this.setDataModel = a(this.setDataModel, this), this.getPropertyIdForElement = a(this.getPropertyIdForElement, this), this.setSaveState = a(this.setSaveState, this), this.updateSaveState = a(this.updateSaveState, this), this.updateErrorMessages = a(this.updateErrorMessages, this), this.deleteItem = a(this.deleteItem, this), this.saveChanges = a(this.saveChanges, this), this.addProperty = a(this.addProperty, this), this.deleteProperty = a(this.deleteProperty, this), this.valueChangeDone = a(this.valueChangeDone, this), this.keyChangeDone = a(this.keyChangeDone, this), this.valueChanged = a(this.valueChanged, this), this.keyChanged = a(this.keyChanged, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, d), b.prototype.events = {
                "keyup input.property-key": "keyChanged",
                "keyup input.property-value": "valueChanged",
                "change input.property-key": "keyChangeDone",
                "change input.property-value": "valueChangeDone",
                "click .delete-property": "deleteProperty",
                "click .add-property": "addProperty",
                "click .data-save-properties": "saveChanges",
                "click .data-delete-item": "deleteItem"
            }, b.prototype.initialize = function (a) {
                return this.template = a.template
            }, b.prototype.keyChanged = function (a) {
                var b;
                b = this.getPropertyIdForElement(a.target);
                if (f(a.target).val() !== this.propertyContainer.getProperty(b).getKey()) return this.propertyContainer.setNotSaved()
            }, b.prototype.valueChanged = function (a) {
                var b, c;
                b = this.getPropertyIdForElement(a.target);
                if (f(a.target).val() !== this.propertyContainer.getProperty(b).getValueAsJSON()) return c = this.propertyContainer.setNotSaved()
            }, b.prototype.keyChangeDone = function (a) {
                var b;
                return b = this.getPropertyIdForElement(a.target), this.propertyContainer.setKey(b, f(a.target).val()), this.saveChanges(), this.updateErrorMessages()
            }, b.prototype.valueChangeDone = function (a) {
                var b, c;
                return c = this.getPropertyIdForElement(a.target), b = f(a.target), this.shouldBeConvertedToString(b.val()) && b.val('"' + b.val() + '"'), this.propertyContainer.setValue(c, b.val()), this.saveChanges(), this.updateErrorMessages()
            }, b.prototype.deleteProperty = function (a) {
                var b;
                return b = this.getPropertyIdForElement(a.target), this.propertyContainer.deleteProperty(b), this.propertyContainer.save()
            }, b.prototype.addProperty = function (a) {
                return this.propertyContainer.addProperty()
            }, b.prototype.saveChanges = function (a) {
                return this.propertyContainer.save()
            }, b.prototype.deleteItem = function (a) {
                if (confirm("Are you sure?")) return this.propertyContainer.getItem().remove().then(function () {
                    return window.location = "#/data/search/0"
                })
            }, b.prototype.updateErrorMessages = function () {
                var a, b, c, d, e, g, h, i, j;
                i = f("ul.property-row", this.el), j = [];
                for (g = 0, h = i.length; g < h; g++) d = i[g], a = f(d).find("input.property-id").val(), c = this.propertyContainer.getProperty(a), j.push(c ? (b = f(d).find(".property-key-wrap .form-error"), e = f(d).find(".property-value-wrap .form-error"), c.hasKeyError() ? (b.html(c.getKeyError()), b.show()) : b.hide(), c.hasValueError() ? (e.html(c.getValueError()), e.show()) : e.hide()) : void 0);
                return j
            }, b.prototype.updateSaveState = function (a) {
                var b;
                b = this.propertyContainer.getSaveState();
                switch (b) {
                    case "saved":
                        return this.setSaveState("Saved", !0);
                    case "notSaved":
                        return this.setSaveState("Save", !1);
                    case "saving":
                        return this.setSaveState("Saving..", !0);
                    case "cantSave":
                        return this.setSaveState("Save", !0)
                }
            }, b.prototype.setSaveState = function (a, b) {
                var c;
                return c = f(".data-save-properties", this.el), c.html(a), b ? c.attr("disabled", "disabled") : c.removeAttr("disabled")
            }, b.prototype.getPropertyIdForElement = function (a) {
                return f(a).closest("ul").find("input.property-id").val()
            }, b.prototype.setDataModel = function (a) {
                return this.unbind(), this.propertyContainer = a.getData(), this.propertyContainer.bind("remove:property", this.renderProperties), this.propertyContainer.bind("add:property", this.renderProperties), this.propertyContainer.bind("change:status", this.updateSaveState)
            }, b.prototype.render = function () {
                return f(this.el).html(this.template({
                    item: this.propertyContainer
                })), this.renderProperties(), this
            }, b.prototype.renderProperties = function () {
                return f(".properties", this.el).html(e({
                    properties: this.propertyContainer.get("propertyList")
                })), this
            }, b.prototype.remove = function () {
                return this.unbind(), b.__super__.remove.call(this)
            }, b.prototype.unbind = function () {
                if (this.propertyContainer != null) return this.propertyContainer.unbind("remove:property", this.renderProperties), this.propertyContainer.unbind("add:property", this.renderProperties), this.propertyContainer.unbind("change:status", this.updateSaveState)
            }, b.prototype.shouldBeConvertedToString = function (a) {
                try {
                    return JSON.parse(a), !1
                } catch (b) {
                    return /^[a-z0-9- _\/\\\(\)#%\&!$]+$/i.test(a)
                }
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/NodeView", ["./node", "./PropertyContainerView"], function (b, d) {
        var e;
        return e = function () {
            function e() {
                this.initialize = a(this.initialize, this), e.__super__.constructor.apply(this, arguments)
            }
            return c(e, d), e.prototype.initialize = function (a) {
                return a == null && (a = {}), a.template = b, e.__super__.initialize.call(this, a)
            }, e
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/relationship", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="headline-bar pad"><div class="title"><h3>Relationship ' + htmlEscape(item.getId()) + '</h3><p class="small">' + htmlEscape(item.getSelf()) + '</p></div><ul class="button-bar item-controls"><li><div disabled="true" class="data-save-properties button">Saved</div></li><li><div class="data-delete-item bad-button">Delete</div></li></ul><ul class="relationship-meta"><li><a href="#/data/search/' + htmlEscape(item.getStartId()) + '/" class="micro-button">' + "Node " + htmlEscape(item.getStartId()) + '</a></li><li class="type">' + htmlEscape(item.getItem().getType()) + '</li><li><a href="#/data/search/' + htmlEscape(item.getEndId()) + '/" class="micro-button">' + "Node " + htmlEscape(item.getEndId()) + '</a></li></ul><div class="break"></div></div><div class="properties"></div><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/RelationshipView", ["./relationship", "./PropertyContainerView"], function (b, d) {
        var e;
        return e = function () {
            function e() {
                this.initialize = a(this.initialize, this), e.__super__.constructor.apply(this, arguments)
            }
            return c(e, d), e.prototype.initialize = function (a) {
                return a == null && (a = {}), a.template = b, e.__super__.initialize.call(this, a)
            }, e
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/relationshipList", [], function () {
        return function (vars) {
            with(vars || {}) return '<table cellspacing="0" class="data-table"><tbody><tr><th><h3>Relationship</h3></th><th><h3>Start node</h3></th><th><h3>Type</h3></th><th><h3>End node</h3></th>' + function () {
                var a = [],
                    b, c;
                for (b in relationshipList.getPropertyKeys()) relationshipList.getPropertyKeys().hasOwnProperty(b) && (c = relationshipList.getPropertyKeys()[b], a.push("<th><h3>" + htmlEscape(c) + "</h3></th>"));
                return a.join("")
            }.call(this) + "</tr>" + function () {
                var a = [],
                    b, c;
                for (b in relationshipList.getRelationships()) relationshipList.getRelationships().hasOwnProperty(b) && (c = relationshipList.getRelationships()[b], a.push('<tr><td><a href="#/data/search/rel:' + htmlEscape(c.getId()) + '/" class="micro-button">' + "Relationship " + htmlEscape(c.getId()) + '</a></td><td><a href="#/data/search/' + htmlEscape(c.getStartId()) + '/" class="micro-button">' + "Node " + htmlEscape(c.getStartId()) + '</a></td><td class="small">' + htmlEscape(c.getItem().getType()) + '</td><td><a href="#/data/search/' + htmlEscape(c.getEndId()) + '/" class="micro-button">' + "Node " + htmlEscape(c.getEndId()) + "</a></td>" + function () {
                    var a = [],
                        b, d;
                    for (b in relationshipList.getPropertyKeys()) relationshipList.getPropertyKeys().hasOwnProperty(b) && (d = relationshipList.getPropertyKeys()[b], a.push('<td class="small">' + function () {
                        return c.getPropertyByKey(d) ? c.getPropertyByKey(d).getTruncatedHtmlValue(50) : ""
                    }.call(this) + "</td>"));
                    return a.join("")
                }.call(this) + "</tr>"));
                return a.join("")
            }.call(this) + '</tbody></table><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/RelationshipListView", ["./relationshipList", "ribcage/View", "lib/amd/jQuery"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.setDataModel = a(this.setDataModel, this), this.render = a(this.render, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, d), f.prototype.render = function () {
                return e(this.el).html(b({
                    relationshipList: this.dataModel.getData()
                })), this
            }, f.prototype.setDataModel = function (a) {
                return this.dataModel = a
            }, f
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/nodeList", [], function () {
        return function (vars) {
            with(vars || {}) return '<table cellspacing="0" class="data-table"><tbody><tr><th><h3>Id</h3></th>' + function () {
                var a = [],
                    b, c;
                for (b in nodeList.getPropertyKeys()) nodeList.getPropertyKeys().hasOwnProperty(b) && (c = nodeList.getPropertyKeys()[b], a.push("<th><h3>" + htmlEscape(c) + "</h3></th>"));
                return a.join("")
            }.call(this) + "</tr>" + function () {
                var a = [],
                    b, c;
                for (b in nodeList.getNodes()) nodeList.getNodes().hasOwnProperty(b) && (c = nodeList.getNodes()[b], a.push('<tr><td><a href="#/data/search/' + htmlEscape(c.getId()) + '/" class="micro-button">' + "Node " + htmlEscape(c.getId()) + "</a></td>" + function () {
                    var a = [],
                        b, d;
                    for (b in nodeList.getPropertyKeys()) nodeList.getPropertyKeys().hasOwnProperty(b) && (d = nodeList.getPropertyKeys()[b], a.push('<td class="small">' + function () {
                        return c.getPropertyByKey(d) ? c.getPropertyByKey(d).getTruncatedHtmlValue(50) : ""
                    }.call(this) + "</td>"));
                    return a.join("")
                }.call(this) + "</tr>"));
                return a.join("")
            }.call(this) + '</tbody></table><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/NodeListView", ["./nodeList", "ribcage/View", "lib/amd/jQuery"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.setDataModel = a(this.setDataModel, this), this.render = a(this.render, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, d), f.prototype.render = function () {
                return e(this.el).html(b({
                    nodeList: this.dataModel.getData()
                })), this
            }, f.prototype.setDataModel = function (a) {
                return this.dataModel = a
            }, f
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/cypherResult", [], function () {
        return function (vars) {
            with(vars || {}) return '<table cellspacing="0" class="data-table"><tbody><tr>' + function () {
                var a = [],
                    b, c;
                for (b in result.columns) result.columns.hasOwnProperty(b) && (c = result.columns[b], a.push("<th><h3>" + htmlEscape(c) + "</h3></th>"));
                return a.join("")
            }.call(this) + "</tr>" + function () {
                var a = [],
                    b, c;
                for (b in result.data) result.data.hasOwnProperty(b) && (c = result.data[b], a.push("<tr>" + function () {
                    var a = [],
                        b, d;
                    for (b in c) c.hasOwnProperty(b) && (d = c[b], a.push('<td class="small">' + function () {
                        return d != null && typeof d.self != "undefined" && typeof d.type == "undefined" ? '<a href="#/data/search/' + htmlEscape(id(d)) + '/" class="micro-button">' + "Node " + htmlEscape(id(d)) + "</a>" : ""
                    }.call(this) + function () {
                        return d != null && typeof d.self != "undefined" && typeof d.type != "undefined" ? '<a href="#/data/search/rel:' + htmlEscape(id(d)) + '/" class="micro-button">' + "Rel " + htmlEscape(id(d)) + "</a>" : ""
                    }.call(this) + function () {
                        return d != null && typeof d.self == "undefined" ? htmlEscape(JSON.stringify(d)) : ""
                    }.call(this) + function () {
                        return d == null ? "null" : ""
                    }.call(this) + "</td>"));
                    return a.join("")
                }.call(this) + "</tr>"));
                return a.join("")
            }.call(this) + '</tbody></table><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/CypherResultView", ["./cypherResult", "ribcage/View", "lib/amd/jQuery"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.setDataModel = a(this.setDataModel, this), this.render = a(this.render, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, d), f.prototype.render = function () {
                return e(this.el).html(b({
                    result: this.dataModel.getData(),
                    id: function (a) {
                        return a.self.substr(a.self.lastIndexOf("/") + 1)
                    }
                })), this
            }, f.prototype.setDataModel = function (a) {
                return this.dataModel = a
            }, f
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/notfound", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="pad"><h1>Not found</h1><p>There was no result returned for your query.</p></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/TabularView", ["./NodeView", "./RelationshipView", "./RelationshipListView", "./NodeListView", "./CypherResultView", "ribcage/View", "./notfound", "lib/amd/jQuery"], function (b, d, e, f, g, h, i, j) {
        var k;
        return k = function () {
            function k() {
                this.remove = a(this.remove, this), this.render = a(this.render, this), k.__super__.constructor.apply(this, arguments)
            }
            return c(k, h), k.prototype.initialize = function (a) {
                return this.nodeView = new b, this.relationshipView = new d, this.relationshipListView = new e, this.nodeListView = new f, this.cypherResultView = new g, this.dataModel = a.dataModel, this.dataModel.bind("change:data", this.render)
            }, k.prototype.render = function () {
                var a, b;
                a = this.dataModel.get("type");
                switch (a) {
                    case "node":
                        b = this.nodeView;
                        break;
                    case "nodeList":
                        b = this.nodeListView;
                        break;
                    case "relationship":
                        b = this.relationshipView;
                        break;
                    case "relationshipList":
                        b = this.relationshipListView;
                        break;
                    case "cypher":
                        b = this.cypherResultView;
                        break;
                    default:
                        return j(this.el).html(i()), this
                }
                return b.setDataModel(this.dataModel), j(this.el).html(b.render().el), b.delegateEvents(), this
            }, k.prototype.remove = function () {
                return this.dataModel.unbind("change", this.render), this.nodeView.remove(), this.nodeListView.remove(), this.relationshipView.remove(), this.relationshipListView.remove(), k.__super__.remove.call(this)
            }, k
        }()
    })
}.call(this),
function () {
    define("neo4j/webadmin/modules/databrowser/visualization/RelationshipStyler", [], function () {
        var a;
        return a = function () {
            function a() {}
            return a.prototype.defaultBetweenExploredStyle = {
                edgeStyle: {
                    color: "rgba(0, 0, 0, 1)",
                    width: 1
                },
                labelStyle: {
                    color: "rgba(0, 0, 0, 1)",
                    font: "10px Helvetica"
                }
            }, a.prototype.defaultToUnknownStyle = {
                edgeStyle: {
                    color: "rgba(0, 0, 0, 0.2)",
                    width: 1
                },
                labelStyle: {
                    color: "rgba(0, 0, 0, 0.4)",
                    font: "10px Helvetica"
                }
            }, a.prototype.defaultToGroupStyle = {
                edgeStyle: {
                    color: "rgba(0, 0, 0, 0.8)",
                    width: 1
                },
                labelStyle: {
                    color: "rgba(0, 0, 0, 0.8)",
                    font: "10px Helvetica"
                }
            }, a.prototype.getStyleFor = function (a) {
                var b, c, d, e, f, g;
                return e = a.source.data.type, b = a.target.data.type, a.target.data.relType !== null && (f = function () {
                    var b, c;
                    b = a.data.relationships, c = [];
                    for (g in b) d = b[g], c.push(d.getType());
                    return c
                }(), f = _.uniq(f), c = f.join(", ")), e === "explored" && b === "explored" ? {
                    edgeStyle: this.defaultBetweenExploredStyle.edgeStyle,
                    labelStyle: this.defaultBetweenExploredStyle.labelStyle,
                    labelText: c
                } : e === "unexplored" || b === "unexplored" ? {
                    edgeStyle: this.defaultToUnknownStyle.edgeStyle,
                    labelStyle: this.defaultToUnknownStyle.labelStyle,
                    labelText: c
                } : {
                    edgeStyle: this.defaultToGroupStyle.edgeStyle,
                    labelStyle: this.defaultToGroupStyle.labelStyle,
                    labelText: a.data.relType
                }
            }, a
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/visualization/VisualDataModel", [], function () {
        var b;
        return b = function () {
            function b(b) {
                this.groupingThreshold = b != null ? b : 5, this.addNode = a(this.addNode, this), this.clear()
            }
            return b.prototype.clear = function () {
                return this.groupCount = 0, this.visualGraph = {
                    nodes: {},
                    edges: {}
                }, this.data = {
                    relationships: {},
                    nodes: {},
                    groups: {}
                }
            }, b.prototype.getVisualGraph = function () {
                var a, b, c, d, e;
                if (_(this.visualGraph.edges).keys().length === 0) {
                    d = this.visualGraph.nodes;
                    for (a in d) b = d[a], this.visualGraph.nodes["" + a + "-SECRET-HACK-NODE"] = {
                        hidden: !0
                    }, (e = (c = this.visualGraph.edges)[a]) == null && (c[a] = {}), this.visualGraph.edges[a]["" + a + "-SECRET-HACK-NODE"] = {
                        hidden: !0
                    }
                }
                return this.visualGraph
            }, b.prototype.addNode = function (a, b, c) {
                var d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y;
                if (this.visualGraph.nodes[a.getSelf()] == null || this.visualGraph.nodes[a.getSelf()].type !== "explored") this.ungroup([a]), this.ungroup(c), (v = (l = this.data.nodes)[s = a.getSelf()]) == null && (l[s] = {
                    node: a,
                    groups: {}
                }), this.visualGraph.nodes[a.getSelf()] = {
                    neoNode: a,
                    type: "explored"
                };
                for (o = 0, q = c.length; o < q; o++) j = c[o], (w = (m = this.data.nodes)[t = j.getSelf()]) == null && (m[t] = {
                    node: j,
                    groups: {}
                });
                h = {
                    incoming: {},
                    outgoing: {}
                };
                for (p = 0, r = b.length; p < r; p++) {
                    i = b[p];
                    if (this.data.relationships[i.getSelf()] != null) continue;
                    this.data.relationships[i.getSelf()] = i, g = i.getOtherNodeUrl(a.getSelf()), d = i.isStartNode(a.getSelf()) ? "outgoing" : "incoming", this.visualGraph.nodes[g] == null ? ((x = (n = h[d])[u = i.getType()]) == null && (n[u] = {
                        relationships: []
                    }), h[d][i.getType()].relationships.push(i)) : this._addRelationship(i.getStartNodeUrl(), i.getEndNodeUrl(), i)
                }
                y = [];
                for (d in h) f = h[d], y.push(function () {
                    var b;
                    b = [];
                    for (k in f) e = f[k], b.push(function () {
                        var b, c, f, g;
                        if (e.relationships.length < this.groupingThreshold) {
                            f = e.relationships, g = [];
                            for (b = 0, c = f.length; b < c; b++) i = f[b], g.push(this._addUnexploredNode(a, i));
                            return g
                        }
                        return this._addGroup(a, e.relationships, d)
                    }.call(this));
                    return b
                }.call(this));
                return y
            }, b.prototype.ungroup = function (a) {
                var b, c, d, e, f, g, h, i, j, k;
                k = [];
                for (i = 0, j = a.length; i < j; i++) f = a[i], g = f.getSelf(), k.push(function () {
                    var a, f, i, j, k;
                    if (this.data.nodes[g] != null) {
                        e = this.data.nodes[g], i = e.groups, k = [];
                        for (d in i) {
                            c = i[d], b = c.group, b.nodeCount--, delete b.grouped[g], j = c.relationships;
                            for (a = 0, f = j.length; a < f; a++) h = j[a], this._addUnexploredNode(b.baseNode, h);
                            k.push(b.nodeCount > 0 ? void 0 : this._removeGroup(b))
                        }
                        return k
                    }
                }.call(this));
                return k
            }, b.prototype.unexplore = function (a) {
                var b, c, d, e, f;
                b = a.getSelf();
                if (this._isLastExploredNode(a)) return;
                if (this.visualGraph.nodes[b] != null) {
                    f = this.visualGraph.nodes[b], f.type = "unexplored", a.fixed = !1, c = this._getUnexploredNodesRelatedTo(b);
                    for (e in c) d = c[e], this._hasExploredRelationships(e, b) || this.removeNode(d.node);
                    this._removeGroupsFor(a);
                    if (!this._hasExploredRelationships(b)) return this.removeNode(a)
                }
            }, b.prototype.removeNode = function (a) {
                var b;
                return b = a.getSelf(), delete this.visualGraph.nodes[b], delete this.data.nodes[b], this._removeRelationshipsFor(a)
            }, b.prototype._isLastExploredNode = function (a) {
                var b, c, d, e;
                b = a.getSelf(), e = this.visualGraph.nodes;
                for (c in e) {
                    d = e[c];
                    if (d.type === "explored" && c !== b) return !1
                }
                return !0
            }, b.prototype._getUnexploredNodesRelatedTo = function (a) {
                var b, c, d, e, f, g;
                b = [], g = this.visualGraph.edges;
                for (c in g) {
                    e = g[c];
                    for (f in e) d = e[f], c === a && this.visualGraph.nodes[f].type != null && this.visualGraph.nodes[f].type === "unexplored" && (b[f] = this.data.nodes[f]), f === a && this.visualGraph.nodes[c].type != null && this.visualGraph.nodes[c].type === "unexplored" && (b[c] = this.data.nodes[c])
                }
                return b
            }, b.prototype._hasExploredRelationships = function (a, b) {
                var c, d, e, f, g;
                b == null && (b = ""), g = this.visualGraph.edges;
                for (c in g) {
                    e = g[c];
                    for (f in e) {
                        d = e[f];
                        if (c === a && f !== b && this.visualGraph.nodes[f].type === "explored") return !0;
                        if (f === a && c !== b && this.visualGraph.nodes[c].type === "explored") return !0
                    }
                }
                return !1
            }, b.prototype._addRelationship = function (a, b, c, d) {
                var e, f, g, h;
                d == null && (d = null), (g = (e = this.visualGraph.edges)[a]) == null && (e[a] = {}), (h = (f = this.visualGraph.edges[a])[b]) == null && (f[b] = {
                    length: .5,
                    relationships: {},
                    directed: !0,
                    relType: d
                });
                if (c !== !1) return this.visualGraph.edges[a][b].relationships[c.getSelf()] = c
            }, b.prototype._addUnexploredNode = function (a, b) {
                var c, d, e;
                return c = b.getOtherNodeUrl(a.getSelf()), (e = (d = this.visualGraph.nodes)[c]) == null && (d[c] = {
                    neoNode: this.data.nodes[c].node,
                    type: "unexplored"
                }), this._addRelationship(b.getStartNodeUrl(), b.getEndNodeUrl(), b)
            }, b.prototype._addGroup = function (a, b, c) {
                var d, e, f, g, h, i, j, k, l, m, n, o;
                d = a.getSelf(), i = 0, f = {};
                for (n = 0, o = b.length; n < o; n++) {
                    l = b[n], k = l.getOtherNodeUrl(d);
                    if (this.data.nodes[k] == null) continue;
                    j = this.data.nodes[k], f[k] == null && (f[k] = {
                        node: j.node,
                        relationships: []
                    }, i++), f[k].relationships.push(l)
                }
                g = "group-" + this.groupCount++, e = this.data.groups[g] = {
                    key: g,
                    baseNode: a,
                    grouped: f,
                    nodeCount: i
                }, this.visualGraph.nodes[g] = {
                    key: g,
                    type: "group",
                    group: e
                };
                for (m in f) h = f[m], this.data.nodes[m].groups[g] = {
                    group: e,
                    relationships: h.relationships
                };
                return c === "outgoing" ? this._addRelationship(a.getSelf(), g, !1, b[0].getType()) : this._addRelationship(g, a.getSelf(), !1, b[0].getType())
            }, b.prototype._removeRelationshipsFor = function (a) {
                var b, c, d, e, f, g, h, i, j;
                c = a.getSelf(), i = this.visualGraph.edges;
                for (b in i) {
                    f = i[b];
                    for (g in f) {
                        e = f[g];
                        if (g === c || b === c) {
                            j = this.visualGraph.edges[b][g].relationships;
                            for (h in j) d = j[h], delete this.data.relationships[d.getSelf()]
                        }
                        g === c && delete this.visualGraph.edges[b][g]
                    }
                }
                if (this.visualGraph.edges[c] != null) return delete this.visualGraph.edges[c]
            }, b.prototype._removeRelationshipsInGroup = function (a) {
                var b, c, d, e, f, g;
                f = a.grouped, g = [];
                for (e in f) b = f[e], c = this.data.nodes[e], c.groups[a.key] != null && delete c.groups[a.key], g.push(function () {
                    var a, c, e, f;
                    e = b.relationships, f = [];
                    for (a = 0, c = e.length; a < c; a++) d = e[a], f.push(delete this.data.relationships[d.getSelf()]);
                    return f
                }.call(this));
                return g
            }, b.prototype._removeGroup = function (a) {
                delete this.visualGraph.nodes[a.key], delete this.data.groups[a.key];
                if (this.visualGraph.edges[a.key] != null) return delete this.visualGraph.edges[a.key];
                if (this.visualGraph.edges[a.baseNode.getSelf()] != null && this.visualGraph.edges[a.baseNode.getSelf()][a.key] != null) return delete this.visualGraph.edges[a.baseNode.getSelf()][a.key]
            }, b.prototype._removeGroupsFor = function (a) {
                var b, c, d, e, f;
                d = a.getSelf(), e = this.data.groups, f = [];
                for (c in e) b = e[c], f.push(b.baseNode.getSelf() === d ? (this._removeRelationshipsInGroup(b), this._removeGroup(b)) : void 0);
                return f
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("ribcage/ui/Overlay", ["lib/amd/jQuery"], function (b) {
        var c, d, e;
        return e = b("<div class='overlay'></div>"), d = !1, c = function () {
            function b(b) {
                b == null && (b = {}), this.hide = a(this.hide, this), this.show = a(this.show, this), this.el = e
            }
            return b.prototype.show = function (a, b, c) {
                return d || (this.el.appendTo("body"), d = !0), this.el.show()
            }, b.prototype.hide = function () {
                return this.el.hide()
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("ribcage/ui/Dialog", ["./Overlay", "ribcage/View"], function (b, d) {
        var e;
        return e = function () {
            function e(b) {
                this.wrappedView = b, this.attach = a(this.attach, this), this.detach = a(this.detach, this), this.remove = a(this.remove, this), this.hide = a(this.hide, this), this.wrapperClicked = a(this.wrapperClicked, this), this.show = a(this.show, this), e.__super__.constructor.call(this)
            }
            return c(e, d), e.prototype.className = "dialog", e.prototype.initialize = function () {
                return this.el = $(this.el), this.wrapper = $("<div class='dialog-wrap'></div>"), this.wrapper.append(this.el), this.attachedToBody = !1, this.overlay = new b
            }, e.prototype.render = function () {
                return this.el.html(), this.el.append(this.wrappedView.render().el)
            }, e.prototype.show = function (a) {
                a == null && (a = !1), this.overlay.show(), this.bind(), this.attachedToBody || $("body").append(this.wrapper), this.render(), this.wrapper.show();
                if (a) return setTimeout(this.hide, a)
            }, e.prototype.bind = function () {
                return this.wrapper.bind("click", this.wrapperClicked)
            }, e.prototype.unbind = function () {
                return this.wrapper.unbind("click", this.wrapperClicked)
            }, e.prototype.wrapperClicked = function (a) {
                if (a.originalTarget === a.currentTarget) return this.hide()
            }, e.prototype.hide = function () {
                return this.unbind(), this.wrapper.hide(), this.overlay.hide()
            }, e.prototype.remove = function () {
                return this.unbind(), this.wrapper.remove(), this.overlay.hide()
            }, e.prototype.detach = function () {
                return this.wrapper.detach()
            }, e.prototype.attach = function (a) {
                return $(a).append(this.wrapper)
            }, e
        }()
    })
}.call(this),
function (define) {
    define("ribcage/ui/filterListTemplate", [], function () {
        return function (vars) {
            with(vars || {}) return '<p><input type="text" value="' + htmlEscape(filter) + '" class="filterText" /></p><p class="selectWrap"></p>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("ribcage/ui/filterListSelect", [], function () {
        return function (vars) {
            with(vars || {}) return '<select multiple="multiple" class="selectList">' + function () {
                var a = [],
                    b, c;
                for (b in items) items.hasOwnProperty(b) && (c = items[b], a.push('<option value="' + htmlEscape(c.key) + '">' + htmlEscape(c.label) + "</option>"));
                return a.join("")
            }.call(this) + "</select>"
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("ribcage/ui/FilterList", ["ribcage/View", "./filterListTemplate", "./filterListSelect"], function (a, c, d) {
        var e, f;
        return e = "Search available nodes", f = function () {
            function f() {
                f.__super__.constructor.apply(this, arguments)
            }
            return b(f, a), f.prototype.events = {
                "keyup .filterText": "filterKeyUp",
                "change .filterText": "filterChanged",
                "focus .filterText": "filterFocused",
                "blur .filterText": "filterUnfocused"
            }, f.prototype.initialize = function (a) {
                var b, c, d;
                this.items = this.filteredItems = a, this.filter = "", this.keyMap = {};
                for (c = 0, d = a.length; c < d; c++) b = a[c], this.keyMap[b.key] = b;
                return f.__super__.initialize.call(this)
            }, f.prototype.render = function () {
                return $(this.el).html(c({
                    filter: this.filter
                })), this.renderListSelector(), $(".filterText", this.el).focus()
            }, f.prototype.height = function (a) {
                return a != null ? $(".selectList", this.el).height(a - 50) : f.__super__.height.call(this)
            }, f.prototype.renderListSelector = function () {
                return $(".selectWrap", this.el).html(d({
                    items: this.filteredItems
                }))
            }, f.prototype.filterKeyUp = function (a) {
                if ($(a.target).val().toLowerCase() !== this.filter) return this.filterChanged(a)
            }, f.prototype.filterFocused = function (a) {
                if ($(a.target).val() === e) return $(a.target).val("")
            }, f.prototype.filterUnfocused = function (a) {
                if ($(a.target).val().length === 0) return $(a.target).val(e)
            }, f.prototype.filterChanged = function (a) {
                var b, c, d, f;
                this.filter = $(a.target).val().toLowerCase();
                if (this.filter.length === 0 || this.filter === e.toLowerCase()) this.filteredItems = this.items;
                else {
                    this.filteredItems = [], f = this.items;
                    for (c = 0, d = f.length; c < d; c++) b = f[c], b.label.toLowerCase().indexOf(this.filter) !== -1 && this.filteredItems.push(b)
                }
                return this.renderListSelector()
            }, f.prototype.getFilteredItems = function () {
                var a, b, c, d, e;
                b = $(".selectList", this.el).val();
                if (b !== null) {
                    e = [];
                    for (c = 0, d = b.length; c < d; c++) a = b[c], e.push(this.keyMap[a]);
                    return e
                }
                return []
            }, f
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/visualization/views/nodeFilterDialogTemplate", [], function () {
        return function (vars) {
            with(vars || {}) return '<h1>Choose nodes</h1><div class="filter"></div><ul class="button-bar"><li><button class="complete button">Select</button></li><li><button class="selectAll button">Select all</button></li><li><button class="cancel button bad-button">Select none</button></li></ul>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/views/NodeFilterDialog", ["neo4j/webadmin/utils/ItemUrlResolver", "ribcage/ui/Dialog", "ribcage/ui/FilterList", "./nodeFilterDialogTemplate", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function h(c, d, f) {
                var g, i, j, k;
                this.nodes = c, this.completeCallback = d, f == null && (f = []), this.complete = a(this.complete, this), this.wrapperClicked = a(this.wrapperClicked, this), h.__super__.constructor.call(this), this.urlResolver = new b, g = function () {
                    var a, b, c, d;
                    c = this.nodes, d = [];
                    for (a = 0, b = c.length; a < b; a++) k = c[a], i = this.urlResolver.extractNodeId(k.getSelf()), j = "" + i + ": " + JSON.stringify(k.getProperties()), d.push({
                        node: k,
                        key: k.getSelf(),
                        label: j
                    });
                    return d
                }.call(this), this.filterList = new e(g), h.__super__.constructor.call(this)
            }
            return c(h, d), h.prototype.events = {
                "dblclick .selectWrap": "w",
                "click .complete": "complete",
                "click .selectAll": "selectAll",
                "click .cancel": "cancel"
            }, h.prototype.w = function () {}, h.prototype.render = function () {
                var a;
                return g(this.el).html(f()), this.filterList.attach(g(".filter", this.el)), this.filterList.render(), a = g(this.el).height(), this.filterList.height(a - 80)
            }, h.prototype.wrapperClicked = function (a) {
                if (a.originalTarget === a.currentTarget) return this.cancel()
            }, h.prototype.complete = function () {
                var a, b;
                return b = function () {
                    var b, c, d, e;
                    d = this.filterList.getFilteredItems(), e = [];
                    for (b = 0, c = d.length; b < c; b++) a = d[b], e.push(a.node);
                    return e
                }.call(this), this.completeCallback(b, this)
            }, h.prototype.selectAll = function () {
                return this.completeCallback(this.nodes, this)
            }, h.prototype.cancel = function () {
                return this.completeCallback([], this)
            }, h
        }()
    })
}.call(this),
function (a) {
    var b = function (a) {
        if (typeof window == "undefined" || !window.console) return;
        var b = arguments.length,
            c = [];
        for (var d = 0; d < b; d++) c.push(arguments[d]);
        try {
            console.log.apply(this, c)
        } catch (e) {}
    }, c = function (a) {
        var b = a.replace(/^\/?(.*?)\/?$/, "$1").split("/");
        return b.pop(), "/" + b.join("/")
    }, d = function (a) {
        var b = a.replace(/^\/?(.*?)\/?$/, "$1").split("/"),
            c = b.pop();
        return c == "" ? null : c
    }, e = /(\d)(?=(\d\d\d)+(?!\d))/g,
        f = function (a) {
            var b = "" + a;
            return a < 11e3 ? b = ("" + a).replace(e, "$1,") : a < 1e6 ? b = Math.floor(a / 1e3) + "k" : a < 1e9 && (b = ("" + Math.floor(a / 1e3)).replace(e, "$1,") + "m"), b
        }, g = function (b, c) {
            return b.replace(/\{([\w\-\.]*)}/g, function (b, d) {
                var e = d.split("."),
                    f = c[e.shift()];
                return a.each(e, function () {
                    f.hasOwnProperty(this) ? f = f[this] : f = b
                }), f
            })
        }, h = function (b) {
            if (b === undefined) return undefined;
            if (b === null) return null;
            if (b.parentNode) return b;
            switch (typeof b) {
                case "string":
                    return b.substring(0);
                case "number":
                    return b + 0;
                case "boolean":
                    return b === !0
            }
            var c = a.isArray(b) ? [] : {};
            return a.each(b, function (a, b) {
                c[a] = h(b)
            }), c
        }, i = function (a, b) {
            a = a || {}, b = b || {};
            var c = h(a);
            for (var d in b) c[d] = b[d];
            return c
        }, j = function (b, c, d) {
            if (!b || !c) return b === c;
            if (typeof b != typeof c) return !1;
            if (typeof b != "object") return b === c;
            if (a.isArray(b)) {
                if (!a.isArray(c)) return !1;
                if (b.length != c.length) return !1
            } else {
                var e = [];
                for (var f in b) b.hasOwnProperty(f) && e.push(f);
                var g = [];
                for (var f in c) c.hasOwnProperty(f) && g.push(f);
                d || (e.sort(), g.sort());
                if (e.join(",") !== g.join(",")) return !1
            }
            var h = !0;
            return a.each(b, function (a) {
                var d = j(b[a], c[a]);
                h = h && d;
                if (!h) return !1
            }), h
        }, k = function (b) {
            var c = [];
            return a.each(b, function (a, d) {
                b.hasOwnProperty(a) && c.push(a)
            }), c
        }, l = function (a) {
            if (!a || typeof a != "object") return !1;
            for (var b = 1, c = arguments.length; b < c; b++) if (a.hasOwnProperty(arguments[b])) return !0;
            return !1
        }, m = function (a) {
            var b = a.length,
                c = {};
            for (var d = 0; d < b; d++) c[a[d]] = !0;
            return k(c)
        }, n = function () {
            var b = a("script").map(function (b) {
                var c = a(this).attr("src");
                if (!c) return;
                if (c.match(/arbor[^\/\.]*.js|dev.js/)) return c.match(/.*\//) || "/"
            });
            return b.length > 0 ? b[0] : null
        }, o = function () {
            var a = /#[0-9a-f]{6}/i,
                b = /#(..)(..)(..)/,
                c = function (a) {
                    var b = a.toString(16);
                    return b.length == 2 ? b : "0" + b
                }, d = function (a) {
                    return parseInt(a, 16)
                }, e = function (a) {
                    if (!a || typeof a != "object") return !1;
                    var b = k(a).sort().join("");
                    if (b == "abgr") return !0
                }, f = {
                    CSS: {
                        aliceblue: "#f0f8ff",
                        antiquewhite: "#faebd7",
                        aqua: "#00ffff",
                        aquamarine: "#7fffd4",
                        azure: "#f0ffff",
                        beige: "#f5f5dc",
                        bisque: "#ffe4c4",
                        black: "#000000",
                        blanchedalmond: "#ffebcd",
                        blue: "#0000ff",
                        blueviolet: "#8a2be2",
                        brown: "#a52a2a",
                        burlywood: "#deb887",
                        cadetblue: "#5f9ea0",
                        chartreuse: "#7fff00",
                        chocolate: "#d2691e",
                        coral: "#ff7f50",
                        cornflowerblue: "#6495ed",
                        cornsilk: "#fff8dc",
                        crimson: "#dc143c",
                        cyan: "#00ffff",
                        darkblue: "#00008b",
                        darkcyan: "#008b8b",
                        darkgoldenrod: "#b8860b",
                        darkgray: "#a9a9a9",
                        darkgrey: "#a9a9a9",
                        darkgreen: "#006400",
                        darkkhaki: "#bdb76b",
                        darkmagenta: "#8b008b",
                        darkolivegreen: "#556b2f",
                        darkorange: "#ff8c00",
                        darkorchid: "#9932cc",
                        darkred: "#8b0000",
                        darksalmon: "#e9967a",
                        darkseagreen: "#8fbc8f",
                        darkslateblue: "#483d8b",
                        darkslategray: "#2f4f4f",
                        darkslategrey: "#2f4f4f",
                        darkturquoise: "#00ced1",
                        darkviolet: "#9400d3",
                        deeppink: "#ff1493",
                        deepskyblue: "#00bfff",
                        dimgray: "#696969",
                        dimgrey: "#696969",
                        dodgerblue: "#1e90ff",
                        firebrick: "#b22222",
                        floralwhite: "#fffaf0",
                        forestgreen: "#228b22",
                        fuchsia: "#ff00ff",
                        gainsboro: "#dcdcdc",
                        ghostwhite: "#f8f8ff",
                        gold: "#ffd700",
                        goldenrod: "#daa520",
                        gray: "#808080",
                        grey: "#808080",
                        green: "#008000",
                        greenyellow: "#adff2f",
                        honeydew: "#f0fff0",
                        hotpink: "#ff69b4",
                        indianred: "#cd5c5c",
                        indigo: "#4b0082",
                        ivory: "#fffff0",
                        khaki: "#f0e68c",
                        lavender: "#e6e6fa",
                        lavenderblush: "#fff0f5",
                        lawngreen: "#7cfc00",
                        lemonchiffon: "#fffacd",
                        lightblue: "#add8e6",
                        lightcoral: "#f08080",
                        lightcyan: "#e0ffff",
                        lightgoldenrodyellow: "#fafad2",
                        lightgray: "#d3d3d3",
                        lightgrey: "#d3d3d3",
                        lightgreen: "#90ee90",
                        lightpink: "#ffb6c1",
                        lightsalmon: "#ffa07a",
                        lightseagreen: "#20b2aa",
                        lightskyblue: "#87cefa",
                        lightslategray: "#778899",
                        lightslategrey: "#778899",
                        lightsteelblue: "#b0c4de",
                        lightyellow: "#ffffe0",
                        lime: "#00ff00",
                        limegreen: "#32cd32",
                        linen: "#faf0e6",
                        magenta: "#ff00ff",
                        maroon: "#800000",
                        mediumaquamarine: "#66cdaa",
                        mediumblue: "#0000cd",
                        mediumorchid: "#ba55d3",
                        mediumpurple: "#9370d8",
                        mediumseagreen: "#3cb371",
                        mediumslateblue: "#7b68ee",
                        mediumspringgreen: "#00fa9a",
                        mediumturquoise: "#48d1cc",
                        mediumvioletred: "#c71585",
                        midnightblue: "#191970",
                        mintcream: "#f5fffa",
                        mistyrose: "#ffe4e1",
                        moccasin: "#ffe4b5",
                        navajowhite: "#ffdead",
                        navy: "#000080",
                        oldlace: "#fdf5e6",
                        olive: "#808000",
                        olivedrab: "#6b8e23",
                        orange: "#ffa500",
                        orangered: "#ff4500",
                        orchid: "#da70d6",
                        palegoldenrod: "#eee8aa",
                        palegreen: "#98fb98",
                        paleturquoise: "#afeeee",
                        palevioletred: "#d87093",
                        papayawhip: "#ffefd5",
                        peachpuff: "#ffdab9",
                        peru: "#cd853f",
                        pink: "#ffc0cb",
                        plum: "#dda0dd",
                        powderblue: "#b0e0e6",
                        purple: "#800080",
                        red: "#ff0000",
                        rosybrown: "#bc8f8f",
                        royalblue: "#4169e1",
                        saddlebrown: "#8b4513",
                        salmon: "#fa8072",
                        sandybrown: "#f4a460",
                        seagreen: "#2e8b57",
                        seashell: "#fff5ee",
                        sienna: "#a0522d",
                        silver: "#c0c0c0",
                        skyblue: "#87ceeb",
                        slateblue: "#6a5acd",
                        slategray: "#708090",
                        slategrey: "#708090",
                        snow: "#fffafa",
                        springgreen: "#00ff7f",
                        steelblue: "#4682b4",
                        tan: "#d2b48c",
                        teal: "#008080",
                        thistle: "#d8bfd8",
                        tomato: "#ff6347",
                        turquoise: "#40e0d0",
                        violet: "#ee82ee",
                        wheat: "#f5deb3",
                        white: "#ffffff",
                        whitesmoke: "#f5f5f5",
                        yellow: "#ffff00",
                        yellowgreen: "#9acd32"
                    },
                    decode: function (c) {
                        var g = arguments.length;
                        for (var h = g - 1; h >= 0; h--) arguments[h] === undefined && g--;
                        var i = arguments;
                        if (!c) return null;
                        if (g == 1 && e(c)) return c;
                        var j = null;
                        if (typeof c == "string") {
                            var k = 1;
                            g == 2 && (k = i[1]);
                            var l = f.CSS[c.toLowerCase()];
                            l !== undefined && (c = l);
                            var m = c.match(a);
                            if (m) {
                                vals = c.match(b);
                                if (!vals || !vals.length || vals.length != 4) return null;
                                j = {
                                    r: d(vals[1]),
                                    g: d(vals[2]),
                                    b: d(vals[3]),
                                    a: k
                                }
                            }
                        } else typeof c == "number" && (g < 3 ? g >= 1 && (j = {
                            r: i[0],
                            g: i[0],
                            b: i[0],
                            a: 1
                        }, g == 2 && (j.a *= i[1])) : (j = {
                            r: i[0],
                            g: i[1],
                            b: i[2],
                            a: 1
                        }, g >= 4 && (j.a *= i[3])));
                        return j
                    },
                    validate: function (b) {
                        return !b || typeof b != "string" ? !1 : f.CSS[b.toLowerCase()] !== undefined ? !0 : b.match(a) ? !0 : !1
                    },
                    mix: function (a, b, c) {
                        var d = f.decode(a),
                            e = f.decode(b)
                    },
                    blend: function (a, b) {
                        b = b !== undefined ? Math.max(0, Math.min(1, b)) : 1;
                        var c = f.decode(a);
                        if (!c) return null;
                        if (b == 1) return a;
                        var c = a;
                        typeof a == "string" && (c = f.decode(a));
                        var d = h(c);
                        return d.a *= b, g("rgba({r},{g},{b},{a})", d)
                    },
                    encode: function (a) {
                        if (!e(a)) {
                            a = f.decode(a);
                            if (!e(a)) return null
                        }
                        return a.a == 1 ? g("#{r}{g}{b}", {
                            r: c(a.r),
                            g: c(a.g),
                            b: c(a.b)
                        }) : g("rgba({r},{g},{b},{a})", a)
                    }
                };
            return f
        }(),
        p = function (c, d, e) {
            var f = function (a, b, c, d, e) {
                this.x = a, this.y = b, this.w = c, this.h = d, this.style = e !== undefined ? e : {}
            };
            f.prototype = {
                draw: function (a) {
                    this._draw(a)
                },
                _draw: function (a, b, e, f, g) {
                    l(a, "stroke", "fill", "width") && (g = a), this.x !== undefined && (a = this.x, b = this.y, e = this.w, f = this.h, g = i(this.style, g)), g = i(d, g);
                    if (!g.stroke && !g.fill) return;
                    var h = .5522848;
                    ox = e / 2 * h, oy = f / 2 * h, xe = a + e, ye = b + f, xm = a + e / 2, ym = b + f / 2, c.save(), c.beginPath(), c.moveTo(a, ym), c.bezierCurveTo(a, ym - oy, xm - ox, b, xm, b), c.bezierCurveTo(xm + ox, b, xe, ym - oy, xe, ym), c.bezierCurveTo(xe, ym + oy, xm + ox, ye, xm, ye), c.bezierCurveTo(xm - ox, ye, a, ym + oy, a, ym), c.closePath(), g.fill !== null && (g.alpha !== undefined ? c.fillStyle = o.blend(g.fill, g.alpha) : c.fillStyle = o.encode(g.fill), c.fill()), g.stroke !== null && (c.strokeStyle = o.encode(g.stroke), isNaN(g.width) || (c.lineWidth = g.width), c.stroke()), c.restore()
                }
            };
            var g = function (a, b, c, d, e, f) {
                l(e, "stroke", "fill", "width") && (f = e, e = 0), this.x = a, this.y = b, this.w = c, this.h = d, this.r = e !== undefined ? e : 0, this.style = f !== undefined ? f : {}
            };
            g.prototype = {
                draw: function (a) {
                    this._draw(a)
                },
                _draw: function (a, b, e, f, g, h) {
                    l(g, "stroke", "fill", "width", "alpha") ? (h = g, g = 0) : l(a, "stroke", "fill", "width", "alpha") && (h = a), this.x !== undefined && (a = this.x, b = this.y, e = this.w, f = this.h, h = i(this.style, h)), h = i(d, h);
                    if (!h.stroke && !h.fill) return;
                    var j = g > 0;
                    c.save(), c.beginPath(), c.moveTo(a + g, b), c.lineTo(a + e - g, b), j && c.quadraticCurveTo(a + e, b, a + e, b + g), c.lineTo(a + e, b + f - g), j && c.quadraticCurveTo(a + e, b + f, a + e - g, b + f), c.lineTo(a + g, b + f), j && c.quadraticCurveTo(a, b + f, a, b + f - g), c.lineTo(a, b + g), j && c.quadraticCurveTo(a, b, a + g, b), h.fill !== null && (h.alpha !== undefined ? c.fillStyle = o.blend(h.fill, h.alpha) : c.fillStyle = o.encode(h.fill), c.fill()), h.stroke !== null && (c.strokeStyle = o.encode(h.stroke), isNaN(h.width) || (c.lineWidth = h.width), c.stroke()), c.restore()
                }
            };
            var h = function (b, c, d, e, f) {
                f !== undefined || typeof e == "number" ? (this.points = [{
                    x: b,
                    y: c
                }, {
                    x: d,
                    y: e
                }], this.style = f || {}) : a.isArray(b) ? (this.points = b, this.style = c || {}) : (this.points = [b, c], this.style = d || {})
            };
            h.prototype = {
                draw: function (b) {
                    if (this.points.length < 2) return;
                    var e = [];
                    a.isArray(this.points[0]) ? e = this.points : e.push(this.points), c.save(), c.beginPath(), a.each(e, function (b, d) {
                        c.moveTo(d[0].x + .5, d[0].y + .5), a.each(d, function (a, b) {
                            if (a == 0) return;
                            c.lineTo(b.x + .5, b.y + .5)
                        })
                    });
                    var f = a.extend(i(d, this.style), b);
                    f.closed && c.closePath();
                    if (f.fill !== undefined) {
                        var g = o.decode(f.fill, f.alpha !== undefined ? f.alpha : 1);
                        g && (c.fillStyle = o.encode(g)), c.fill()
                    }
                    if (f.stroke !== undefined) {
                        var h = o.decode(f.stroke, f.alpha !== undefined ? f.alpha : 1);
                        h && (c.strokeStyle = o.encode(h)), isNaN(f.width) || (c.lineWidth = f.width), c.stroke()
                    }
                    c.restore()
                }
            };
            var j = function (a, b, c, d) {
                var e = o.decode(a, b, c, d);
                e && (this.r = e.r, this.g = e.g, this.b = e.b, this.a = e.a)
            };
            return j.prototype = {
                toString: function () {
                    return o.encode(this)
                },
                blend: function () {
                    b("blend", this.r, this.g, this.b, this.a)
                }
            }, {
                _Oval: f,
                _Rect: g,
                _Color: j,
                _Path: h
            }
        }, q = function (b) {
            var c = a(b),
                d = a(c).get(0).getContext("2d"),
                e = null,
                f = "rgb",
                j = "origin",
                k = {}, l = {
                    background: null,
                    fill: null,
                    stroke: null,
                    width: 0
                }, m = {}, n = {
                    font: "sans-serif",
                    size: 12,
                    align: "left",
                    color: o.decode("black"),
                    alpha: 1,
                    baseline: "ideographic"
                }, q = [],
                r = p(d, l, n),
                s = r._Oval,
                t = r._Rect,
                u = r._Color,
                v = r._Path,
                w = {
                    init: function () {
                        return d ? w : null
                    },
                    size: function (a, b) {
                        return !isNaN(a) && !isNaN(b) && c.attr({
                            width: a,
                            height: b
                        }), {
                            width: c.attr("width"),
                            height: c.attr("height")
                        }
                    },
                    clear: function (a, b, e, f) {
                        arguments.length < 4 && (a = 0, b = 0, e = c.attr("width"), f = c.attr("height")), d.clearRect(a, b, e, f), l.background !== null && (d.save(), d.fillStyle = o.encode(l.background), d.fillRect(a, b, e, f), d.restore())
                    },
                    background: function (a, b, c, d) {
                        if (a == null) return l.background = null, null;
                        var e = o.decode(a, b, c, d);
                        e && (l.background = e, w.clear())
                    },
                    noFill: function () {
                        l.fill = null
                    },
                    fill: function (a, b, c, e) {
                        if (arguments.length == 0) return l.fill;
                        if (arguments.length > 0) {
                            var f = o.decode(a, b, c, e);
                            l.fill = f, d.fillStyle = o.encode(f)
                        }
                    },
                    noStroke: function () {
                        l.stroke = null, d.strokeStyle = null
                    },
                    stroke: function (a, b, c, e) {
                        if (arguments.length == 0 && l.stroke !== null) return l.stroke;
                        if (arguments.length > 0) {
                            var f = o.decode(a, b, c, e);
                            l.stroke = f, d.strokeStyle = o.encode(f)
                        }
                    },
                    strokeWidth: function (a) {
                        if (a === undefined) return d.lineWidth;
                        d.lineWidth = l.width = a
                    },
                    Color: function (a) {
                        return new u(a)
                    },
                    drawStyle: function (b) {
                        if (arguments.length == 0) return h(l);
                        if (arguments.length == 2) {
                            var c = arguments[0],
                                e = arguments[1];
                            if (typeof c == "string" && typeof e == "object") {
                                var f = {};
                                if (e.color !== undefined) {
                                    var g = o.decode(e.color);
                                    g && (f.color = g)
                                }
                                a.each("background fill stroke width".split(" "), function (a, b) {
                                    e[b] !== undefined && (f[b] = e[b])
                                }), a.isEmptyObject(f) || (k[c] = f)
                            }
                            return
                        }
                        arguments.length == 1 && k[arguments[0]] !== undefined && (b = k[arguments[0]]), b.width !== undefined && (l.width = b.width), d.lineWidth = l.width, a.each("background fill stroke", function (a, c) {
                            if (b[c] !== undefined) if (b[c] === null) l[c] = null;
                            else {
                                var d = o.decode(b[c]);
                                d && (l[c] = d)
                            }
                        }), d.fillStyle = l.fill, d.strokeStyle = l.stroke
                    },
                    textStyle: function (b) {
                        if (arguments.length == 0) return h(n);
                        if (arguments.length == 2) {
                            var c = arguments[0],
                                e = arguments[1];
                            if (typeof c == "string" && typeof e == "object") {
                                var f = {};
                                if (e.color !== undefined) {
                                    var i = o.decode(e.color);
                                    i && (f.color = i)
                                }
                                a.each("font size align baseline alpha".split(" "), function (a, b) {
                                    e[b] !== undefined && (f[b] = e[b])
                                }), a.isEmptyObject(f) || (m[c] = f)
                            }
                            return
                        }
                        arguments.length == 1 && m[arguments[0]] !== undefined && (b = m[arguments[0]]), b.font !== undefined && (n.font = b.font), b.size !== undefined && (n.size = b.size), d.font = g("{size}px {font}", n), b.align !== undefined && (d.textAlign = n.align = b.align), b.baseline !== undefined && (d.textBaseline = n.baseline = b.baseline), b.alpha !== undefined && (n.alpha = b.alpha);
                        if (b.color !== undefined) {
                            var i = o.decode(b.color);
                            i && (n.color = i)
                        }
                        if (n.color) {
                            var i = o.blend(n.color, n.alpha);
                            i && (d.fillStyle = i)
                        }
                    },
                    text: function (a, b, c, e) {
                        arguments.length >= 3 && !isNaN(b) ? (e = e || {}, e.x = b, e.y = c) : arguments.length == 2 && typeof b == "object" ? e = b : e = e || {};
                        var f = i(n, e);
                        d.save(), f.align !== undefined && (d.textAlign = f.align), f.baseline !== undefined && (d.textBaseline = f.baseline), f.font !== undefined && !isNaN(f.size) && (d.font = g("{size}px {font}", f));
                        var h = f.alpha !== undefined ? f.alpha : n.alpha,
                            j = f.color !== undefined ? f.color : n.color;
                        d.fillStyle = o.blend(j, h), h > 0 && d.fillText(a, Math.round(f.x), f.y), d.restore()
                    },
                    textWidth: function (a, b) {
                        b = i(n, b || {}), d.save(), d.font = g("{size}px {font}", b);
                        var c = d.measureText(a).width;
                        return d.restore(), c
                    },
                    Rect: function (a, b, c, d, e, f) {
                        return new t(a, b, c, d, e, f)
                    },
                    rect: function (a, b, c, d, e, f) {
                        t.prototype._draw(a, b, c, d, e, f)
                    },
                    Oval: function (a, b, c, d, e) {
                        return new s(a, b, c, d, e)
                    },
                    oval: function (a, b, c, d, e) {
                        e = e || {}, s.prototype._draw(a, b, c, d, e)
                    },
                    line: function (a, b, c, d, e) {
                        var f = new v(a, b, c, d);
                        f.draw(e)
                    },
                    lines: function (a, b, c, d) {
                        typeof d == "number" ? q.push([{
                            x: a,
                            y: b
                        }, {
                            x: c,
                            y: d
                        }]) : q.push([a, b])
                    },
                    drawLines: function (a) {
                        var b = new v(q);
                        b.draw(a), q = []
                    }
                };
            return w.init()
        };
    arbor = typeof arbor != "undefined" ? arbor : {}, a.extend(arbor, {
        Graphics: function (a) {
            return q(a)
        },
        colors: {
            CSS: o.CSS,
            validate: o.validate,
            decode: o.decode,
            encode: o.encode,
            blend: o.blend
        }
    })
}(this.jQuery), define("lib/arbor-graphics", function () {}),
function (a) {
    var b = function (a) {
        if (typeof window == "undefined" || !window.console) return;
        var b = arguments.length,
            c = [];
        for (var d = 0; d < b; d++) c.push(arguments[d]);
        try {
            console.log.apply(this, c)
        } catch (e) {}
    }, c = function (a) {
        var b = a.replace(/^\/?(.*?)\/?$/, "$1").split("/");
        return b.pop(), "/" + b.join("/")
    }, d = function (a) {
        var b = a.replace(/^\/?(.*?)\/?$/, "$1").split("/"),
            c = b.pop();
        return c == "" ? null : c
    }, e = /(\d)(?=(\d\d\d)+(?!\d))/g,
        f = function (a) {
            var b = "" + a;
            return a < 11e3 ? b = ("" + a).replace(e, "$1,") : a < 1e6 ? b = Math.floor(a / 1e3) + "k" : a < 1e9 && (b = ("" + Math.floor(a / 1e3)).replace(e, "$1,") + "m"), b
        }, g = function (b, c) {
            return b.replace(/\{([\w\-\.]*)}/g, function (b, d) {
                var e = d.split("."),
                    f = c[e.shift()];
                return a.each(e, function () {
                    f.hasOwnProperty(this) ? f = f[this] : f = b
                }), f
            })
        }, h = function (b) {
            if (b === undefined) return undefined;
            if (b === null) return null;
            if (b.parentNode) return b;
            switch (typeof b) {
                case "string":
                    return b.substring(0);
                case "number":
                    return b + 0;
                case "boolean":
                    return b === !0
            }
            var c = a.isArray(b) ? [] : {};
            return a.each(b, function (a, b) {
                c[a] = h(b)
            }), c
        }, i = function (a, b) {
            a = a || {}, b = b || {};
            var c = h(a);
            for (var d in b) c[d] = b[d];
            return c
        }, j = function (b, c, d) {
            if (!b || !c) return b === c;
            if (typeof b != typeof c) return !1;
            if (typeof b != "object") return b === c;
            if (a.isArray(b)) {
                if (!a.isArray(c)) return !1;
                if (b.length != c.length) return !1
            } else {
                var e = [];
                for (var f in b) b.hasOwnProperty(f) && e.push(f);
                var g = [];
                for (var f in c) c.hasOwnProperty(f) && g.push(f);
                d || (e.sort(), g.sort());
                if (e.join(",") !== g.join(",")) return !1
            }
            var h = !0;
            return a.each(b, function (a) {
                var d = j(b[a], c[a]);
                h = h && d;
                if (!h) return !1
            }), h
        }, k = function (b) {
            var c = [];
            return a.each(b, function (a, d) {
                b.hasOwnProperty(a) && c.push(a)
            }), c
        }, l = function (a) {
            if (!a || typeof a != "object") return !1;
            for (var b = 1, c = arguments.length; b < c; b++) if (a.hasOwnProperty(arguments[b])) return !0;
            return !1
        }, m = function (a) {
            var b = a.length,
                c = {};
            for (var d = 0; d < b; d++) c[a[d]] = !0;
            return k(c)
        }, n = function () {
            var b = a("script").map(function (b) {
                var c = a(this).attr("src");
                if (!c) return;
                if (c.match(/arbor[^\/\.]*.js|dev.js/)) return c.match(/.*\//) || "/"
            });
            return b.length > 0 ? b[0] : null
        }, o = function () {
            var a = /#[0-9a-f]{6}/i,
                b = /#(..)(..)(..)/,
                c = function (a) {
                    var b = a.toString(16);
                    return b.length == 2 ? b : "0" + b
                }, d = function (a) {
                    return parseInt(a, 16)
                }, e = function (a) {
                    if (!a || typeof a != "object") return !1;
                    var b = k(a).sort().join("");
                    if (b == "abgr") return !0
                }, f = {
                    CSS: {
                        aliceblue: "#f0f8ff",
                        antiquewhite: "#faebd7",
                        aqua: "#00ffff",
                        aquamarine: "#7fffd4",
                        azure: "#f0ffff",
                        beige: "#f5f5dc",
                        bisque: "#ffe4c4",
                        black: "#000000",
                        blanchedalmond: "#ffebcd",
                        blue: "#0000ff",
                        blueviolet: "#8a2be2",
                        brown: "#a52a2a",
                        burlywood: "#deb887",
                        cadetblue: "#5f9ea0",
                        chartreuse: "#7fff00",
                        chocolate: "#d2691e",
                        coral: "#ff7f50",
                        cornflowerblue: "#6495ed",
                        cornsilk: "#fff8dc",
                        crimson: "#dc143c",
                        cyan: "#00ffff",
                        darkblue: "#00008b",
                        darkcyan: "#008b8b",
                        darkgoldenrod: "#b8860b",
                        darkgray: "#a9a9a9",
                        darkgrey: "#a9a9a9",
                        darkgreen: "#006400",
                        darkkhaki: "#bdb76b",
                        darkmagenta: "#8b008b",
                        darkolivegreen: "#556b2f",
                        darkorange: "#ff8c00",
                        darkorchid: "#9932cc",
                        darkred: "#8b0000",
                        darksalmon: "#e9967a",
                        darkseagreen: "#8fbc8f",
                        darkslateblue: "#483d8b",
                        darkslategray: "#2f4f4f",
                        darkslategrey: "#2f4f4f",
                        darkturquoise: "#00ced1",
                        darkviolet: "#9400d3",
                        deeppink: "#ff1493",
                        deepskyblue: "#00bfff",
                        dimgray: "#696969",
                        dimgrey: "#696969",
                        dodgerblue: "#1e90ff",
                        firebrick: "#b22222",
                        floralwhite: "#fffaf0",
                        forestgreen: "#228b22",
                        fuchsia: "#ff00ff",
                        gainsboro: "#dcdcdc",
                        ghostwhite: "#f8f8ff",
                        gold: "#ffd700",
                        goldenrod: "#daa520",
                        gray: "#808080",
                        grey: "#808080",
                        green: "#008000",
                        greenyellow: "#adff2f",
                        honeydew: "#f0fff0",
                        hotpink: "#ff69b4",
                        indianred: "#cd5c5c",
                        indigo: "#4b0082",
                        ivory: "#fffff0",
                        khaki: "#f0e68c",
                        lavender: "#e6e6fa",
                        lavenderblush: "#fff0f5",
                        lawngreen: "#7cfc00",
                        lemonchiffon: "#fffacd",
                        lightblue: "#add8e6",
                        lightcoral: "#f08080",
                        lightcyan: "#e0ffff",
                        lightgoldenrodyellow: "#fafad2",
                        lightgray: "#d3d3d3",
                        lightgrey: "#d3d3d3",
                        lightgreen: "#90ee90",
                        lightpink: "#ffb6c1",
                        lightsalmon: "#ffa07a",
                        lightseagreen: "#20b2aa",
                        lightskyblue: "#87cefa",
                        lightslategray: "#778899",
                        lightslategrey: "#778899",
                        lightsteelblue: "#b0c4de",
                        lightyellow: "#ffffe0",
                        lime: "#00ff00",
                        limegreen: "#32cd32",
                        linen: "#faf0e6",
                        magenta: "#ff00ff",
                        maroon: "#800000",
                        mediumaquamarine: "#66cdaa",
                        mediumblue: "#0000cd",
                        mediumorchid: "#ba55d3",
                        mediumpurple: "#9370d8",
                        mediumseagreen: "#3cb371",
                        mediumslateblue: "#7b68ee",
                        mediumspringgreen: "#00fa9a",
                        mediumturquoise: "#48d1cc",
                        mediumvioletred: "#c71585",
                        midnightblue: "#191970",
                        mintcream: "#f5fffa",
                        mistyrose: "#ffe4e1",
                        moccasin: "#ffe4b5",
                        navajowhite: "#ffdead",
                        navy: "#000080",
                        oldlace: "#fdf5e6",
                        olive: "#808000",
                        olivedrab: "#6b8e23",
                        orange: "#ffa500",
                        orangered: "#ff4500",
                        orchid: "#da70d6",
                        palegoldenrod: "#eee8aa",
                        palegreen: "#98fb98",
                        paleturquoise: "#afeeee",
                        palevioletred: "#d87093",
                        papayawhip: "#ffefd5",
                        peachpuff: "#ffdab9",
                        peru: "#cd853f",
                        pink: "#ffc0cb",
                        plum: "#dda0dd",
                        powderblue: "#b0e0e6",
                        purple: "#800080",
                        red: "#ff0000",
                        rosybrown: "#bc8f8f",
                        royalblue: "#4169e1",
                        saddlebrown: "#8b4513",
                        salmon: "#fa8072",
                        sandybrown: "#f4a460",
                        seagreen: "#2e8b57",
                        seashell: "#fff5ee",
                        sienna: "#a0522d",
                        silver: "#c0c0c0",
                        skyblue: "#87ceeb",
                        slateblue: "#6a5acd",
                        slategray: "#708090",
                        slategrey: "#708090",
                        snow: "#fffafa",
                        springgreen: "#00ff7f",
                        steelblue: "#4682b4",
                        tan: "#d2b48c",
                        teal: "#008080",
                        thistle: "#d8bfd8",
                        tomato: "#ff6347",
                        turquoise: "#40e0d0",
                        violet: "#ee82ee",
                        wheat: "#f5deb3",
                        white: "#ffffff",
                        whitesmoke: "#f5f5f5",
                        yellow: "#ffff00",
                        yellowgreen: "#9acd32"
                    },
                    decode: function (c) {
                        var g = arguments.length;
                        for (var h = g - 1; h >= 0; h--) arguments[h] === undefined && g--;
                        var i = arguments;
                        if (!c) return null;
                        if (g == 1 && e(c)) return c;
                        var j = null;
                        if (typeof c == "string") {
                            var k = 1;
                            g == 2 && (k = i[1]);
                            var l = f.CSS[c.toLowerCase()];
                            l !== undefined && (c = l);
                            var m = c.match(a);
                            if (m) {
                                vals = c.match(b);
                                if (!vals || !vals.length || vals.length != 4) return null;
                                j = {
                                    r: d(vals[1]),
                                    g: d(vals[2]),
                                    b: d(vals[3]),
                                    a: k
                                }
                            }
                        } else typeof c == "number" && (g < 3 ? g >= 1 && (j = {
                            r: i[0],
                            g: i[0],
                            b: i[0],
                            a: 1
                        }, g == 2 && (j.a *= i[1])) : (j = {
                            r: i[0],
                            g: i[1],
                            b: i[2],
                            a: 1
                        }, g >= 4 && (j.a *= i[3])));
                        return j
                    },
                    validate: function (b) {
                        return !b || typeof b != "string" ? !1 : f.CSS[b.toLowerCase()] !== undefined ? !0 : b.match(a) ? !0 : !1
                    },
                    mix: function (a, b, c) {
                        var d = f.decode(a),
                            e = f.decode(b)
                    },
                    blend: function (a, b) {
                        b = b !== undefined ? Math.max(0, Math.min(1, b)) : 1;
                        var c = f.decode(a);
                        if (!c) return null;
                        if (b == 1) return a;
                        var c = a;
                        typeof a == "string" && (c = f.decode(a));
                        var d = h(c);
                        return d.a *= b, g("rgba({r},{g},{b},{a})", d)
                    },
                    encode: function (a) {
                        if (!e(a)) {
                            a = f.decode(a);
                            if (!e(a)) return null
                        }
                        return a.a == 1 ? g("#{r}{g}{b}", {
                            r: c(a.r),
                            g: c(a.g),
                            b: c(a.b)
                        }) : g("rgba({r},{g},{b},{a})", a)
                    }
                };
            return f
        }(),
        p = function () {
            var a = {
                linear: function (a, b, c, d) {
                    return c * (a / d) + b
                },
                quadin: function (a, b, c, d) {
                    return c * (a /= d) * a + b
                },
                quadout: function (a, b, c, d) {
                    return -c * (a /= d) * (a - 2) + b
                },
                quadinout: function (a, b, c, d) {
                    return (a /= d / 2) < 1 ? c / 2 * a * a + b : -c / 2 * (--a * (a - 2) - 1) + b
                },
                cubicin: function (a, b, c, d) {
                    return c * (a /= d) * a * a + b
                },
                cubicout: function (a, b, c, d) {
                    return c * ((a = a / d - 1) * a * a + 1) + b
                },
                cubicinout: function (a, b, c, d) {
                    return (a /= d / 2) < 1 ? c / 2 * a * a * a + b : c / 2 * ((a -= 2) * a * a + 2) + b
                },
                quartin: function (a, b, c, d) {
                    return c * (a /= d) * a * a * a + b
                },
                quartout: function (a, b, c, d) {
                    return -c * ((a = a / d - 1) * a * a * a - 1) + b
                },
                quartinout: function (a, b, c, d) {
                    return (a /= d / 2) < 1 ? c / 2 * a * a * a * a + b : -c / 2 * ((a -= 2) * a * a * a - 2) + b
                },
                quintin: function (a, b, c, d) {
                    return c * (a /= d) * a * a * a * a + b
                },
                quintout: function (a, b, c, d) {
                    return c * ((a = a / d - 1) * a * a * a * a + 1) + b
                },
                quintinout: function (a, b, c, d) {
                    return (a /= d / 2) < 1 ? c / 2 * a * a * a * a * a + b : c / 2 * ((a -= 2) * a * a * a * a + 2) + b
                },
                sinein: function (a, b, c, d) {
                    return -c * Math.cos(a / d * (Math.PI / 2)) + c + b
                },
                sineout: function (a, b, c, d) {
                    return c * Math.sin(a / d * (Math.PI / 2)) + b
                },
                sineinout: function (a, b, c, d) {
                    return -c / 2 * (Math.cos(Math.PI * a / d) - 1) + b
                },
                expoin: function (a, b, c, d) {
                    return a == 0 ? b : c * Math.pow(2, 10 * (a / d - 1)) + b
                },
                expoout: function (a, b, c, d) {
                    return a == d ? b + c : c * (-Math.pow(2, -10 * a / d) + 1) + b
                },
                expoinout: function (a, b, c, d) {
                    return a == 0 ? b : a == d ? b + c : (a /= d / 2) < 1 ? c / 2 * Math.pow(2, 10 * (a - 1)) + b : c / 2 * (-Math.pow(2, -10 * --a) + 2) + b
                },
                circin: function (a, b, c, d) {
                    return -c * (Math.sqrt(1 - (a /= d) * a) - 1) + b
                },
                circout: function (a, b, c, d) {
                    return c * Math.sqrt(1 - (a = a / d - 1) * a) + b
                },
                circinout: function (a, b, c, d) {
                    return (a /= d / 2) < 1 ? -c / 2 * (Math.sqrt(1 - a * a) - 1) + b : c / 2 * (Math.sqrt(1 - (a -= 2) * a) + 1) + b
                },
                elasticin: function (a, b, c, d) {
                    var e = 1.70158,
                        f = 0,
                        g = c;
                    if (a == 0) return b;
                    if ((a /= d) == 1) return b + c;
                    f || (f = d * .3);
                    if (g < Math.abs(c)) {
                        g = c;
                        var e = f / 4
                    } else var e = f / (2 * Math.PI) * Math.asin(c / g);
                    return -(g * Math.pow(2, 10 * (a -= 1)) * Math.sin((a * d - e) * 2 * Math.PI / f)) + b
                },
                elasticout: function (a, b, c, d) {
                    var e = 1.70158,
                        f = 0,
                        g = c;
                    if (a == 0) return b;
                    if ((a /= d) == 1) return b + c;
                    f || (f = d * .3);
                    if (g < Math.abs(c)) {
                        g = c;
                        var e = f / 4
                    } else var e = f / (2 * Math.PI) * Math.asin(c / g);
                    return g * Math.pow(2, -10 * a) * Math.sin((a * d - e) * 2 * Math.PI / f) + c + b
                },
                elasticinout: function (a, b, c, d) {
                    var e = 1.70158,
                        f = 0,
                        g = c;
                    if (a == 0) return b;
                    if ((a /= d / 2) == 2) return b + c;
                    f || (f = d * .3 * 1.5);
                    if (g < Math.abs(c)) {
                        g = c;
                        var e = f / 4
                    } else var e = f / (2 * Math.PI) * Math.asin(c / g);
                    return a < 1 ? -0.5 * g * Math.pow(2, 10 * (a -= 1)) * Math.sin((a * d - e) * 2 * Math.PI / f) + b : g * Math.pow(2, -10 * (a -= 1)) * Math.sin((a * d - e) * 2 * Math.PI / f) * .5 + c + b
                },
                backin: function (a, b, c, d, e) {
                    return e == undefined && (e = 1.70158), c * (a /= d) * a * ((e + 1) * a - e) + b
                },
                backout: function (a, b, c, d, e) {
                    return e == undefined && (e = 1.70158), c * ((a = a / d - 1) * a * ((e + 1) * a + e) + 1) + b
                },
                backinout: function (a, b, c, d, e) {
                    return e == undefined && (e = 1.70158), (a /= d / 2) < 1 ? c / 2 * a * a * (((e *= 1.525) + 1) * a - e) + b : c / 2 * ((a -= 2) * a * (((e *= 1.525) + 1) * a + e) + 2) + b
                },
                bouncein: function (b, c, d, e) {
                    return d - a.bounceOut(e - b, 0, d, e) + c
                },
                bounceout: function (a, b, c, d) {
                    return (a /= d) < 1 / 2.75 ? c * 7.5625 * a * a + b : a < 2 / 2.75 ? c * (7.5625 * (a -= 1.5 / 2.75) * a + .75) + b : a < 2.5 / 2.75 ? c * (7.5625 * (a -= 2.25 / 2.75) * a + .9375) + b : c * (7.5625 * (a -= 2.625 / 2.75) * a + .984375) + b
                },
                bounceinout: function (b, c, d, e) {
                    return b < e / 2 ? a.bounceIn(b * 2, 0, d, e) * .5 + c : a.bounceOut(b * 2 - e, 0, d, e) * .5 + d * .5 + c
                }
            };
            return a
        }(),
        q = function () {
            var b = {}, c = !0,
                d = {
                    init: function () {
                        return d
                    },
                    busy: function () {
                        var a = !1;
                        for (var c in b) {
                            a = !0;
                            break
                        }
                        return a
                    },
                    to: function (d, e, f) {
                        var g = (new Date).valueOf(),
                            h = {}, i = {
                                from: {},
                                to: {},
                                colors: {},
                                node: d,
                                t0: g,
                                t1: g + e * 1e3,
                                dur: e * 1e3
                            }, j = "linear";
                        for (var k in f) {
                            if (k == "easing") {
                                var l = f[k].toLowerCase();
                                l in p && (j = l);
                                continue
                            }
                            if (k == "delay") {
                                var m = (f[k] || 0) * 1e3;
                                i.t0 += m, i.t1 += m;
                                continue
                            }
                            o.validate(f[k]) ? (i.colors[k] = [o.decode(d.data[k]), o.decode(f[k]), f[k]], h[k] = !0) : (i.from[k] = d.data[k] != undefined ? d.data[k] : f[k], i.to[k] = f[k], h[k] = !0)
                        }
                        i.ease = p[j], b[d._id] === undefined && (b[d._id] = []), b[d._id].push(i);
                        if (b.length > 1) for (var n = b.length - 2; n >= 0; n++) {
                            var q = b[n];
                            for (var k in q.to) k in h ? delete q.to[k] : h[k] = !0;
                            for (var k in q.colors) k in h ? delete q.colors[k] : h[k] = !0;
                            a.isEmptyObject(q.colors) && a.isEmptyObject(q.to) && b.splice(n, 1)
                        }
                        c = !1
                    },
                    interpolate: function (a, b, c, d) {
                        d = (d || "").toLowerCase();
                        var e = p.linear;
                        d in p && (e = p[d]);
                        var f = e(a, 0, 1, 1);
                        if (o.validate(b) && o.validate(c)) return s(f, b, c);
                        if (!isNaN(b)) return r(f, b, c);
                        if (typeof b == "string") return f < .5 ? b : c
                    },
                    tick: function () {
                        var d = !0;
                        for (var e in b) {
                            d = !1;
                            break
                        }
                        if (d) return;
                        var f = (new Date).valueOf();
                        return a.each(b, function (c, d) {
                            var e = !1;
                            a.each(d, function (a, b) {
                                var c = b.ease(f - b.t0, 0, 1, b.dur);
                                c = Math.min(1, c);
                                var d = b.from,
                                    g = b.to,
                                    h = b.colors,
                                    i = b.node.data,
                                    j = c == 1;
                                for (var k in g) switch (typeof g[k]) {
                                    case "number":
                                        i[k] = r(c, d[k], g[k]), k == "alpha" && (i[k] = Math.max(0, Math.min(1, i[k])));
                                        break;
                                    case "string":
                                        j && (i[k] = g[k])
                                }
                                for (var k in h) if (j) i[k] = h[k][2];
                                else {
                                    var l = s(c, h[k][0], h[k][1]);
                                    i[k] = o.encode(l)
                                }
                                j && (b.completed = !0, e = !0)
                            }), e && (b[c] = a.map(d, function (a) {
                                if (!a.completed) return a
                            }), b[c].length == 0 && delete b[c])
                        }), c = a.isEmptyObject(b), c
                    }
                };
            return d.init()
        }, r = function (a, b, c) {
            return b + a * (c - b)
        }, s = function (b, c, d) {
            b = Math.max(Math.min(b, 1), 0);
            var e = {};
            return a.each("rgba".split(""), function (a, f) {
                e[f] = Math.round(c[f] + b * (d[f] - c[f]))
            }), e
        };
    arbor = typeof arbor != "undefined" ? arbor : {}, a.extend(arbor, {
        Tween: q,
        colors: {
            CSS: o.CSS,
            validate: o.validate,
            decode: o.decode,
            encode: o.encode,
            blend: o.blend
        }
    })
}(this.jQuery), define("lib/arbor-tween", function () {}),
function () {
    define("lib/amd/arbor", ["order!lib/amd/jQuery", "order!lib/arbor", "order!lib/arbor-graphics", "order!lib/arbor-tween"], function () {
        return arbor
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/visualization/Renderer", ["lib/amd/arbor", "lib/amd/Backbone", "lib/amd/jQuery"], function (b, c, d) {
        var e;
        return e = function () {
            function e(e, f) {
                this.relationshipStyler = f, this.intersect_line_box = a(this.intersect_line_box, this), this.intersect_line_line = a(this.intersect_line_line, this), this.thesePointsAreReallyClose = a(this.thesePointsAreReallyClose, this), this.ghostify = a(this.ghostify, this), this.ptInBox = a(this.ptInBox, this), this.intersectingNode = a(this.intersectingNode, this), this.nodeDropped = a(this.nodeDropped, this), this.nodeDragged = a(this.nodeDragged, this), this.clicked = a(this.clicked, this), this.stop = a(this.stop, this), this.start = a(this.start, this), this.initMouseHandling = a(this.initMouseHandling, this), this.renderEdge = a(this.renderEdge, this), this.renderNode = a(this.renderNode, this), this.redraw = a(this.redraw, this), this.init = a(this.init, this), this.canvas = d(e).get(0), this.bgColor = "#EEEEEE", this.canvas.getContext || (this.canvas = window.G_vmlCanvasManager.initElement(this.canvas)), this.ctx = this.canvas.getContext("2d"), this.gfx = b.Graphics(this.canvas), _.extend(this, c.Events)
            }
            return e.prototype.init = function (a) {
                return this.particleSystem = a, this.particleSystem.screenSize(this.canvas.width, this.canvas.height), this.particleSystem.screenStep(0), this.particleSystem.screenPadding(40), this.initMouseHandling()
            }, e.prototype.redraw = function () {
                if (!this.particleSystem || this.stopped === !0) return;
                return this.gfx.clear(), this.nodeBoxes = {}, this.particleSystem.eachNode(this.renderNode), this.particleSystem.eachEdge(this.renderEdge)
            }, e.prototype.renderNode = function (a, b) {
                var c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
                if (a.data.hidden === !0) return;
                p = a.data.style, m = [], g = p.labelStyle.size, n = Math.ceil(g + g / 5), this.ctx.font = "" + g + "px " + p.labelStyle.font, r = g, h = Math.floor(g - g / 5), x = ("" + p.labelText).split(";");
                for (t = 0, v = x.length; t < v; t++) k = x[t], l = this.ctx.measureText("" + k), l.width + g > r && (r = l.width + g), h += g, m.push(k);
                m.length > 0 ? (b.x = Math.floor(b.x), b.y = Math.floor(b.y)) : m = null, o = p.shapeStyle;
                if (this.hovered && a._id === this.hovered._id) {
                    o = {}, y = p.shapeStyle;
                    for (j in y) q = y[j], o[j] = q;
                    o.stroke = {
                        r: 255,
                        g: 0,
                        b: 0,
                        a: a.data.alpha
                    }
                }
                if (o.shape === "dot") e = r > h ? r : h, this.gfx.oval(b.x - e / 2, b.y - e / 2, e, e, o), this.nodeBoxes[a.name] = [b.x - e / 2, b.y - e / 2, e, e];
                else if (o.shape === "icon") {
                    i = p.icon, c = b.x - i.width / 2, d = b.y - i.height / 2;
                    try {
                        i = p.icon, this.ctx.drawImage(i, c, d), o.alpha != null && (f = {
                            alpha: 1 - o.alpha,
                            fill: this.bgColor
                        }, this.gfx.rect(c, d, i.width, i.height, 0, f))
                    } catch (A) {}
                    this.nodeBoxes[a.name] = [c, d, i.width, i.height]
                } else this.gfx.rect(b.x - r / 2, b.y - h / 2, r, h, 4, o), this.nodeBoxes[a.name] = [b.x - r / 2, b.y - h / 2, r, h];
                if (m) {
                    this.ctx.textAlign = "center", this.ctx.fillStyle = p.labelStyle.color, p.shapeStyle.shape === "icon" ? s = h + 2 : s = h / -2 + n, z = [];
                    for (u = 0, w = m.length; u < w; u++) k = m[u], this.ctx.fillText(k || "", b.x, b.y + s), z.push(s += n);
                    return z
                }
            }, e.prototype.renderEdge = function (a, b, c) {
                var d, e, f, g, h, i, j;
                if (a.data.hidden === !0) return;
                h = this.relationshipStyler.getStyleFor(a), i = this.intersect_line_box(b, c, this.nodeBoxes[a.source.name]);
                if (i === !1) return;
                g = this.intersect_line_box(i, c, this.nodeBoxes[a.target.name]);
                if (g === !1) return;
                this.ctx.save(), this.ctx.beginPath(), this.ctx.lineWidth = h.edgeStyle.width, this.ctx.strokeStyle = h.edgeStyle.color, this.ctx.fillStyle = "rgba(0, 0, 0, 0)", this.ctx.moveTo(i.x, i.y), this.ctx.lineTo(g.x, g.y), this.ctx.stroke(), this.ctx.restore(), a.data.directed && (this.ctx.save(), j = h.edgeStyle.width, d = 6 + j, e = 2 + j, this.ctx.fillStyle = h.edgeStyle.color, this.ctx.translate(g.x, g.y), this.ctx.rotate(Math.atan2(g.y - i.y, g.x - i.x)), this.ctx.clearRect(-d / 2, -j / 2, d / 2, j), this.ctx.beginPath(), this.ctx.moveTo(-d, e), this.ctx.lineTo(0, 0), this.ctx.lineTo(-d, -e), this.ctx.lineTo(-d * .8, 0), this.ctx.closePath(), this.ctx.fill(), this.ctx.restore());
                if (h.labelText) return this.ctx.save(), this.ctx.font = h.labelStyle.font, this.ctx.translate(g.x, g.y), f = g.x - i.x, f < 0 ? (this.ctx.textAlign = "left", this.ctx.rotate(Math.atan2(g.y - i.y, f) - Math.PI), this.ctx.translate(20, h.edgeStyle.width - 5)) : (this.ctx.textAlign = "right", this.ctx.rotate(Math.atan2(g.y - i.y, f)), this.ctx.translate(-20, h.edgeStyle.width - 5)), this.ctx.fillStyle = h.labelStyle.color, this.ctx.fillText(h.labelText || "", 0, 0), this.ctx.restore()
            }, e.prototype.initMouseHandling = function () {
                return this.selected = null, this.nearest = null, this.dragged = null, this.hovered = null, d(this.canvas).mousedown(this.clicked)
            }, e.prototype.start = function () {
                return this.stopped = !1
            }, e.prototype.stop = function () {
                return this.stopped = !0
            }, e.prototype.clicked = function (a) {
                var c, e;
                return e = d(this.canvas).offset(), this.dragStart = {
                    x: a.pageX,
                    y: a.pageY
                }, c = b.Point(a.pageX - e.left, a.pageY - e.top), this.selected = this.nearest = this.dragged = this.particleSystem.nearest(c), this.dragged.node != null && (this.dragged.node.fixed = !0, this.particleSystem.eachNode(function (a, b) {
                    return a.data.flow = a.fixed, a.fixed = !0
                })), d(this.canvas).bind("mousemove", this.nodeDragged), d(window).bind("mouseup", this.nodeDropped), !1
            }, e.prototype.nodeDragged = function (a) {
                var c, e, f, g, h;
                e = this.nearest && this.nearest.node._id, g = d(this.canvas).offset(), h = b.Point(a.pageX - g.left, a.pageY - g.top), this.ghostify(this.dragged.node);
                if (!this.nearest) return;
                return this.dragged !== null && this.dragged.node !== null && (f = this.particleSystem.fromScreen(h), this.dragged.node.p = f, c = this.intersectingNode(h), c && c !== this.hovered && (c.data.alpha = 0, this.particleSystem.tweenNode(c, .25, {
                    alpha: 1
                })), this.hovered = c), !1
            }, e.prototype.nodeDropped = function (a) {
                var c, e, f;
                this.hovered = null;
                if (this.dragged === null || this.dragged.node === void 0) return;
                return this.dragged.node != null && (this.dragged.node.fixed = this.dragged.node.data.fixated), this.dragged.node.fixed = !0, this.dragged.node.mass = 1, this.dragged.node != null && this.thesePointsAreReallyClose(this.dragStart, {
                    x: a.pageX,
                    y: a.pageY
                }) && this.trigger("node:click", this.dragged.node, a), f = d(this.canvas).offset(), e = b.Point(a.pageX - f.left, a.pageY - f.top), this.particleSystem.eachNode(function (a, b) {
                    return a.fixed = a.data.flow
                }), c = this.intersectingNode(e), c && this.trigger("node:dropped", this.dragged.node, c, a), this.particleSystem.start(), this.dragged = null, this.selected = null, d(this.canvas).unbind("mousemove", this.nodeDragged), d(window).unbind("mouseup", this.nodeDropped), !1
            }, e.prototype.intersectingNode = function (a) {
                var b, c;
                c = {
                    node: null,
                    distance: null
                }, b = this.dragged.node, this.particleSystem.eachNode(function (d, e) {
                    var f;
                    if (d._id !== b._id) {
                        f = a.subtract(e).magnitude();
                        if (c.distance === null || f < c.distance) return c.node = d, c.distance = f
                    }
                });
                if (c.node != null && this.ptInBox(a, this.nodeBoxes[c.node.name])) return c.node
            }, e.prototype.ptInBox = function (a, c) {
                var d, e, f, g, h, i;
                return c != null ? (g = c[0], h = c[1], f = c[2], e = c[3], i = [f - 2, e - 2], f = i[0], e = i[1], d = a.subtract(b.Point(g, h)), Math.abs(d.x) < f && Math.abs(d.y) < e) : !1
            }, e.prototype.ghostify = function (a) {
                return a.fixed = !0
            }, e.prototype.thesePointsAreReallyClose = function (a, b) {
                return Math.abs(a.x - b.x) < 5 && Math.abs(a.y - b.y) < 5
            }, e.prototype.intersect_line_line = function (a, c, d, e) {
                var f, g, h;
                return f = (e.y - d.y) * (c.x - a.x) - (e.x - d.x) * (c.y - a.y), f === 0 ? !1 : (g = ((e.x - d.x) * (a.y - d.y) - (e.y - d.y) * (a.x - d.x)) / f, h = ((c.x - a.x) * (a.y - d.y) - (c.y - a.y) * (a.x - d.x)) / f, g < 0 || g > 1 || h < 0 || h > 1 ? !1 : b.Point(a.x + g * (c.x - a.x), a.y + g * (c.y - a.y)))
            }, e.prototype.intersect_line_box = function (a, b, c) {
                var d, e, f, g, h, i, j;
                return g = {
                    x: c[0],
                    y: c[1]
                }, j = c[2], f = c[3], h = {
                    x: g.x,
                    y: g.y
                }, i = {
                    x: g.x + j,
                    y: g.y
                }, d = {
                    x: g.x,
                    y: g.y + f
                }, e = {
                    x: g.x + j,
                    y: g.y + f
                }, this.intersect_line_line(a, b, h, i) || this.intersect_line_line(a, b, i, e) || this.intersect_line_line(a, b, e, d) || this.intersect_line_line(a, b, d, h) || !1
            }, e
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/databrowser/visualization/VisualGraph", ["./Renderer", "./RelationshipStyler", "./VisualDataModel", "./views/NodeFilterDialog", "lib/amd/arbor"], function (b, c, d, e, f) {
        var g;
        return g = function () {
            function g(e, g, h, i, j) {
                this.server = e, this.profile = g, h == null && (h = 800), i == null && (i = 400), this.groupingThreshold = j != null ? j : 10, this.detach = a(this.detach, this), this.attach = a(this.attach, this), this.start = a(this.start, this), this.stop = a(this.stop, this), this.floatNode = a(this.floatNode, this), this.reflow = a(this.reflow, this), this.nodeClicked = a(this.nodeClicked, this), this.addNodes = a(this.addNodes, this), this.addNode = a(this.addNode, this), this.setNodes = a(this.setNodes, this), this.setNode = a(this.setNode, this), this.clear = a(this.clear, this), this.steadyStateCheck = a(this.steadyStateCheck, this), this.el = $("<canvas width='" + h + "' height='" + i + "'></canvas>"), this.labelProperties = [], this.relationshipStyler = new c, this.dataModel = new d, this.sys = f.ParticleSystem(), this.sys.parameters({
                    repulsion: 10,
                    stiffness: 100,
                    friction: .5,
                    gravity: !0,
                    fps: 30,
                    dt: .015,
                    precision: .5
                }), this.stop(), this.sys.renderer = new b(this.el, this.relationshipStyler), this.sys.renderer.bind("node:click", this.nodeClicked), this.sys.renderer.bind("node:dropped", this.nodeDropped), this.sys.screenPadding(20), this.steadStateWorker = setInterval(this.steadyStateCheck, 1e3)
            }
            return g.prototype.steadyStateCheck = function () {
                var a, b;
                a = this.sys.energy();
                if (a != null) {
                    b = a.mean;
                    if (b < .01) return this.sys.stop()
                }
            }, g.prototype.clear = function () {
                return this.dataModel.clear(), this._synchronizeUiWithData()
            }, g.prototype.setNode = function (a) {
                return this.setNodes([a])
            }, g.prototype.setNodes = function (a) {
                return this.dataModel.clear(), this.addNodes(a)
            }, g.prototype.addNode = function (a) {
                return this.addNodes([a])
            }, g.prototype.addNodes = function (b) {
                var c, d, e, f, g;
                c = b.length, this.stop(), g = [];
                for (e = 0, f = b.length; e < f; e++) d = b[e], g.push(a(function (b) {
                    var d, e;
                    return d = b.getRelationships(), e = b.traverse({}), neo4j.Promise.join(d, e).then(a(function (a) {
                        var d, e;
                        e = a[0], d = a[1], this.dataModel.addNode(b, e, d);
                        if (--c === 0) return this._synchronizeUiWithData()
                    }, this))
                }, this)(d));
                return g
            }, g.prototype.nodeClicked = function (b, c) {
                var d, f, g, h, i;
                if (b.data.type != null) {
                    if (c.button === 2) return 1;
                    switch (b.data.type) {
                        case "unexplored":
                            return this.addNode(b.data.neoNode);
                        case "explored":
                            return this.dataModel.unexplore(b.data.neoNode), this._synchronizeUiWithData();
                        case "group":
                            return h = function () {
                                var a, c;
                                a = b.data.group.grouped, c = [];
                                for (i in a) g = a[i], c.push(g.node);
                                return c
                            }(), d = a(function (a, b) {
                                return b.remove(), this.dataModel.ungroup(a), this._synchronizeUiWithData()
                            }, this), f = new e(h, d), f.show()
                    }
                }
            }, g.prototype.nodeDropped = function (a, b, c) {
                return neo4j.events.trigger("ui:node:dropped", {
                    dropped: a.data.neoNode,
                    target: b.data.neoNode,
                    altKey: c.altKey,
                    ctrlKey: c.ctrlKey,
                    metaKey: c.metaKey,
                    button: c.button
                })
            }, g.prototype.reflow = function () {
                return this.sys.eachNode(this.floatNode), this.sys.parameters({
                    gravity: !0
                }), this.start()
            }, g.prototype.floatNode = function (a, b) {
                return a.fixed = !1
            }, g.prototype.stop = function () {
                return this.sys.renderer != null && this.sys.renderer.stop(), this.sys.parameters({
                    gravity: !1
                }), this.sys.stop()
            }, g.prototype.start = function () {
                return this.sys.renderer != null && this.sys.renderer.start(), this.sys.start(!0), this.sys.renderer.redraw()
            }, g.prototype.attach = function (a) {
                return this.detach(), $(a).prepend(this.el), this.start()
            }, g.prototype.detach = function () {
                return this.stop(), this.el.detach()
            }, g.prototype.setProfile = function (a) {
                return this.profile = a, this._synchronizeUiWithData()
            }, g.prototype._synchronizeUiWithData = function () {
                var b, c, d;
                d = this.dataModel.getVisualGraph().nodes;
                for (b in d) c = d[b], this.profile.styleNode(c);
                return this._preloadIcons(a(function () {
                    return this.sys.merge(this.dataModel.getVisualGraph()), this.start()
                }, this))
            }, g.prototype._preloadIcons = function (b) {
                var c, d, e, f, g, h, i, j;
                (h = this._images) == null && (this._images = {}), (i = this.imagesLoading) == null && (this.imagesLoading = 0), c = !1, j = this.dataModel.getVisualGraph().nodes;
                for (f in j) g = j[f], e = g.style, e.shapeStyle.shape === "icon" && (f = e.iconUrl, this._images[f] == null && (d = new Image, d.src = f, this._images[f] = d, this.imagesLoading += 1, d.onload = a(function () {
                    this.imagesLoading -= 1;
                    if (this.imagesLoading === 0 && c) return b()
                }, this)), e.icon = this._images[f]);
                c = !0;
                if (this.imagesLoading === 0) return b()
            }, g
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/visualizationSettings", [], function () {
        return function (vars) {
            with(vars || {}) return '<p><label for="create-relationship-from">Label keys</label><input type="text" value="' + htmlEscape(labels) + '" id="visualization-label-properties" /><div class="helpbox break">Comma separated list of property keys to use for labeling nodes.</div></p><ul class="button-bar popout-controls"><li><div class="button" id="save-visualization-settings">Done</div></li></ul>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/VisualizationSettingsDialog", ["neo4j/webadmin/utils/ItemUrlResolver", "./visualizationSettings", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f) {
        var g;
        return g = function () {
            function b() {
                this.render = a(this.render, this), this.position = a(this.position, this), this.save = a(this.save, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, e), b.prototype.className = "popout", b.prototype.events = {
                "click #save-visualization-settings": "save"
            }, b.prototype.initialize = function (a) {
                return f("body").append(this.el), this.baseElement = a.baseElement, this.closeCallback = a.closeCallback, this.settings = a.dataBrowserSettings, this.position(), this.render()
            }, b.prototype.save = function () {
                var a, b;
                return b = f("#visualization-label-properties").val().split(","), b = function () {
                    var c, d, e;
                    e = [];
                    for (c = 0, d = b.length; c < d; c++) a = b[c], e.push(a.trim());
                    return e
                }(), this.settings.setLabelProperties(b), this.closeCallback()
            }, b.prototype.position = function () {
                var a, b, c;
                return a = f(this.baseElement).offset(), c = a.top + f(this.baseElement).outerHeight(), b = a.left - (f(this.el).outerWidth() - f(this.baseElement).outerWidth()), f(this.el).css({
                    position: "absolute",
                    top: c + "px",
                    left: b + "px"
                })
            }, b.prototype.render = function () {
                return f(this.el).html(d({
                    labels: this.settings.getLabelProperties().join(", ")
                })), this
            }, b
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/visualization", [], function () {
        return function (vars) {
            with(vars || {}) return '<div id="visualization"><ul class="button-bar item-controls"><li><div title="Control how the visualization looks" class="text-icon-button" id="visualization-profiles-button"><span class="icon"></span>Style</div></li><li><div title="Refresh graph auto-layout" class="text-icon-button" id="visualization-reflow"><span class="icon"></span>Re-layout</div></li><li><div title="Clear the visualization." class="text-icon-button" id="visualization-clear"><span class="icon"></span>Clear</div></li></ul></div><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("ribcage/ui/Dropdown", ["ribcage/View"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.clickedAnywhere = a(this.clickedAnywhere, this), this.activateHideOnClickAnywhere = a(this.activateHideOnClickAnywhere, this), this.hide = a(this.hide, this), this.isVisible = a(this.isVisible, this), d.__super__.constructor.call(this), this.el = $(this.el), this.el.hide(), this.el.addClass("dropdown"), this.listElement = $("<ul></ul>"), this.el.append(this.listElement), $("body").append(this.el)
            }
            return c(d, b), d.prototype.isVisible = function () {
                return this.el.is(":visible")
            }, d.prototype.hide = function () {
                return this.el.hide()
            }, d.prototype.render = function () {
                var a, b, c, d;
                this.listElement.html(""), d = this.getItems();
                for (b = 0, c = d.length; b < c; b++) a = d[b], this.listElement.append(a);
                return this
            }, d.prototype.renderFor = function (a) {
                return this.render(), this.positionFor(a), this.el.show(), setTimeout(this.activateHideOnClickAnywhere, 0)
            }, d.prototype.positionFor = function (a) {
                var b, c, d, e, f, g, h, i, j;
                return a = $(a), j = a.offset(), b = j.left, f = j.top, e = a.outerHeight(), g = a.outerWidth(), i = $(window).width(), h = $(window).height(), d = this.el.outerWidth(), c = this.el.outerHeight(), b + d > i && (b -= d - g), f + c > h && !f - c < 0 && (f -= e + c), this.el.css({
                    position: "absolute",
                    top: f + e,
                    left: b
                })
            }, d.prototype.activateHideOnClickAnywhere = function () {
                return $("body").bind("click", this.clickedAnywhere)
            }, d.prototype.clickedAnywhere = function () {
                return $("body").unbind("click", this.clickedAnywhere), this.hide()
            }, d.prototype.title = function (a) {
                return "<li><h3>" + htmlEscape(a) + "</h3></li>"
            }, d.prototype.divider = function () {
                return "<li><hr /></li>"
            }, d.prototype.actionable = function (a, b) {
                var c;
                return c = $("<li class='actionable'></li>"), c.click(b), c.append(a), c
            }, d.prototype.item = function (a) {
                var b;
                return b = $("<li></li>"), b.append(a), b
            }, d
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/createRelationship", [], function () {
        return function (vars) {
            with(vars || {}) return '<p><label for="create-relationship-from">From node</label><input type="text" value="' + htmlEscape(from) + '" id="create-relationship-from" /></p><p><label for="create-relationship-type">Type</label><input type="text" value="' + htmlEscape(type) + '" id="create-relationship-type" /></p><p><select id="create-relationship-types">&nbsp;<option>Types in use</option>' + function () {
                var a = [],
                    b, c;
                for (b in types) types.hasOwnProperty(b) && (c = types[b], a.push("<option>" + htmlEscape(c) + "</option>"));
                return a.join("")
            }.call(this) + '</select><div class="break"></div></p><p><label for="create-relationship-to">To node</label><input type="text" value="' + htmlEscape(to) + '" id="create-relationship-to" /></p><ul class="button-bar popout-controls"><li><div class="button" id="create-relationship">Create</div></li></ul>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    define("neo4j/webadmin/utils/FormHelper", ["lib/amd/jQuery"], function (a) {
        var b;
        return b = function () {
            function b(b) {
                b == null && (b = "body"), this.context = a(b)
            }
            return b.prototype.addErrorTo = function (b, c, d) {
                var e, f;
                return d == null && (d = "p"), f = a(b, this.context).closest(d), e = f.find(".error"), e.length === 0 && (e = a("<div class='error'></div>"), f.prepend(e)), e.html(c)
            }, b.prototype.removeAllErrors = function () {
                return a(".error", this.context).remove()
            }, b.prototype.removeErrorsFrom = function (b, c) {
                return c == null && (c = "p"), a(b, this.context).closest(c).find(".error").remove()
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/CreateRelationshipDialog", ["neo4j/webadmin/utils/ItemUrlResolver", "./createRelationship", "ribcage/View", "neo4j/webadmin/utils/FormHelper", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function h() {
                this.render = a(this.render, this), this.position = a(this.position, this), this.saveFailed = a(this.saveFailed, this), this.saveSuccessful = a(this.saveSuccessful, this), this.save = a(this.save, this), this.pickedFromAvailableTypes = a(this.pickedFromAvailableTypes, this), this.initialize = a(this.initialize, this), h.__super__.constructor.apply(this, arguments)
            }
            return c(h, e), h.prototype.className = "popout", h.prototype.events = {
                "click #create-relationship": "save",
                "change #create-relationship-types": "pickedFromAvailableTypes"
            }, h.prototype.initialize = function (c) {
                return g(this.el).hide(), g("body").append(this.el), this.formHelper = new f(this.el), this.baseElement = c.baseElement, this.server = c.server, this.dataModel = c.dataModel, this.closeCallback = c.closeCallback, this.urlResolver = new b(this.server), this.type = "RELATED_TO", this.dataModel.dataIsSingleNode() ? this.from = this.dataModel.getData().getId() : this.from = "", this.to = "", this.server.getAvailableRelationshipTypes().then(a(function (a) {
                    return this.types = a, this.position(), this.render(), g(this.el).show()
                }, this))
            }, h.prototype.pickedFromAvailableTypes = function () {
                var a;
                return a = g("#create-relationship-types").val(), a !== "Types in use" && g("#create-relationship-type").val(a), g("#create-relationship-types").val("Types in use")
            }, h.prototype.save = function () {
                var a;
                return this.formHelper.removeAllErrors(), a = g("#create-relationship-type").val(), this.server.rel(this.getFromUrl(), a, this.getToUrl()).then(this.saveSuccessful, this.saveFailed)
            }, h.prototype.saveSuccessful = function (a) {
                var b;
                return b = this.urlResolver.extractRelationshipId(a.getSelf()), this.dataModel.setData(a, !0), this.dataModel.setQuery("rel:" + b, !0), this.closeCallback()
            }, h.prototype.saveFailed = function (a) {
                return a instanceof neo4j.exceptions.NotFoundException ? a.url === this.getFromUrl() ? this.formHelper.addErrorTo("#create-relationship-from", "This node cannot be found.") : this.formHelper.addErrorTo("#create-relationship-to", "This node cannot be found.") : this.formHelper.addErrorTo("#create-relationship-from", "Unable to create relationship.")
            }, h.prototype.getFromUrl = function () {
                return this.urlResolver.getNodeUrl(this.urlResolver.extractNodeId(g("#create-relationship-from").val()))
            }, h.prototype.getToUrl = function () {
                return this.urlResolver.getNodeUrl(this.urlResolver.extractNodeId(g("#create-relationship-to").val()))
            }, h.prototype.position = function () {
                var a, b, c;
                return a = g(this.baseElement).offset(), c = a.top + g(this.baseElement).outerHeight(), b = a.left - (g(this.el).outerWidth() - g(this.baseElement).outerWidth()), g(this.el).css({
                    position: "absolute",
                    top: c + "px",
                    left: b + "px"
                })
            }, h.prototype.render = function () {
                return g(this.el).html(d({
                    from: this.from,
                    to: this.to,
                    type: this.type,
                    types: this.types
                })), this
            }, h
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/views/base", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="workarea"><div class="controls pad"><div class="span-half data-console-wrap"><input value="' + htmlEscape(query) + '" type="text" id="data-console" /><div title="Search" class="icon-button" id="data-execute-console"><span class="icon"></span></div><div class="form-tooltip"><a href="#"><span class="form-tooltip-icon"></span><span class="form-tooltip-text"><b>Shortcuts:</b><br /><b>s</b> - Highlight search bar</br><b>v</b> - Toggle between visualizer and tabular view<br /><br /><b>Search bar syntax:</b><br /><b>Cypher:</b> [any cypher query]<br /><b>Get node:</b> [node id]<br /><b>Get relationship:</b> rel:[relationship id]<br /><b>Get relationships for node:</b> rels:[node id]<br /><br /><b>Indexes</b><br /><b>Search nodes</b> node:index:[index]:[query]<br /><i>Example: node:index:myindex:name:*</i><br /><b>Search relationships:</b> rel:index:[index]:[index query]<br /><b>Direct node lookup</b> node:index:[index]:[key]:[value]<br /><b>Direct relationship lookup</b> rel:index:[index]:[key]:[value]</span></a></div></div><div class="span-half last"><ul class="data-toolbar button-bar"><li><div title="Create a node" class="text-icon-button" id="data-create-node"><span class="icon"></span>Node</div></li><li><div title="Create a relationship" class="text-icon-button" id="data-create-relationship"><span class="icon"></span>Relationship</div></li>' + function () {
                return viewType === "tabular" ? '<li><div title="Switch view mode" class="icon-button" id="data-switch-view"><span class="icon"></span></div></li>' : ""
            }.call(this) + function () {
                return viewType !== "tabular" ? '<li><div title="Switch view mode" class="icon-button" id="data-switch-view"><span class="icon tabular"></span></div></li>' : ""
            }.call(this) + '</ul></div><div class="break"></div></div><div id="data-area"></div></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/databrowser/visualization/views/visualizationSettings", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><h1 class="pad">Visualization settings</h1></div><div class="workarea with-sidebar" id="visualization-settings"><h2 class="pad">Visualization profiles</h2><table class="visualization-profile-list"></table><a href="#/data/visualization/settings/profile/" class="button">New profile</a></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/databrowser/visualization/views/profileListItem", [], function () {
        return function (vars) {
            with(vars || {}) return '<td class="name">' + name + '</td><td><a href="#/data/visualization/settings/profile/' + encodeURIComponent(id) + '/" class="edit-profile button">Edit</a></td><td>' + function () {
                return isBuiltin ? "" : '<button class="delete-profile bad-button">Delete</button>'
            }.call(this) + "</td>"
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/views/ProfileListItemView", ["neo4j/webadmin/utils/ItemUrlResolver", "./profileListItem", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f) {
        var g;
        return g = function () {
            function b() {
                this.render = a(this.render, this), this.deleteProfile = a(this.deleteProfile, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, e), b.prototype.tagName = "tr", b.prototype.events = {
                "click .delete-profile": "deleteProfile"
            }, b.prototype.initialize = function (a) {
                return this.settings = a.dataBrowserSettings, this.profile = a.profile, this.profiles = this.settings.getVisualizationProfiles()
            }, b.prototype.deleteProfile = function () {
                if (confirm("Are you sure?")) return this.profiles.remove(this.profile), this.profiles.save(), this.remove()
            }, b.prototype.render = function () {
                return f(this.el).html(d({
                    name: this.profile.getName(),
                    id: this.profile.id,
                    isBuiltin: this.profile.isBuiltin()
                })), this
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/views/VisualizationSettingsView", ["neo4j/webadmin/utils/ItemUrlResolver", "./visualizationSettings", "./ProfileListItemView", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function b() {
                this.render = a(this.render, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, f), b.prototype.initialize = function (a) {
                return this.settings = a.dataBrowserSettings
            }, b.prototype.render = function () {
                var b;
                return g(this.el).html(d()), b = g(".visualization-profile-list", this.el), this.settings.getVisualizationProfiles().forEach(a(function (a) {
                    var c;
                    return c = new e({
                        profile: a,
                        dataBrowserSettings: this.settings
                    }), b.append(c.render().el)
                }, this)), this
            }, b
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/visualization/views/visualizationProfile", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="workarea" id="visualization-settings"><div class="pad"><div class="span-half">' + function () {
                return isInCreateMode ? '<h2>Create new visualization profile</h2><input id="profile-name" type="text" placeholder="Enter a name for this profile" class="big" />' : ""
            }.call(this) + function () {
                return isInCreateMode ? "" : '<h2>Manage visualization profile</h2><input id="profile-name" type="text" value="' + htmlEscape(name) + '" placeholder="Enter a name for this profile" class="big" />'
            }.call(this) + '</div><div class="span-half last"><ul class="button-bar data-toolbar"><li><button class="button save">Save</button></li><li><button class="button cancel">Cancel</button></li></ul></div><div class="break"></div></div><div class="styleRulesWrap"><div class="span-half"><div class="headline-bar pad"><h2>Style rules</h2><div class="form-help-text">Each style rule controls how either all nodes, or a filtered subset of nodes, should look in the visualization. The first matching style rule will be used, you can re-order the rules with the drag-handle under the each rules\' Remove button.</div></div><div class="break"></div></div><div class="break"></div><ul class="styleRules"></ul><div class="button-bar pad"><button class="button addStyleRule">Add style rule</button><div class="break"></div></div></div><ul class="button-bar pad"><li><button class="button save">Save</button></li><li><button class="button cancel">Cancel</button></li></ul></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("ribcage/LocalCollection", ["lib/amd/Backbone"], function (b) {
        var d;
        return d = function () {
            function d(b) {
                this.save = a(this.save, this), this.fetch = a(this.fetch, this);
                var c, e, f;
                _(b).isArray() || (b = []), this._idCounter = 0;
                for (e = 0, f = b.length; e < f; e++) c = b[e], c.id && c.id > this._idCounter && (this._idCounter = c.id);
                d.__super__.constructor.call(this, b)
            }
            return c(d, b.Collection), d.prototype.add = function (c, e) {
                var f, g, h, i, j;
                _(c).isArray() || (c = [c]), h = [];
                for (i = 0, j = c.length; i < j; i++) {
                    f = c[i], f.id == null && (f.id = ++this._idCounter);
                    if (!(f instanceof b.Model)) try {
                        f = this.deserializeItem(f)
                    } catch (k) {
                        continue
                    }
                    g = a(function () {
                        return this.fetch(f.id)
                    }, this), f.setSaveMethod != null && f.setSaveMethod(this.save), f.setFetchMethod != null && f.setFetchMethod(g), h.push(f)
                }
                return d.__super__.add.call(this, h, e)
            }, d.prototype.fetch = function (a) {
                var b, c, d, e;
                a == null && (a = null), c = this._fetch();
                if (a === null) return this.update(c);
                for (d = 0, e = c.length; d < e; d++) {
                    b = c[d];
                    if (a === b.id) return b
                }
            }, d.prototype.update = function (a) {
                var b, c, d, e, f;
                f = [];
                for (d = 0, e = jsonItems.length; d < e; d++) c = jsonItems[d], f.push((b = this.get(c.id)) !== null ? (b.clear({
                    silent: !0
                }), b.set(c)) : this.add(c));
                return f
            }, d.prototype.save = function () {
                return this._save(this)
            }, d.prototype.deserializeItem = function (a) {
                return this.model != null ? new this.model(a) : a
            }, d.prototype.setFetchMethod = function (a) {
                this._fetch = a
            }, d.prototype.setSaveMethod = function (a) {
                this._save = a
            }, d
        }()
    })
}.call(this),
function () {
    define("ribcage/ui/Nano", [], function () {
        var a;
        return a = function () {
            function a() {}
            return a.pipes = {
                truncate: function (a, b) {
                    var c;
                    return c = Number(b[0]), c !== NaN && a.length > c ? a.slice(0, c) : a
                }
            }, a.compile = function (b, c) {
                return b.replace(/\{([\w\-\,\.\|:]*)}/g, function (b, d) {
                    var e, f, g, h, i, j, k, l, m, n, o, p, q, r, s;
                    k = d.split("|"), f = function () {
                        var a, b, c, d;
                        c = k.shift().split(","), d = [];
                        for (a = 0, b = c.length; a < b; a++) i = c[a], d.push(i.split("."));
                        return d
                    }();
                    for (m = 0, p = f.length; m < p; m++) {
                        g = f[m], l = c[g.shift()];
                        for (n = 0, q = g.length; n < q; n++) d = g[n], l != null && l.hasOwnProperty(d) ? l = l[d] : l = null;
                        if (l != null) {
                            for (o = 0, r = k.length; o < r; o++) j = k[o], s = j.split(":"), h = s[0], e = s[1], e = e.split(","), l = a.pipes[h](l, e);
                            return l
                        }
                    }
                    return "N/A"
                })
            }, a
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("ribcage/ui/ImagePicker", ["ribcage/View"], function (b) {
        var d;
        return d = function () {
            function d(b, c) {
                this.imgUrls = b, this.cols = c != null ? c : 8, this.imageClicked = a(this.imageClicked, this), d.__super__.constructor.call(this), this.el = $(this.el), this.el.addClass("image-picker"), this.el.addClass("grid")
            }
            return c(d, b), d.prototype.tagName = "ul", d.prototype.render = function () {
                var b, c, d, e, f, g;
                this.el.html(""), f = a(function (b) {
                    return c.click(a(function () {
                        return this.imageClicked(b)
                    }, this))
                }, this);
                for (b = 0, g = this.imgUrls.length; 0 > g ? b > g : b < g; 0 > g ? b-- : b++) b % this.cols === 0 && (d = $("<ul></ul>"), c = $("<li></li>"), c.append(d), this.el.append(c)), e = this.imgUrls[b], c = $("<li><div class='imagepicker-image' style='background:url(" + e + ") no-repeat center center;'><div/></li>"), f(e), d.append(c);
                return this
            }, d.prototype.imageClicked = function (a) {
                return this.trigger("image:clicked", {
                    url: a
                })
            }, d
        }(), _(d.prototype).extend(Backbone.Events), d
    })
}.call(this),
function (a) {
    var b = function () {
        var b = {}, c, d = 65,
            e, f = '<div class="colorpicker"><div class="colorpicker_color"><div><div></div></div></div><div class="colorpicker_hue"><div></div></div><div class="colorpicker_new_color"></div><div class="colorpicker_current_color"></div><div class="colorpicker_hex"><input type="text" maxlength="6" size="6" /></div><div class="colorpicker_rgb_r colorpicker_field"><input type="text" maxlength="3" size="3" /><span></span></div><div class="colorpicker_rgb_g colorpicker_field"><input type="text" maxlength="3" size="3" /><span></span></div><div class="colorpicker_rgb_b colorpicker_field"><input type="text" maxlength="3" size="3" /><span></span></div><div class="colorpicker_hsb_h colorpicker_field"><input type="text" maxlength="3" size="3" /><span></span></div><div class="colorpicker_hsb_s colorpicker_field"><input type="text" maxlength="3" size="3" /><span></span></div><div class="colorpicker_hsb_b colorpicker_field"><input type="text" maxlength="3" size="3" /><span></span></div><div class="colorpicker_submit"></div></div>',
            g = {
                eventName: "click",
                onShow: function () {},
                onBeforeShow: function () {},
                onHide: function () {},
                onChange: function () {},
                onSubmit: function () {},
                color: "ff0000",
                livePreview: !0,
                flat: !1
            }, h = function (b, c) {
                var d = O(b);
                a(c).data("colorpicker").fields.eq(1).val(d.r).end().eq(2).val(d.g).end().eq(3).val(d.b).end()
            }, i = function (b, c) {
                a(c).data("colorpicker").fields.eq(4).val(b.h).end().eq(5).val(b.s).end().eq(6).val(b.b).end()
            }, j = function (b, c) {
                a(c).data("colorpicker").fields.eq(0).val(Q(b)).end()
            }, k = function (b, c) {
                a(c).data("colorpicker").selector.css("backgroundColor", "#" + Q({
                    h: b.h,
                    s: 100,
                    b: 100
                })), a(c).data("colorpicker").selectorIndic.css({
                    left: parseInt(150 * b.s / 100, 10),
                    top: parseInt(150 * (100 - b.b) / 100, 10)
                })
            }, l = function (b, c) {
                a(c).data("colorpicker").hue.css("top", parseInt(150 - 150 * b.h / 360, 10))
            }, m = function (b, c) {
                a(c).data("colorpicker").currentColor.css("backgroundColor", "#" + Q(b))
            }, n = function (b, c) {
                a(c).data("colorpicker").newColor.css("backgroundColor", "#" + Q(b))
            }, o = function (b) {
                var c = b.charCode || b.keyCode || -1;
                if (c > d && c <= 90 || c == 32) return !1;
                var e = a(this).parent().parent();
                e.data("colorpicker").livePreview === !0 && p.apply(this)
            }, p = function (b) {
                var c = a(this).parent().parent(),
                    d;
                this.parentNode.className.indexOf("_hex") > 0 ? c.data("colorpicker").color = d = M(K(this.value)) : this.parentNode.className.indexOf("_hsb") > 0 ? c.data("colorpicker").color = d = I({
                    h: parseInt(c.data("colorpicker").fields.eq(4).val(), 10),
                    s: parseInt(c.data("colorpicker").fields.eq(5).val(), 10),
                    b: parseInt(c.data("colorpicker").fields.eq(6).val(), 10)
                }) : c.data("colorpicker").color = d = N(J({
                    r: parseInt(c.data("colorpicker").fields.eq(1).val(), 10),
                    g: parseInt(c.data("colorpicker").fields.eq(2).val(), 10),
                    b: parseInt(c.data("colorpicker").fields.eq(3).val(), 10)
                })), b && (h(d, c.get(0)), j(d, c.get(0)), i(d, c.get(0))), k(d, c.get(0)), l(d, c.get(0)), n(d, c.get(0)), c.data("colorpicker").onChange.apply(c, [d, Q(d), O(d)])
            }, q = function (b) {
                var c = a(this).parent().parent();
                c.data("colorpicker").fields.parent().removeClass("colorpicker_focus")
            }, r = function () {
                d = this.parentNode.className.indexOf("_hex") > 0 ? 70 : 65, a(this).parent().parent().data("colorpicker").fields.parent().removeClass("colorpicker_focus"), a(this).parent().addClass("colorpicker_focus")
            }, s = function (b) {
                var c = a(this).parent().find("input").focus(),
                    d = {
                        el: a(this).parent().addClass("colorpicker_slider"),
                        max: this.parentNode.className.indexOf("_hsb_h") > 0 ? 360 : this.parentNode.className.indexOf("_hsb") > 0 ? 100 : 255,
                        y: b.pageY,
                        field: c,
                        val: parseInt(c.val(), 10),
                        preview: a(this).parent().parent().data("colorpicker").livePreview
                    };
                a(document).bind("mouseup", d, u), a(document).bind("mousemove", d, t)
            }, t = function (a) {
                return a.data.field.val(Math.max(0, Math.min(a.data.max, parseInt(a.data.val + a.pageY - a.data.y, 10)))), a.data.preview && p.apply(a.data.field.get(0), [!0]), !1
            }, u = function (b) {
                return p.apply(b.data.field.get(0), [!0]), b.data.el.removeClass("colorpicker_slider").find("input").focus(), a(document).unbind("mouseup", u), a(document).unbind("mousemove", t), !1
            }, v = function (b) {
                var c = {
                    cal: a(this).parent(),
                    y: a(this).offset().top
                };
                c.preview = c.cal.data("colorpicker").livePreview, a(document).bind("mouseup", c, x), a(document).bind("mousemove", c, w)
            }, w = function (a) {
                return p.apply(a.data.cal.data("colorpicker").fields.eq(4).val(parseInt(360 * (150 - Math.max(0, Math.min(150, a.pageY - a.data.y))) / 150, 10)).get(0), [a.data.preview]), !1
            }, x = function (b) {
                return h(b.data.cal.data("colorpicker").color, b.data.cal.get(0)), j(b.data.cal.data("colorpicker").color, b.data.cal.get(0)), a(document).unbind("mouseup", x), a(document).unbind("mousemove", w), !1
            }, y = function (b) {
                var c = {
                    cal: a(this).parent(),
                    pos: a(this).offset()
                };
                c.preview = c.cal.data("colorpicker").livePreview, a(document).bind("mouseup", c, A), a(document).bind("mousemove", c, z)
            }, z = function (a) {
                return p.apply(a.data.cal.data("colorpicker").fields.eq(6).val(parseInt(100 * (150 - Math.max(0, Math.min(150, a.pageY - a.data.pos.top))) / 150, 10)).end().eq(5).val(parseInt(100 * Math.max(0, Math.min(150, a.pageX - a.data.pos.left)) / 150, 10)).get(0), [a.data.preview]), !1
            }, A = function (b) {
                return h(b.data.cal.data("colorpicker").color, b.data.cal.get(0)), j(b.data.cal.data("colorpicker").color, b.data.cal.get(0)), a(document).unbind("mouseup", A), a(document).unbind("mousemove", z), !1
            }, B = function (b) {
                a(this).addClass("colorpicker_focus")
            }, C = function (b) {
                a(this).removeClass("colorpicker_focus")
            }, D = function (b) {
                var c = a(this).parent(),
                    d = c.data("colorpicker").color;
                c.data("colorpicker").origColor = d, m(d, c.get(0)), c.data("colorpicker").onSubmit(d, Q(d), O(d), c.data("colorpicker").el)
            }, E = function (b) {
                var c = a("#" + a(this).data("colorpickerId"));
                c.data("colorpicker").onBeforeShow.apply(this, [c.get(0)]);
                var d = a(this).offset(),
                    e = H(),
                    f = d.top + this.offsetHeight,
                    g = d.left;
                return f + 176 > e.t + e.h && (f -= this.offsetHeight + 176), g + 356 > e.l + e.w && (g -= 356), c.css({
                    left: g + "px",
                    top: f + "px"
                }), c.data("colorpicker").onShow.apply(this, [c.get(0)]) != 0 && c.show(), a(document).bind("mousedown", {
                    cal: c
                }, F), !1
            }, F = function (b) {
                G(b.data.cal.get(0), b.target, b.data.cal.get(0)) || (b.data.cal.data("colorpicker").onHide.apply(this, [b.data.cal.get(0)]) != 0 && b.data.cal.hide(), a(document).unbind("mousedown", F))
            }, G = function (a, b, c) {
                if (a == b) return !0;
                if (a.contains) return a.contains(b);
                if (a.compareDocumentPosition) return !!(a.compareDocumentPosition(b) & 16);
                var d = b.parentNode;
                while (d && d != c) {
                    if (d == a) return !0;
                    d = d.parentNode
                }
                return !1
            }, H = function () {
                var a = document.compatMode == "CSS1Compat";
                return {
                    l: window.pageXOffset || (a ? document.documentElement.scrollLeft : document.body.scrollLeft),
                    t: window.pageYOffset || (a ? document.documentElement.scrollTop : document.body.scrollTop),
                    w: window.innerWidth || (a ? document.documentElement.clientWidth : document.body.clientWidth),
                    h: window.innerHeight || (a ? document.documentElement.clientHeight : document.body.clientHeight)
                }
            }, I = function (a) {
                return {
                    h: Math.min(360, Math.max(0, a.h)),
                    s: Math.min(100, Math.max(0, a.s)),
                    b: Math.min(100, Math.max(0, a.b))
                }
            }, J = function (a) {
                return {
                    r: Math.min(255, Math.max(0, a.r)),
                    g: Math.min(255, Math.max(0, a.g)),
                    b: Math.min(255, Math.max(0, a.b))
                }
            }, K = function (a) {
                var b = 6 - a.length;
                if (b > 0) {
                    var c = [];
                    for (var d = 0; d < b; d++) c.push("0");
                    c.push(a), a = c.join("")
                }
                return a
            }, L = function (a) {
                var a = parseInt(a.indexOf("#") > -1 ? a.substring(1) : a, 16);
                return {
                    r: a >> 16,
                    g: (a & 65280) >> 8,
                    b: a & 255
                }
            }, M = function (a) {
                return N(L(a))
            }, N = function (a) {
                var b = {
                    h: 0,
                    s: 0,
                    b: 0
                }, c = Math.min(a.r, a.g, a.b),
                    d = Math.max(a.r, a.g, a.b),
                    e = d - c;
                return b.b = d, d == 0, b.s = d != 0 ? 255 * e / d : 0, b.s != 0 ? a.r == d ? b.h = (a.g - a.b) / e : a.g == d ? b.h = 2 + (a.b - a.r) / e : b.h = 4 + (a.r - a.g) / e : b.h = -1, b.h *= 60, b.h < 0 && (b.h += 360), b.s *= 100 / 255, b.b *= 100 / 255, b
            }, O = function (a) {
                var b = {}, c = Math.round(a.h),
                    d = Math.round(a.s * 255 / 100),
                    e = Math.round(a.b * 255 / 100);
                if (d == 0) b.r = b.g = b.b = e;
                else {
                    var f = e,
                        g = (255 - d) * e / 255,
                        h = (f - g) * (c % 60) / 60;
                    c == 360 && (c = 0), c < 60 ? (b.r = f, b.b = g, b.g = g + h) : c < 120 ? (b.g = f, b.b = g, b.r = f - h) : c < 180 ? (b.g = f, b.r = g, b.b = g + h) : c < 240 ? (b.b = f, b.r = g, b.g = f - h) : c < 300 ? (b.b = f, b.g = g, b.r = g + h) : c < 360 ? (b.r = f, b.g = g, b.b = f - h) : (b.r = 0, b.g = 0, b.b = 0)
                }
                return {
                    r: Math.round(b.r),
                    g: Math.round(b.g),
                    b: Math.round(b.b)
                }
            }, P = function (b) {
                var c = [b.r.toString(16), b.g.toString(16), b.b.toString(16)];
                return a.each(c, function (a, b) {
                    b.length == 1 && (c[a] = "0" + b)
                }), c.join("")
            }, Q = function (a) {
                return P(O(a))
            }, R = function () {
                var b = a(this).parent(),
                    c = b.data("colorpicker").origColor;
                b.data("colorpicker").color = c, h(c, b.get(0)), j(c, b.get(0)), i(c, b.get(0)), k(c, b.get(0)), l(c, b.get(0)), n(c, b.get(0))
            };
        return {
            init: function (b) {
                b = a.extend({}, g, b || {});
                if (typeof b.color == "string") b.color = M(b.color);
                else if (b.color.r != undefined && b.color.g != undefined && b.color.b != undefined) b.color = N(b.color);
                else if (b.color.h != undefined && b.color.s != undefined && b.color.b != undefined) b.color = I(b.color);
                else return this;
                return this.each(function () {
                    if (!a(this).data("colorpickerId")) {
                        var c = a.extend({}, b);
                        c.origColor = b.color;
                        var d = "collorpicker_" + parseInt(Math.random() * 1e3);
                        a(this).data("colorpickerId", d);
                        var e = a(f).attr("id", d);
                        c.flat ? e.appendTo(this).show() : e.appendTo(document.body), c.fields = e.find("input").bind("keyup", o).bind("change", p).bind("blur", q).bind("focus", r), e.find("span").bind("mousedown", s).end().find(">div.colorpicker_current_color").bind("click", R), c.selector = e.find("div.colorpicker_color").bind("mousedown", y), c.selectorIndic = c.selector.find("div div"), c.el = this, c.hue = e.find("div.colorpicker_hue div"), e.find("div.colorpicker_hue").bind("mousedown", v), c.newColor = e.find("div.colorpicker_new_color"), c.currentColor = e.find("div.colorpicker_current_color"), e.data("colorpicker", c), e.find("div.colorpicker_submit").bind("mouseenter", B).bind("mouseleave", C).bind("click", D), h(c.color, e.get(0)), i(c.color, e.get(0)), j(c.color, e.get(0)), l(c.color, e.get(0)), k(c.color, e.get(0)), m(c.color, e.get(0)), n(c.color, e.get(0)), c.flat ? e.css({
                            position: "relative",
                            display: "block"
                        }) : a(this).bind(c.eventName, E)
                    }
                })
            },
            showPicker: function () {
                return this.each(function () {
                    a(this).data("colorpickerId") && E.apply(this)
                })
            },
            hidePicker: function () {
                return this.each(function () {
                    a(this).data("colorpickerId") && a("#" + a(this).data("colorpickerId")).hide()
                })
            },
            setColor: function (b) {
                if (typeof b == "string") b = M(b);
                else if (b.r != undefined && b.g != undefined && b.b != undefined) b = N(b);
                else if (b.h != undefined && b.s != undefined && b.b != undefined) b = I(b);
                else return this;
                return this.each(function () {
                    if (a(this).data("colorpickerId")) {
                        var c = a("#" + a(this).data("colorpickerId"));
                        c.data("colorpicker").color = b, c.data("colorpicker").origColor = b, h(b, c.get(0)), i(b, c.get(0)), j(b, c.get(0)), l(b, c.get(0)), k(b, c.get(0)), m(b, c.get(0)), n(b, c.get(0))
                    }
                })
            }
        }
    }();
    a.fn.extend({
        ColorPicker: b.init,
        ColorPickerHide: b.hidePicker,
        ColorPickerShow: b.showPicker,
        ColorPickerSetColor: b.setColor
    })
}(jQuery), define("lib/colorpicker", function () {}), define("lib/rgbcolor", function () {}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("ribcage/forms", ["ribcage/View", "ribcage/security/HtmlEscaper", "ribcage/ui/Nano", "ribcage/ui/Dialog", "ribcage/ui/ImagePicker", "lib/colorpicker", "lib/rgbcolor"], function (b, d, e, f, g) {
        var h, i, j, k, l, m, n, o, p, q, r;
        return r = {}, r.ModelForm = n = function () {
            function d() {
                this.render = a(this.render, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.createFields = function () {
                return {}
            }, d.prototype.initialize = function (a) {
                var b, c, d, e;
                this.fields = this.createFields(), d = this.fields, e = [];
                for (c in d) b = d[c], b.setPropertyKey(c), e.push(b.setModel(this.model));
                return e
            }, d.prototype.render = function () {
                var a, b, c, d;
                c = $("<ul class='form'></ul>"), d = this._getFieldSets();
                for (b in d) a = d[b], c.append(a.renderLi(this.model));
                return $(this.el).html(c), this
            }, d.prototype.validates = function () {
                var a, b, c;
                c = this.fields;
                for (b in c) {
                    a = c[b];
                    if (!a.validates()) return !1
                }
                return !0
            }, d.prototype._getFieldSets = function () {
                var a, b, c, d, e;
                d = {
                    _default: new j
                }, b = !1, e = this.fields;
                for (c in e) a = e[c], a instanceof j ? d[c] = a : (b = !0, d._default.fields[c] = a);
                return b || delete d._default, d
            }, d
        }(), r.FieldSet = j = function () {
            function b(b, c) {
                this.label = b != null ? b : "", this.fields = c != null ? c : {}, this.setPropertyKey = a(this.setPropertyKey, this), this.setModel = a(this.setModel, this), _(this.label).isString() || (this.fields = this.label, this.label = null)
            }
            return b.prototype.setModel = function (a) {
                var b, c, d, e;
                d = this.fields, e = [];
                for (c in d) b = d[c], b.setPropertyKey(c), e.push(b.setModel(a));
                return e
            }, b.prototype.setPropertyKey = function (a) {}, b.prototype.renderLi = function (a) {
                var b, c, d, e, f;
                d = $("<ul class='form-fieldset'></ul>"), f = this.fields;
                for (c in f) b = f[c], d.append(b.renderLi());
                return e = $("<li></li>"), this.label && e.append("<h3>" + htmlEscape(this.label) + "</h3>"), e.append(d), e
            }, b.prototype.validates = function () {
                var a, b, c;
                c = this.fields;
                for (b in c) {
                    a = c[b];
                    if (!a.validates()) return !1
                }
                return !0
            }, b
        }(), r.ValueException = q = function () {
            function a(b) {
                this.errorMessage = b, a.__super__.constructor.call(this, this.errorMessage)
            }
            return c(a, Error), a
        }(), r.Field = i = function () {
            function b(b, c) {
                this.label = b, this.opts = c != null ? c : {}, this.setPropertyKey = a(this.setPropertyKey, this), this.setModel = a(this.setModel, this), this.tooltip = this.opts.tooltip || ""
            }
            return b.prototype.LI_TEMPLATE = "<li>\n  {label}\n  {tooltip}\n  <div class='form-error' style='display:none;'></div>\n  {input} \n</li>", b.prototype.errors = [], b.prototype.setModel = function (a) {
                this.model = a
            }, b.prototype.setPropertyKey = function (a) {
                this.propertyKey = a
            }, b.prototype.renderLi = function () {
                return this.renderWithTemplate(this.LI_TEMPLATE)
            }, b.prototype.renderWithTemplate = function (a) {
                var b;
                return b = this.tooltip.length > 0 ? "<div class='form-tooltip'><a><span class='form-tooltip-icon'></span><span class='form-tooltip-text'>" + this.tooltip + "</span></a></div>" : "", this.el = $(e.compile(a, {
                    label: "<label class='form-label'>" + this.label + "</label>",
                    input: "<div class='__PLACEHOLDER__'></div>",
                    tooltip: b
                })), this.widget = this.renderWidget(), $(".__PLACEHOLDER__", this.el).replaceWith(this.widget), this.updateUIValue(), this.el
            }, b.prototype.updateUIValue = function () {
                return this.setUIValue(this.model.get(this.propertyKey))
            }, b.prototype.setUIValue = function (a) {
                return this.widget.val(a)
            }, b.prototype.setModelValue = function (a) {
                try {
                    return this.setErrors([]), a = this.cleanUIValue(a), this.model.set(this.propertyKey, a)
                } catch (b) {
                    return this.setErrors([b.errorMessage])
                }
            }, b.prototype.cleanUIValue = function (a) {
                return a
            }, b.prototype.setErrors = function (a) {
                return this.errors = a, this.errors.length === 0 ? this.hideErrorBox() : this.showErrorBox(this.errors[0])
            }, b.prototype.validates = function () {
                return this.errors.length === 0
            }, b.prototype.hideErrorBox = function () {
                return $(".form-error", this.el).hide()
            }, b.prototype.showErrorBox = function (a) {
                var b;
                return b = $(".form-error", this.el), b.html(a), b.show()
            }, b
        }(), r.TextField = p = function () {
            function b() {
                this.renderWidget = a(this.renderWidget, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, i), b.prototype.renderWidget = function () {
                var b;
                return b = $("<input type='text' class='form-input' value='' />"), b.change(a(function () {
                    return this.setModelValue(b.val())
                }, this)), b
            }, b
        }(), r.NumberField = o = function () {
            function a() {
                a.__super__.constructor.apply(this, arguments)
            }
            return c(a, p), a.prototype.cleanUIValue = function (a) {
                a = Number(a);
                if (!_(a).isNumber()) throw new q("Value must be a number");
                return a
            }, a
        }(), r.ImageURLField = m = function () {
            function b() {
                this.updateImageElementUrl = a(this.updateImageElementUrl, this), this.setUIValue = a(this.setUIValue, this), this.renderWidget = a(this.renderWidget, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, i), b.prototype.renderWidget = function () {
                var b, c, d, e, h;
                return this.urlInput = $("<input type='text' class='form-input' value='' />"), this.urlInput.change(a(function () {
                    return this.setModelValue(this.urlInput.val()), this.updateImageElementUrl(this.urlInput.val())
                }, this)), this.imageElement = $("<img class='form-image-url-field-preview'/>"), d = $("<div class='form-image-url-field'></div>"), b = $("<div class='form-image-url-field-metabar'></div>"), b.append("<span class='form-image-url-field-preview-title small'>Preview:</span>"), d.append(this.urlInput), d.append(b), this.opts["imageUrls"] != null && ((e = this.imagePicker) == null && (this.imagePicker = new g(this.opts.imageUrls)), $(this.imagePicker.el).addClass("imagepicker-dialog"), (h = this.dialog) == null && (this.dialog = new f(this.imagePicker)), this.dialog.el.prepend("<h1>Pick an image you like</h1>"), this.imagePicker.bind("image:clicked", a(function (a) {
                    return this.setUIValue(a.url), this.setModelValue(a.url), this.dialog.hide()
                }, this)), c = $("<a href='#' class='form-image-url-field-builtin micro-button'>Built in images</a>"), c.click(a(function (a) {
                    return a.preventDefault(), this.dialog.show()
                }, this)), b.append(c)), d.append(this.imageElement), d
            }, b.prototype.setUIValue = function (a) {
                return this.urlInput.val(a), this.updateImageElementUrl(a)
            }, b.prototype.updateImageElementUrl = function (a) {
                return this.imageElement.attr("src", a)
            }, b
        }(), r.ColorField = h = function () {
            function b() {
                this.setUIValue = a(this.setUIValue, this), this.renderWidget = a(this.renderWidget, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, i), b.prototype.renderWidget = function () {
                var b;
                return b = $("<div class='colorpicker-input'></div>"), b.ColorPicker({
                    onChange: function (a, c, d) {
                        return b.css({
                            "background-color": "#" + c
                        })
                    },
                    onBeforeShow: function () {
                        var a;
                        return a = new RGBColor(b.css("background-color")), b.ColorPickerSetColor(a.toHex())
                    },
                    onHide: a(function (a, c, d) {
                        var e;
                        return e = new RGBColor(b.css("background-color")), this.setModelValue(e.toRGB())
                    }, this)
                }), b
            }, b.prototype.setUIValue = function (a) {
                return this.widget.css("background-color", a)
            }, b
        }(), r.FormChooserField = k = function () {
            function b(c, d) {
                this.label = c, this.options = d, this.showForm = a(this.showForm, this), this.setUIValue = a(this.setUIValue, this), this.renderWidget = a(this.renderWidget, this), b.__super__.constructor.call(this, this.label)
            }
            return c(b, i), b.prototype.renderWidget = function () {
                var b, c, d, e;
                this.select = $("<select></select>"), e = this.options;
                for (b in e) c = e[b], this.select.append("<option value='" + htmlEscape(b) + "'>" + htmlEscape(c.label) + "</option>");
                return this.select.change(a(function (a) {
                    return this.setModelValue($(a.target).val()), this.showForm($(a.target).val())
                }, this)), d = $("<div class='form-chooser-field'></div>"), this.formContainer = $("<div class='form-chooser-form-container'></div>"), d.append(this.select), d.append(this.formContainer), d
            }, b.prototype.setUIValue = function (a) {
                return this.select.val(a), this.showForm(a)
            }, b.prototype.showForm = function (a) {
                var b, c, d;
                if (this.options[a] == null) {
                    d = this.options;
                    for (b in d) {
                        c = d[b], a = b;
                        break
                    }
                }
                return this.formContainer.html(""), this.formContainer.append(this.options[a].form.render().el)
            }, b
        }(), r.FormChooserOption = l = function () {
            function a(a, b) {
                this.label = a, this.form = b
            }
            return a
        }(), r
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        }, c = function (a, b) {
            return function () {
                return a.apply(b, arguments)
            }
        };
    define("ribcage/LocalModel", ["lib/amd/Backbone"], function (a) {
        var d;
        return d = function () {
            function d(a, b) {
                this._nestedModels = [], d.__super__.constructor.call(this, a, b)
            }
            return b(d, a.Model), d.prototype.get = function (a, b) {
                var c;
                return b == null && (b = null), c = d.__super__.get.call(this, a), c ? c : b
            }, d.prototype.set = function (a, b, c) {
                var e;
                return _(a).isString() ? (e = {}, e[a] = b, a = e) : c = b, d.__super__.set.call(this, a, c)
            }, d.prototype.fetch = function (a) {
                var b;
                return a == null && (a = null), b = this._fetch(), a === null ? (this.clear({
                    silent: !0
                }), this.set(b)) : b[a]
            }, d.prototype.save = function () {
                return this._save(this)
            }, d.prototype.initNestedModel = function (a, b) {
                return b.deserialize != null ? this[a] = b.deserialize(this.get(a)) : this[a] = new b(this.get(a)), this[a].setFetchMethod(c(function () {
                    return this.fetch(a)
                }, this)), this[a].setSaveMethod(this.save), this._nestedModels.push(a)
            }, d.prototype.toJSON = function () {
                var a, b, c, e, f;
                a = d.__super__.toJSON.call(this), a.id = this.id, f = this._nestedModels;
                for (c = 0, e = f.length; c < e; c++) b = f[c], a[b] = this[b].toJSON();
                return a
            }, d.prototype.setFetchMethod = function (a) {
                this._fetch = a
            }, d.prototype.setSaveMethod = function (a) {
                this._save = a
            }, d
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/style", ["ribcage/LocalModel", "ribcage/ui/Nano", "ribcage/forms", "ribcage/View", "lib/rgbcolor"], function (a, c, d, e) {
        var f, g, h, i, j, k, l;
        return l = {}, i = 'You can use placeholders in the label.<br/>\n{id} for node id<br/>\n{PROPERTYNAME} or {prop.PROPERTYNAME} for properties.<br/>\n{props} for all properties.<br/><br/>\n<b>Truncate values</b><br/>\n{bigproperty|truncate:10}<br/><br/>\n<b>Use first matching property</b><br/>\n{name,title,id}<br/><br/>\n<b>Multiline labels</b><br/>\nUse ";" to create multiline labels.<br/><br/>\n<b>Example</b><br/>\n{id};{description|truncate:20}..', f = function () {
            function a() {
                a.__super__.constructor.apply(this, arguments)
            }
            return b(a, d.ModelForm), a.prototype.createFields = function () {
                return {
                    shapeColor: new d.ColorField("Background"),
                    labelColor: new d.ColorField("Label color")
                }
            }, a
        }(), h = function () {
            function a() {
                a.__super__.constructor.apply(this, arguments)
            }
            return b(a, d.ModelForm), a.prototype.createFields = function () {
                return {
                    iconUrl: new d.ImageURLField("Icon url", {
                        imageUrls: ["img/icons/glyphish/21-skull.png", "img/icons/glyphish/07-map-marker.png", "img/icons/glyphish/08-chat.png", "img/icons/glyphish/10-medical.png", "img/icons/glyphish/11-clock.png", "img/icons/glyphish/12-eye.png", "img/icons/glyphish/13-target.png", "img/icons/glyphish/14-tag.png", "img/icons/glyphish/18-envelope.png", "img/icons/glyphish/19-gear.png", "img/icons/glyphish/21-skull.png", "img/icons/glyphish/22-skull-n-bones.png", "img/icons/glyphish/23-bird.png", "img/icons/glyphish/24-gift.png", "img/icons/glyphish/25-weather.png", "img/icons/glyphish/26-bandaid.png", "img/icons/glyphish/27-planet.png", "img/icons/glyphish/28-star.png", "img/icons/glyphish/29-heart.png", "img/icons/glyphish/52-pine-tree.png", "img/icons/glyphish/53-house.png", "img/icons/glyphish/56-cloud.png", "img/icons/glyphish/64-zap.png", "img/icons/glyphish/71-compass.png", "img/icons/glyphish/76-baby.png", "img/icons/glyphish/82-dog-paw.png", "img/icons/glyphish/84-lightbulb.png", "img/icons/glyphish/90-life-buoy.png", "img/icons/glyphish/94-pill.png", "img/icons/glyphish/99-umbrella.png", "img/icons/glyphish/102-walk.png", "img/icons/glyphish/109-chicken.png", "img/icons/glyphish/110-bug.png", "img/icons/glyphish/111-user.png", "img/icons/glyphish/112-group.png", "img/icons/glyphish/113-navigation.png", "img/icons/glyphish/114-balloon.png", "img/icons/glyphish/116-controller.png", "img/icons/glyphish/119-piggy-bank.png", "img/icons/glyphish/132-ghost.png", "img/icons/glyphish/133-ufo.png", "img/icons/glyphish/134-viking.png", "img/icons/glyphish/136-tractor.png", "img/icons/glyphish/145-persondot.png", "img/icons/glyphish/170-butterfly.png", "img/icons/glyphish/171-sun.png", "img/icons/glyphish/195-barcode.png", "img/icons/glyphish/196-radiation.png"]
                    }),
                    labelColor: new d.ColorField("Label color")
                }
            }, a
        }(), k = function () {
            function a() {
                a.__super__.constructor.apply(this, arguments)
            }
            return b(a, d.ModelForm), a.prototype.createFields = function () {
                return {
                    shape: new d.FormChooserField("Show as", {
                        box: new d.FormChooserOption("Box", new f({
                            model: this.model
                        })),
                        dot: new d.FormChooserOption("Circle", new f({
                            model: this.model
                        })),
                        icon: new d.FormChooserOption("Icon", new h({
                            model: this.model
                        }))
                    }),
                    label: new d.FieldSet({
                        labelPattern: new d.TextField("Label", {
                            tooltip: i
                        }),
                        labelSize: new d.NumberField("Font size")
                    })
                }
            }, a
        }(), l.NodeStyle = j = function () {
            function d() {
                d.__super__.constructor.apply(this, arguments)
            }
            return b(d, a), d.prototype.defaults = {
                type: "node",
                shape: "box",
                shapeColor: "#000000",
                labelFont: "monospace",
                labelSize: 10,
                labelColor: "#eeeeee",
                labelPattern: "{id}"
            }, d.prototype.getViewClass = function () {
                return k
            }, d.prototype.getLabelPattern = function () {
                return this.get("labelPattern")
            }, d.prototype.applyTo = function (a) {
                var b, d, e, f;
                return (f = a.style) == null && (a.style = {}), e = new RGBColor(this.get("shapeColor")), a.style.shapeStyle = {
                    fill: e.toHex(),
                    shape: this.get("shape")
                }, a.style.iconUrl = this.get("iconUrl"), a.style.labelStyle = {
                    font: this.get("labelFont"),
                    color: this.get("labelColor"),
                    size: this.get("labelSize")
                }, b = this.getLabelCtx(a), d = this.getLabelPattern(), d !== null && d.length > 0 ? a.style.labelText = c.compile(this.getLabelPattern(), b) : a.style.labelText = ""
            }, d.prototype.getLabelCtx = function (a) {
                var b, c, d, e;
                b = {
                    id: "N/A",
                    props: "",
                    prop: {}
                };
                if (a.neoNode) {
                    e = a.neoNode.getProperties();
                    for (c in e) d = e[c], b[c] = JSON.stringify(d);
                    b.id = a.neoNode.getId(), b.props = JSON.stringify(a.neoNode.getProperties()), b.prop = a.neoNode.getProperties()
                }
                return b
            }, d
        }(), l.GroupStyle = g = function () {
            function a() {
                a.__super__.constructor.apply(this, arguments)
            }
            return b(a, j), a.prototype.defaults = {
                type: "group",
                shape: "dot",
                shapeColor: "#590101",
                labelSize: 10,
                labelFont: "monospace",
                labelColor: "#eeeeee",
                labelPattern: "{count};nodes"
            }, a.prototype.getLabelCtx = function (a) {
                return {
                    count: a.group.nodeCount
                }
            }, a
        }(), l
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/filters/Filter", ["ribcage/LocalModel"], function (a) {
        var c;
        return c = function () {
            function c(a, b) {
                c.__super__.constructor.call(this, a, b), this.set({
                    type: this.getType()
                })
            }
            return b(c, a), c.prototype.matches = function (a) {
                return !1
            }, c
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/visualization/models/filters/propertyFilterTemplate", [], function () {
        return function (vars) {
            with(vars || {}) return '<ul><li class="filter"><div class="filterBooleanOp">and</div><div class="filterType">where property</div><input type="text" placeholder="Property name" class="small propertyName" /><select class="method"></select><input type="text" placeholder="A value" style="display:none" class="small compareValue" /></li><li class="removeFilterWrap"><button class="bad-button micro-button removeFilter">X</button></li></ul><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/views/AbstractFilterView", ["neo4j/webadmin/utils/ItemUrlResolver", "ribcage/View"], function (b, d) {
        var e;
        return e = function () {
            function b() {
                this.render = a(this.render, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, d), b.prototype.tagName = "li", b.prototype.initialize = function (a) {
                return this.filter = a.filter, this.filters = a.filters
            }, b.prototype.render = function () {
                return this
            }, b.prototype.deleteFilter = function () {
                return this.filters.remove(this.filter), this.remove()
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/filters/PropertyFilter", ["./Filter", "./propertyFilterTemplate", "../../views/AbstractFilterView"], function (b, d, e) {
        var f, g;
        return g = function () {
            function b() {
                this.compareValueChanged = a(this.compareValueChanged, this), this.propertyNameChanged = a(this.propertyNameChanged, this), this.methodChanged = a(this.methodChanged, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, e), b.prototype.events = {
                "change .method": "methodChanged",
                "change .propertyName": "propertyNameChanged",
                "change .compareValue": "compareValueChanged",
                "click button.removeFilter": "deleteFilter"
            }, b.prototype.render = function () {
                var a, b, c, e, g;
                $(this.el).html(d()), e = $(".method", this.el), e.append("<option value='exists'>exists</option>"), e.append("<option value='!exists'>doesn't exist</option>"), g = f.compareMethods;
                for (c in g) a = g[c], b = a.label, e.append("<option value='" + htmlEscape(c) + "'>" + htmlEscape(b) + "</option>");
                return this.uiSetMethod(this.filter.getMethodName()), this.uiSetPropertyName(this.filter.getPropertyName()), this.uiSetCompareValue(this.filter.getCompareValue()), this
            }, b.prototype.methodChanged = function () {
                var a;
                return a = $(".method", this.el).val(), this.uiSetMethod(a), this.filter.setMethodName(a)
            }, b.prototype.propertyNameChanged = function () {
                var a;
                return a = $(".propertyName", this.el).val(), this.filter.setPropertyName(a)
            }, b.prototype.compareValueChanged = function () {
                var a;
                return a = $(".compareValue", this.el).val(), this.filter.setCompareValue(a)
            }, b.prototype.uiSetMethod = function (a) {
                return $(".method", this.el).val(a), f.compareMethods[a] != null ? $(".compareValue", this.el).show() : $(".compareValue", this.el).hide()
            }, b.prototype.uiSetPropertyName = function (a) {
                return $(".propertyName", this.el).val(a)
            }, b.prototype.uiSetCompareValue = function (a) {
                return $(".compareValue", this.el).val(a)
            }, b
        }(), f = function () {
            function d() {
                this.matches = a(this.matches, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.type = "propertyFilter", d.compareMethods = {
                "==": {
                    label: "is",
                    cmp: function (a, b) {
                        return a === b
                    }
                },
                "!=": {
                    label: "isn't",
                    cmp: function (a, b) {
                        return a !== b
                    }
                },
                ">": {
                    label: ">",
                    cmp: function (a, b) {
                        return a > b
                    }
                },
                "<": {
                    label: "<",
                    cmp: function (a, b) {
                        return a < b
                    }
                },
                ">=": {
                    label: ">=",
                    cmp: function (a, b) {
                        return a >= b
                    }
                },
                "<=": {
                    label: "<=",
                    cmp: function (a, b) {
                        return a <= b
                    }
                }
            }, d.prototype.defaults = {
                method: "exists"
            }, d.prototype.getViewClass = function () {
                return g
            }, d.prototype.getType = function () {
                return d.type
            }, d.prototype.getMethodName = function () {
                return this.get("method")
            }, d.prototype.getPropertyName = function () {
                return this.get("propertyName")
            }, d.prototype.getCompareValue = function () {
                return this.get("compareValue")
            }, d.prototype.setMethodName = function (a) {
                return this.set("method", a)
            }, d.prototype.setPropertyName = function (a) {
                return this.set("propertyName", a)
            }, d.prototype.setCompareValue = function (a) {
                return this.set("compareValue", a)
            }, d.prototype.matches = function (a) {
                var b, c, e, f, g;
                e = this.getMethodName();
                if (a.neoNode != null) {
                    f = a.neoNode;
                    if (e === "exists") return f.hasProperty(this.getPropertyName());
                    if (e === "!exists") return !f.hasProperty(this.getPropertyName());
                    if (d.compareMethods[e] != null) return b = d.compareMethods[e].cmp, g = f.getProperty(this.getPropertyName()), c = this.getCompareValue(), _(g).isNumber() && (c = Number(c)), b(g, c)
                }
                return !1
            }, d
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/filters/GroupSizeFilter", ["./Filter", "ribcage/View"], function (a, c) {
        var d, e;
        return e = function () {
            function a() {
                a.__super__.constructor.apply(this, arguments)
            }
            return b(a, c), a
        }(), d = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a), c.name = "groupSizeFilter", c.view = e, c
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/Filters", ["./filters/PropertyFilter", "./filters/GroupSizeFilter", "ribcage/LocalCollection"], function (a, c, d) {
        var e, f, g, h, i, j;
        h = [a, c], g = {};
        for (i = 0, j = h.length; i < j; i++) f = h[i], g[f.type] = f;
        return e = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, d), c.prototype.filters = g, c.prototype.deserializeItem = function (b) {
                b.type === "d" && (b.type = a.type);
                if (this.filters[b.type] != null) return new this.filters[b.type](b);
                throw new Error("Unknown filter type '" + b.type + "' for visualization profile")
            }, c
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        }, c = function (a, b) {
            return function () {
                return a.apply(b, arguments)
            }
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/StyleRule", ["./Filters", "./style", "ribcage/LocalModel"], function (a, d, e) {
        var f, g;
        return f = d.NodeStyle, g = function () {
            function g() {
                g.__super__.constructor.apply(this, arguments)
            }
            return b(g, e), g.prototype.defaults = {
                target: "node",
                style: {},
                order: 0
            }, g.prototype.initialize = function () {
                return this.initNestedModel("filters", a), this.initNestedModel("style", {
                    deserialize: c(function (a) {
                        return new f(a)
                    }, this)
                })
            }, g.prototype.setTarget = function (a) {
                return this.set({
                    target: a
                })
            }, g.prototype.getTarget = function () {
                return this.get("target")
            }, g.prototype.getStyle = function () {
                return this.style
            }, g.prototype.setStyle = function (a) {
                return this.style = a
            }, g.prototype.getOrder = function () {
                return this.get("order")
            }, g.prototype.setOrder = function (a) {
                return this.set("order", a)
            }, g.prototype.getTargetEntity = function () {
                return this.getTarget().split(":")[0]
            }, g.prototype.getTargetEntityType = function () {
                return this.getTarget().split(":")[1]
            }, g.prototype.hasTargetEntityType = function () {
                return this.getTarget().split(":").length > 1
            }, g.prototype.appliesTo = function (a, b) {
                var c, d, e, f;
                if (b !== this.getTargetEntity() || this.hasTargetEntityType() && a.type !== this.getTargetEntityType()) return !1;
                f = this.filters.models;
                for (d = 0, e = f.length; d < e; d++) {
                    c = f[d];
                    if (!c.matches(a)) return !1
                }
                return !0
            }, g.prototype.applyStyleTo = function (a) {
                d = this.getStyle();
                if (d != null) return d.applyTo(a)
            }, g
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/StyleRules", ["./StyleRule", "ribcage/LocalCollection"], function (b, d) {
        var e;
        return e = function () {
            function e() {
                this.addLast = a(this.addLast, this), e.__super__.constructor.apply(this, arguments)
            }
            return c(e, d), e.prototype.model = b, e.prototype.comparator = function (a) {
                return a.getOrder()
            }, e.prototype.addLast = function (a) {
                return this.last() != null && a.setOrder(this.last().getOrder() + 1), this.add(a)
            }, e
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/VisualizationProfile", ["./StyleRules", "./style", "ribcage/LocalModel"], function (a, c, d) {
        var e;
        return e = function () {
            function e() {
                e.__super__.constructor.apply(this, arguments)
            }
            return b(e, d), e.prototype.initialize = function () {
                return this.initNestedModel("styleRules", a), this._defaultNodeStyle = new c.NodeStyle, this._defaultGroupStyle = new c.GroupStyle
            }, e.prototype.setName = function (a) {
                return this.set({
                    name: a
                })
            }, e.prototype.getName = function () {
                return this.get("name")
            }, e.prototype.isDefault = function () {
                return this.get("builtin")
            }, e.prototype.styleNode = function (a) {
                var b, c, d, e, f;
                e = a.type === "group" ? "group" : "node";
                switch (e) {
                    case "group":
                        this._defaultGroupStyle.applyTo(a);
                        break;
                    case "node":
                        this._defaultNodeStyle.applyTo(a)
                }
                d = this.styleRules.models;
                for (b = f = d.length - 1; b >= 0; b += -1) c = d[b], c.appliesTo(a, e) && c.applyStyleTo(a);
                if (a.type === "unexplored") return a.style.shapeStyle.alpha = .2
            }, e
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/databrowser/visualization/views/styleRule", [], function () {
        return function (vars) {
            with(vars || {}) return '<ul class="styleRule"><li class="ruleTitle"><div class="ruleTarget">Nodes</div><button class="bad-button micro-button remove">Remove</button><div class="break"></div><div class="form-sort-handle"></div></li><li class="ruleFilters"><ul class="filters property-list"></ul><ul class="property-list"><li class="property-controls"><button class="micro-button addFilter">Add filter</button><div class="break"></div></li></ul></li><li class="ruleStyle"></li></ul><div class="break"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/views/StyleRuleView", ["neo4j/webadmin/utils/ItemUrlResolver", "./styleRule", "../models/Filters", "../models/filters/PropertyFilter", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f, g, h) {
        var i;
        return i = function () {
            function b() {
                this.render = a(this.render, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, g), b.prototype.tagName = "li", b.prototype.events = {
                "click button.remove": "deleteRule",
                "click button.addFilter": "addFilter",
                "change select.target": "targetChanged"
            }, b.prototype.initialize = function (a) {
                return this.rule = a.rule, this.rules = a.rules
            }, b.prototype.render = function () {
                return h(this.el).html(d()), h(".target", this.el).val(this.rule.getTarget()), this.filterContainer = h(".filters", this.el), this.rule.filters.each(a(function (a) {
                    return this.addFilterElement(a)
                }, this)), this.renderStyleView(), this
            }, b.prototype.renderStyleView = function () {
                var a;
                return a = this.rule.getStyle().getViewClass(), this.styleView = new a({
                    model: this.rule.getStyle()
                }), h(".ruleStyle", this.el).append(this.styleView.render().el)
            }, b.prototype.targetChanged = function (a) {
                return this.rule.setTarget(h(a.target).val())
            }, b.prototype.deleteRule = function () {
                return this.rules.remove(this.rule), this.remove()
            }, b.prototype.addFilter = function () {
                var a;
                return a = new f, this.rule.filters.add(a), this.addFilterElement(a)
            }, b.prototype.addFilterElement = function (a) {
                var b, c;
                return b = a.getViewClass(), c = new b({
                    filter: a,
                    filters: this.rule.filters
                }), this.filterContainer.append(c.render().el)
            }, b.prototype.validates = function () {
                return this.styleView.validates()
            }, b
        }()
    })
}.call(this),
function (a, b) {
    function c(b, c) {
        var e = b.nodeName.toLowerCase();
        return "area" === e ? (c = b.parentNode, e = c.name, !b.href || !e || c.nodeName.toLowerCase() !== "map" ? !1 : (b = a("img[usemap=#" + e + "]")[0], !! b && d(b))) : (/input|select|textarea|button|object/.test(e) ? !b.disabled : "a" == e ? b.href || c : c) && d(b)
    }
    function d(b) {
        return !a(b).parents().andSelf().filter(function () {
            return a.curCSS(this, "visibility") === "hidden" || a.expr.filters.hidden(this)
        }).length
    }
    a.ui = a.ui || {}, a.ui.version || (a.extend(a.ui, {
        version: "1.8.16",
        keyCode: {
            ALT: 18,
            BACKSPACE: 8,
            CAPS_LOCK: 20,
            COMMA: 188,
            COMMAND: 91,
            COMMAND_LEFT: 91,
            COMMAND_RIGHT: 93,
            CONTROL: 17,
            DELETE: 46,
            DOWN: 40,
            END: 35,
            ENTER: 13,
            ESCAPE: 27,
            HOME: 36,
            INSERT: 45,
            LEFT: 37,
            MENU: 93,
            NUMPAD_ADD: 107,
            NUMPAD_DECIMAL: 110,
            NUMPAD_DIVIDE: 111,
            NUMPAD_ENTER: 108,
            NUMPAD_MULTIPLY: 106,
            NUMPAD_SUBTRACT: 109,
            PAGE_DOWN: 34,
            PAGE_UP: 33,
            PERIOD: 190,
            RIGHT: 39,
            SHIFT: 16,
            SPACE: 32,
            TAB: 9,
            UP: 38,
            WINDOWS: 91
        }
    }), a.fn.extend({
        propAttr: a.fn.prop || a.fn.attr,
        _focus: a.fn.focus,
        focus: function (b, c) {
            return typeof b == "number" ? this.each(function () {
                var d = this;
                setTimeout(function () {
                    a(d).focus(), c && c.call(d)
                }, b)
            }) : this._focus.apply(this, arguments)
        },
        scrollParent: function () {
            var b;
            return b = a.browser.msie && /(static|relative)/.test(this.css("position")) || /absolute/.test(this.css("position")) ? this.parents().filter(function () {
                return /(relative|absolute|fixed)/.test(a.curCSS(this, "position", 1)) && /(auto|scroll)/.test(a.curCSS(this, "overflow", 1) + a.curCSS(this, "overflow-y", 1) + a.curCSS(this, "overflow-x", 1))
            }).eq(0) : this.parents().filter(function () {
                return /(auto|scroll)/.test(a.curCSS(this, "overflow", 1) + a.curCSS(this, "overflow-y", 1) + a.curCSS(this, "overflow-x", 1))
            }).eq(0), /fixed/.test(this.css("position")) || !b.length ? a(document) : b
        },
        zIndex: function (c) {
            if (c !== b) return this.css("zIndex", c);
            if (this.length) {
                c = a(this[0]);
                for (var d; c.length && c[0] !== document;) {
                    d = c.css("position");
                    if (d === "absolute" || d === "relative" || d === "fixed") {
                        d = parseInt(c.css("zIndex"), 10);
                        if (!isNaN(d) && d !== 0) return d
                    }
                    c = c.parent()
                }
            }
            return 0
        },
        disableSelection: function () {
            return this.bind((a.support.selectstart ? "selectstart" : "mousedown") + ".ui-disableSelection", function (a) {
                a.preventDefault()
            })
        },
        enableSelection: function () {
            return this.unbind(".ui-disableSelection")
        }
    }), a.each(["Width", "Height"], function (c, d) {
        function e(b, c, d, e) {
            return a.each(f, function () {
                c -= parseFloat(a.curCSS(b, "padding" + this, !0)) || 0, d && (c -= parseFloat(a.curCSS(b, "border" + this + "Width", !0)) || 0), e && (c -= parseFloat(a.curCSS(b, "margin" + this, !0)) || 0)
            }), c
        }
        var f = d === "Width" ? ["Left", "Right"] : ["Top", "Bottom"],
            g = d.toLowerCase(),
            h = {
                innerWidth: a.fn.innerWidth,
                innerHeight: a.fn.innerHeight,
                outerWidth: a.fn.outerWidth,
                outerHeight: a.fn.outerHeight
            };
        a.fn["inner" + d] = function (c) {
            return c === b ? h["inner" + d].call(this) : this.each(function () {
                a(this).css(g, e(this, c) + "px")
            })
        }, a.fn["outer" + d] = function (b, c) {
            return typeof b != "number" ? h["outer" + d].call(this, b) : this.each(function () {
                a(this).css(g, e(this, b, !0, c) + "px")
            })
        }
    }), a.extend(a.expr[":"], {
        data: function (b, c, d) {
            return !!a.data(b, d[3])
        },
        focusable: function (b) {
            return c(b, !isNaN(a.attr(b, "tabindex")))
        },
        tabbable: function (b) {
            var d = a.attr(b, "tabindex"),
                e = isNaN(d);
            return (e || d >= 0) && c(b, !e)
        }
    }), a(function () {
        var b = document.body,
            c = b.appendChild(c = document.createElement("div"));
        a.extend(c.style, {
            minHeight: "100px",
            height: "auto",
            padding: 0,
            borderWidth: 0
        }), a.support.minHeight = c.offsetHeight === 100, a.support.selectstart = "onselectstart" in c, b.removeChild(c).style.display = "none"
    }), a.extend(a.ui, {
        plugin: {
            add: function (b, c, d) {
                b = a.ui[b].prototype;
                for (var e in d) b.plugins[e] = b.plugins[e] || [], b.plugins[e].push([c, d[e]])
            },
            call: function (a, b, c) {
                if ((b = a.plugins[b]) && a.element[0].parentNode) for (var d = 0; d < b.length; d++) a.options[b[d][0]] && b[d][1].apply(a.element, c)
            }
        },
        contains: function (a, b) {
            return document.compareDocumentPosition ? a.compareDocumentPosition(b) & 16 : a !== b && a.contains(b)
        },
        hasScroll: function (b, c) {
            if (a(b).css("overflow") === "hidden") return !1;
            c = c && c === "left" ? "scrollLeft" : "scrollTop";
            var d = !1;
            return b[c] > 0 ? !0 : (b[c] = 1, d = b[c] > 0, b[c] = 0, d)
        },
        isOverAxis: function (a, b, c) {
            return a > b && a < b + c
        },
        isOver: function (b, c, d, e, f, g) {
            return a.ui.isOverAxis(b, d, f) && a.ui.isOverAxis(c, e, g)
        }
    }))
}(jQuery),
function (a, b) {
    if (a.cleanData) {
        var c = a.cleanData;
        a.cleanData = function (b) {
            for (var d = 0, e;
            (e = b[d]) != null; d++) try {
                a(e).triggerHandler("remove")
            } catch (f) {}
            c(b)
        }
    } else {
        var d = a.fn.remove;
        a.fn.remove = function (b, c) {
            return this.each(function () {
                return c || (!b || a.filter(b, [this]).length) && a("*", this).add([this]).each(function () {
                    try {
                        a(this).triggerHandler("remove")
                    } catch (b) {}
                }), d.call(a(this), b, c)
            })
        }
    }
    a.widget = function (b, c, d) {
        var e = b.split(".")[0],
            f;
        b = b.split(".")[1], f = e + "-" + b, d || (d = c, c = a.Widget), a.expr[":"][f] = function (c) {
            return !!a.data(c, b)
        }, a[e] = a[e] || {}, a[e][b] = function (a, b) {
            arguments.length && this._createWidget(a, b)
        }, c = new c, c.options = a.extend(!0, {}, c.options), a[e][b].prototype = a.extend(!0, c, {
            namespace: e,
            widgetName: b,
            widgetEventPrefix: a[e][b].prototype.widgetEventPrefix || b,
            widgetBaseClass: f
        }, d), a.widget.bridge(b, a[e][b])
    }, a.widget.bridge = function (c, d) {
        a.fn[c] = function (e) {
            var f = typeof e == "string",
                g = Array.prototype.slice.call(arguments, 1),
                h = this;
            return e = !f && g.length ? a.extend.apply(null, [!0, e].concat(g)) : e, f && e.charAt(0) === "_" ? h : (f ? this.each(function () {
                var d = a.data(this, c),
                    f = d && a.isFunction(d[e]) ? d[e].apply(d, g) : d;
                if (f !== d && f !== b) return h = f, !1
            }) : this.each(function () {
                var b = a.data(this, c);
                b ? b.option(e || {})._init() : a.data(this, c, new d(e, this))
            }), h)
        }
    }, a.Widget = function (a, b) {
        arguments.length && this._createWidget(a, b)
    }, a.Widget.prototype = {
        widgetName: "widget",
        widgetEventPrefix: "",
        options: {
            disabled: !1
        },
        _createWidget: function (b, c) {
            a.data(c, this.widgetName, this), this.element = a(c), this.options = a.extend(!0, {}, this.options, this._getCreateOptions(), b);
            var d = this;
            this.element.bind("remove." + this.widgetName, function () {
                d.destroy()
            }), this._create(), this._trigger("create"), this._init()
        },
        _getCreateOptions: function () {
            return a.metadata && a.metadata.get(this.element[0])[this.widgetName]
        },
        _create: function () {},
        _init: function () {},
        destroy: function () {
            this.element.unbind("." + this.widgetName).removeData(this.widgetName), this.widget().unbind("." + this.widgetName).removeAttr("aria-disabled").removeClass(this.widgetBaseClass + "-disabled ui-state-disabled")
        },
        widget: function () {
            return this.element
        },
        option: function (c, d) {
            var e = c;
            if (arguments.length === 0) return a.extend({}, this.options);
            if (typeof c == "string") {
                if (d === b) return this.options[c];
                e = {}, e[c] = d
            }
            return this._setOptions(e), this
        },
        _setOptions: function (b) {
            var c = this;
            return a.each(b, function (a, b) {
                c._setOption(a, b)
            }), this
        },
        _setOption: function (a, b) {
            return this.options[a] = b, a === "disabled" && this.widget()[b ? "addClass" : "removeClass"](this.widgetBaseClass + "-disabled ui-state-disabled").attr("aria-disabled", b), this
        },
        enable: function () {
            return this._setOption("disabled", !1)
        },
        disable: function () {
            return this._setOption("disabled", !0)
        },
        _trigger: function (b, c, d) {
            var e = this.options[b];
            c = a.Event(c), c.type = (b === this.widgetEventPrefix ? b : this.widgetEventPrefix + b).toLowerCase(), d = d || {};
            if (c.originalEvent) {
                b = a.event.props.length;
                for (var f; b;) f = a.event.props[--b], c[f] = c.originalEvent[f]
            }
            return this.element.trigger(c, d), !(a.isFunction(e) && e.call(this.element[0], c, d) === !1 || c.isDefaultPrevented())
        }
    }
}(jQuery),
function (a) {
    var b = !1;
    a(document).mouseup(function () {
        b = !1
    }), a.widget("ui.mouse", {
        options: {
            cancel: ":input,option",
            distance: 1,
            delay: 0
        },
        _mouseInit: function () {
            var b = this;
            this.element.bind("mousedown." + this.widgetName, function (a) {
                return b._mouseDown(a)
            }).bind("click." + this.widgetName, function (c) {
                if (!0 === a.data(c.target, b.widgetName + ".preventClickEvent")) return a.removeData(c.target, b.widgetName + ".preventClickEvent"), c.stopImmediatePropagation(), !1
            }), this.started = !1
        },
        _mouseDestroy: function () {
            this.element.unbind("." + this.widgetName)
        },
        _mouseDown: function (c) {
            if (!b) {
                this._mouseStarted && this._mouseUp(c), this._mouseDownEvent = c;
                var e = this,
                    f = c.which == 1,
                    g = typeof this.options.cancel == "string" && c.target.nodeName ? a(c.target).closest(this.options.cancel).length : !1;
                if (!f || g || !this._mouseCapture(c)) return !0;
                this.mouseDelayMet = !this.options.delay, this.mouseDelayMet || (this._mouseDelayTimer = setTimeout(function () {
                    e.mouseDelayMet = !0
                }, this.options.delay));
                if (this._mouseDistanceMet(c) && this._mouseDelayMet(c)) {
                    this._mouseStarted = this._mouseStart(c) !== !1;
                    if (!this._mouseStarted) return c.preventDefault(), !0
                }
                return !0 === a.data(c.target, this.widgetName + ".preventClickEvent") && a.removeData(c.target, this.widgetName + ".preventClickEvent"), this._mouseMoveDelegate = function (a) {
                    return e._mouseMove(a)
                }, this._mouseUpDelegate = function (a) {
                    return e._mouseUp(a)
                }, a(document).bind("mousemove." + this.widgetName, this._mouseMoveDelegate).bind("mouseup." + this.widgetName, this._mouseUpDelegate), c.preventDefault(), b = !0
            }
        },
        _mouseMove: function (b) {
            return a.browser.msie && document.documentMode < 9 && !b.button ? this._mouseUp(b) : this._mouseStarted ? (this._mouseDrag(b), b.preventDefault()) : (this._mouseDistanceMet(b) && this._mouseDelayMet(b) && ((this._mouseStarted = this._mouseStart(this._mouseDownEvent, b) !== !1) ? this._mouseDrag(b) : this._mouseUp(b)), !this._mouseStarted)
        },
        _mouseUp: function (b) {
            return a(document).unbind("mousemove." + this.widgetName, this._mouseMoveDelegate).unbind("mouseup." + this.widgetName, this._mouseUpDelegate), this._mouseStarted && (this._mouseStarted = !1, b.target == this._mouseDownEvent.target && a.data(b.target, this.widgetName + ".preventClickEvent", !0), this._mouseStop(b)), !1
        },
        _mouseDistanceMet: function (a) {
            return Math.max(Math.abs(this._mouseDownEvent.pageX - a.pageX), Math.abs(this._mouseDownEvent.pageY - a.pageY)) >= this.options.distance
        },
        _mouseDelayMet: function () {
            return this.mouseDelayMet
        },
        _mouseStart: function () {},
        _mouseDrag: function () {},
        _mouseStop: function () {},
        _mouseCapture: function () {
            return !0
        }
    })
}(jQuery),
function (a) {
    a.widget("ui.sortable", a.ui.mouse, {
        widgetEventPrefix: "sort",
        options: {
            appendTo: "parent",
            axis: !1,
            connectWith: !1,
            containment: !1,
            cursor: "auto",
            cursorAt: !1,
            dropOnEmpty: !0,
            forcePlaceholderSize: !1,
            forceHelperSize: !1,
            grid: !1,
            handle: !1,
            helper: "original",
            items: "> *",
            opacity: !1,
            placeholder: !1,
            revert: !1,
            scroll: !0,
            scrollSensitivity: 20,
            scrollSpeed: 20,
            scope: "default",
            tolerance: "intersect",
            zIndex: 1e3
        },
        _create: function () {
            var a = this.options;
            this.containerCache = {}, this.element.addClass("ui-sortable"), this.refresh(), this.floating = this.items.length ? a.axis === "x" || /left|right/.test(this.items[0].item.css("float")) || /inline|table-cell/.test(this.items[0].item.css("display")) : !1, this.offset = this.element.offset(), this._mouseInit()
        },
        destroy: function () {
            this.element.removeClass("ui-sortable ui-sortable-disabled").removeData("sortable").unbind(".sortable"), this._mouseDestroy();
            for (var a = this.items.length - 1; a >= 0; a--) this.items[a].item.removeData("sortable-item");
            return this
        },
        _setOption: function (b, c) {
            b === "disabled" ? (this.options[b] = c, this.widget()[c ? "addClass" : "removeClass"]("ui-sortable-disabled")) : a.Widget.prototype._setOption.apply(this, arguments)
        },
        _mouseCapture: function (b, c) {
            if (this.reverting) return !1;
            if (this.options.disabled || this.options.type == "static") return !1;
            this._refreshItems(b);
            var e = null,
                f = this;
            a(b.target).parents().each(function () {
                if (a.data(this, "sortable-item") == f) return e = a(this), !1
            }), a.data(b.target, "sortable-item") == f && (e = a(b.target));
            if (!e) return !1;
            if (this.options.handle && !c) {
                var g = !1;
                a(this.options.handle, e).find("*").andSelf().each(function () {
                    this == b.target && (g = !0)
                });
                if (!g) return !1
            }
            return this.currentItem = e, this._removeCurrentsFromItems(), !0
        },
        _mouseStart: function (b, c, e) {
            c = this.options;
            var f = this;
            this.currentContainer = this, this.refreshPositions(), this.helper = this._createHelper(b), this._cacheHelperProportions(), this._cacheMargins(), this.scrollParent = this.helper.scrollParent(), this.offset = this.currentItem.offset(), this.offset = {
                top: this.offset.top - this.margins.top,
                left: this.offset.left - this.margins.left
            }, this.helper.css("position", "absolute"), this.cssPosition = this.helper.css("position"), a.extend(this.offset, {
                click: {
                    left: b.pageX - this.offset.left,
                    top: b.pageY - this.offset.top
                },
                parent: this._getParentOffset(),
                relative: this._getRelativeOffset()
            }), this.originalPosition = this._generatePosition(b), this.originalPageX = b.pageX, this.originalPageY = b.pageY, c.cursorAt && this._adjustOffsetFromHelper(c.cursorAt), this.domPosition = {
                prev: this.currentItem.prev()[0],
                parent: this.currentItem.parent()[0]
            }, this.helper[0] != this.currentItem[0] && this.currentItem.hide(), this._createPlaceholder(), c.containment && this._setContainment(), c.cursor && (a("body").css("cursor") && (this._storedCursor = a("body").css("cursor")), a("body").css("cursor", c.cursor)), c.opacity && (this.helper.css("opacity") && (this._storedOpacity = this.helper.css("opacity")), this.helper.css("opacity", c.opacity)), c.zIndex && (this.helper.css("zIndex") && (this._storedZIndex = this.helper.css("zIndex")), this.helper.css("zIndex", c.zIndex)), this.scrollParent[0] != document && this.scrollParent[0].tagName != "HTML" && (this.overflowOffset = this.scrollParent.offset()), this._trigger("start", b, this._uiHash()), this._preserveHelperProportions || this._cacheHelperProportions();
            if (!e) for (e = this.containers.length - 1; e >= 0; e--) this.containers[e]._trigger("activate", b, f._uiHash(this));
            return a.ui.ddmanager && (a.ui.ddmanager.current = this), a.ui.ddmanager && !c.dropBehaviour && a.ui.ddmanager.prepareOffsets(this, b), this.dragging = !0, this.helper.addClass("ui-sortable-helper"), this._mouseDrag(b), !0
        },
        _mouseDrag: function (b) {
            this.position = this._generatePosition(b), this.positionAbs = this._convertPositionTo("absolute"), this.lastPositionAbs || (this.lastPositionAbs = this.positionAbs);
            if (this.options.scroll) {
                var c = this.options,
                    e = !1;
                this.scrollParent[0] != document && this.scrollParent[0].tagName != "HTML" ? (this.overflowOffset.top + this.scrollParent[0].offsetHeight - b.pageY < c.scrollSensitivity ? this.scrollParent[0].scrollTop = e = this.scrollParent[0].scrollTop + c.scrollSpeed : b.pageY - this.overflowOffset.top < c.scrollSensitivity && (this.scrollParent[0].scrollTop = e = this.scrollParent[0].scrollTop - c.scrollSpeed), this.overflowOffset.left + this.scrollParent[0].offsetWidth - b.pageX < c.scrollSensitivity ? this.scrollParent[0].scrollLeft = e = this.scrollParent[0].scrollLeft + c.scrollSpeed : b.pageX - this.overflowOffset.left < c.scrollSensitivity && (this.scrollParent[0].scrollLeft = e = this.scrollParent[0].scrollLeft - c.scrollSpeed)) : (b.pageY - a(document).scrollTop() < c.scrollSensitivity ? e = a(document).scrollTop(a(document).scrollTop() - c.scrollSpeed) : a(window).height() - (b.pageY - a(document).scrollTop()) < c.scrollSensitivity && (e = a(document).scrollTop(a(document).scrollTop() + c.scrollSpeed)), b.pageX - a(document).scrollLeft() < c.scrollSensitivity ? e = a(document).scrollLeft(a(document).scrollLeft() - c.scrollSpeed) : a(window).width() - (b.pageX - a(document).scrollLeft()) < c.scrollSensitivity && (e = a(document).scrollLeft(a(document).scrollLeft() + c.scrollSpeed))), e !== !1 && a.ui.ddmanager && !c.dropBehaviour && a.ui.ddmanager.prepareOffsets(this, b)
            }
            this.positionAbs = this._convertPositionTo("absolute");
            if (!this.options.axis || this.options.axis != "y") this.helper[0].style.left = this.position.left + "px";
            if (!this.options.axis || this.options.axis != "x") this.helper[0].style.top = this.position.top + "px";
            for (c = this.items.length - 1; c >= 0; c--) {
                e = this.items[c];
                var f = e.item[0],
                    g = this._intersectsWithPointer(e);
                if (g && f != this.currentItem[0] && this.placeholder[g == 1 ? "next" : "prev"]()[0] != f && !a.ui.contains(this.placeholder[0], f) && (this.options.type == "semi-dynamic" ? !a.ui.contains(this.element[0], f) : !0)) {
                    this.direction = g == 1 ? "down" : "up";
                    if (this.options.tolerance == "pointer" || this._intersectsWithSides(e)) this._rearrange(b, e);
                    else break;
                    this._trigger("change", b, this._uiHash());
                    break
                }
            }
            return this._contactContainers(b), a.ui.ddmanager && a.ui.ddmanager.drag(this, b), this._trigger("sort", b, this._uiHash()), this.lastPositionAbs = this.positionAbs, !1
        },
        _mouseStop: function (b, c) {
            if (b) {
                a.ui.ddmanager && !this.options.dropBehaviour && a.ui.ddmanager.drop(this, b);
                if (this.options.revert) {
                    var e = this;
                    c = e.placeholder.offset(), e.reverting = !0, a(this.helper).animate({
                        left: c.left - this.offset.parent.left - e.margins.left + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollLeft),
                        top: c.top - this.offset.parent.top - e.margins.top + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollTop)
                    }, parseInt(this.options.revert, 10) || 500, function () {
                        e._clear(b)
                    })
                } else this._clear(b, c);
                return !1
            }
        },
        cancel: function () {
            var b = this;
            if (this.dragging) {
                this._mouseUp({
                    target: null
                }), this.options.helper == "original" ? this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper") : this.currentItem.show();
                for (var c = this.containers.length - 1; c >= 0; c--) this.containers[c]._trigger("deactivate", null, b._uiHash(this)), this.containers[c].containerCache.over && (this.containers[c]._trigger("out", null, b._uiHash(this)), this.containers[c].containerCache.over = 0)
            }
            return this.placeholder && (this.placeholder[0].parentNode && this.placeholder[0].parentNode.removeChild(this.placeholder[0]), this.options.helper != "original" && this.helper && this.helper[0].parentNode && this.helper.remove(), a.extend(this, {
                helper: null,
                dragging: !1,
                reverting: !1,
                _noFinalSort: null
            }), this.domPosition.prev ? a(this.domPosition.prev).after(this.currentItem) : a(this.domPosition.parent).prepend(this.currentItem)), this
        },
        serialize: function (b) {
            var c = this._getItemsAsjQuery(b && b.connected),
                e = [];
            return b = b || {}, a(c).each(function () {
                var c = (a(b.item || this).attr(b.attribute || "id") || "").match(b.expression || /(.+)[-=_](.+)/);
                c && e.push((b.key || c[1] + "[]") + "=" + (b.key && b.expression ? c[1] : c[2]))
            }), !e.length && b.key && e.push(b.key + "="), e.join("&")
        },
        toArray: function (b) {
            var c = this._getItemsAsjQuery(b && b.connected),
                e = [];
            return b = b || {}, c.each(function () {
                e.push(a(b.item || this).attr(b.attribute || "id") || "")
            }), e
        },
        _intersectsWith: function (a) {
            var b = this.positionAbs.left,
                c = b + this.helperProportions.width,
                d = this.positionAbs.top,
                e = d + this.helperProportions.height,
                f = a.left,
                g = f + a.width,
                h = a.top,
                i = h + a.height,
                j = this.offset.click.top,
                k = this.offset.click.left;
            return j = d + j > h && d + j < i && b + k > f && b + k < g, this.options.tolerance == "pointer" || this.options.forcePointerForContainers || this.options.tolerance != "pointer" && this.helperProportions[this.floating ? "width" : "height"] > a[this.floating ? "width" : "height"] ? j : f < b + this.helperProportions.width / 2 && c - this.helperProportions.width / 2 < g && h < d + this.helperProportions.height / 2 && e - this.helperProportions.height / 2 < i
        },
        _intersectsWithPointer: function (b) {
            var c = a.ui.isOverAxis(this.positionAbs.top + this.offset.click.top, b.top, b.height);
            b = a.ui.isOverAxis(this.positionAbs.left + this.offset.click.left, b.left, b.width), c = c && b, b = this._getDragVerticalDirection();
            var e = this._getDragHorizontalDirection();
            return c ? this.floating ? e && e == "right" || b == "down" ? 2 : 1 : b && (b == "down" ? 2 : 1) : !1
        },
        _intersectsWithSides: function (b) {
            var c = a.ui.isOverAxis(this.positionAbs.top + this.offset.click.top, b.top + b.height / 2, b.height);
            b = a.ui.isOverAxis(this.positionAbs.left + this.offset.click.left, b.left + b.width / 2, b.width);
            var e = this._getDragVerticalDirection(),
                f = this._getDragHorizontalDirection();
            return this.floating && f ? f == "right" && b || f == "left" && !b : e && (e == "down" && c || e == "up" && !c)
        },
        _getDragVerticalDirection: function () {
            var a = this.positionAbs.top - this.lastPositionAbs.top;
            return a != 0 && (a > 0 ? "down" : "up")
        },
        _getDragHorizontalDirection: function () {
            var a = this.positionAbs.left - this.lastPositionAbs.left;
            return a != 0 && (a > 0 ? "right" : "left")
        },
        refresh: function (a) {
            return this._refreshItems(a), this.refreshPositions(), this
        },
        _connectWith: function () {
            var a = this.options;
            return a.connectWith.constructor == String ? [a.connectWith] : a.connectWith
        },
        _getItemsAsjQuery: function (b) {
            var c = [],
                e = [],
                f = this._connectWith();
            if (f && b) for (b = f.length - 1; b >= 0; b--) for (var g = a(f[b]), h = g.length - 1; h >= 0; h--) {
                var i = a.data(g[h], "sortable");
                i && i != this && !i.options.disabled && e.push([a.isFunction(i.options.items) ? i.options.items.call(i.element) : a(i.options.items, i.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), i])
            }
            e.push([a.isFunction(this.options.items) ? this.options.items.call(this.element, null, {
                options: this.options,
                item: this.currentItem
            }) : a(this.options.items, this.element).not(".ui-sortable-helper").not(".ui-sortable-placeholder"), this]);
            for (b = e.length - 1; b >= 0; b--) e[b][0].each(function () {
                c.push(this)
            });
            return a(c)
        },
        _removeCurrentsFromItems: function () {
            for (var a = this.currentItem.find(":data(sortable-item)"), b = 0; b < this.items.length; b++) for (var c = 0; c < a.length; c++) a[c] == this.items[b].item[0] && this.items.splice(b, 1)
        },
        _refreshItems: function (b) {
            this.items = [], this.containers = [this];
            var c = this.items,
                e = [
                    [a.isFunction(this.options.items) ? this.options.items.call(this.element[0], b, {
                        item: this.currentItem
                    }) : a(this.options.items, this.element), this]
                ],
                f = this._connectWith();
            if (f) for (var g = f.length - 1; g >= 0; g--) for (var h = a(f[g]), i = h.length - 1; i >= 0; i--) {
                var j = a.data(h[i], "sortable");
                j && j != this && !j.options.disabled && (e.push([a.isFunction(j.options.items) ? j.options.items.call(j.element[0], b, {
                    item: this.currentItem
                }) : a(j.options.items, j.element), j]), this.containers.push(j))
            }
            for (g = e.length - 1; g >= 0; g--) {
                b = e[g][1], f = e[g][0], i = 0;
                for (h = f.length; i < h; i++) j = a(f[i]), j.data("sortable-item", b), c.push({
                    item: j,
                    instance: b,
                    width: 0,
                    height: 0,
                    left: 0,
                    top: 0
                })
            }
        },
        refreshPositions: function (b) {
            this.offsetParent && this.helper && (this.offset.parent = this._getParentOffset());
            for (var c = this.items.length - 1; c >= 0; c--) {
                var e = this.items[c];
                if (e.instance == this.currentContainer || !this.currentContainer || e.item[0] == this.currentItem[0]) {
                    var f = this.options.toleranceElement ? a(this.options.toleranceElement, e.item) : e.item;
                    b || (e.width = f.outerWidth(), e.height = f.outerHeight()), f = f.offset(), e.left = f.left, e.top = f.top
                }
            }
            if (this.options.custom && this.options.custom.refreshContainers) this.options.custom.refreshContainers.call(this);
            else for (c = this.containers.length - 1; c >= 0; c--) f = this.containers[c].element.offset(), this.containers[c].containerCache.left = f.left, this.containers[c].containerCache.top = f.top, this.containers[c].containerCache.width = this.containers[c].element.outerWidth(), this.containers[c].containerCache.height = this.containers[c].element.outerHeight();
            return this
        },
        _createPlaceholder: function (b) {
            var c = b || this,
                e = c.options;
            if (!e.placeholder || e.placeholder.constructor == String) {
                var f = e.placeholder;
                e.placeholder = {
                    element: function () {
                        var b = a(document.createElement(c.currentItem[0].nodeName)).addClass(f || c.currentItem[0].className + " ui-sortable-placeholder").removeClass("ui-sortable-helper")[0];
                        return f || (b.style.visibility = "hidden"), b
                    },
                    update: function (a, b) {
                        if (!f || !! e.forcePlaceholderSize) b.height() || b.height(c.currentItem.innerHeight() - parseInt(c.currentItem.css("paddingTop") || 0, 10) - parseInt(c.currentItem.css("paddingBottom") || 0, 10)), b.width() || b.width(c.currentItem.innerWidth() - parseInt(c.currentItem.css("paddingLeft") || 0, 10) - parseInt(c.currentItem.css("paddingRight") || 0, 10))
                    }
                }
            }
            c.placeholder = a(e.placeholder.element.call(c.element, c.currentItem)), c.currentItem.after(c.placeholder), e.placeholder.update(c, c.placeholder)
        },
        _contactContainers: function (b) {
            for (var c = null, e = null, f = this.containers.length - 1; f >= 0; f--) if (!a.ui.contains(this.currentItem[0], this.containers[f].element[0])) if (this._intersectsWith(this.containers[f].containerCache)) {
                if (!c || !a.ui.contains(this.containers[f].element[0], c.element[0])) c = this.containers[f], e = f
            } else this.containers[f].containerCache.over && (this.containers[f]._trigger("out", b, this._uiHash(this)), this.containers[f].containerCache.over = 0);
            if (c) if (this.containers.length === 1) this.containers[e]._trigger("over", b, this._uiHash(this)), this.containers[e].containerCache.over = 1;
            else if (this.currentContainer != this.containers[e]) {
                c = 1e4, f = null;
                for (var g = this.positionAbs[this.containers[e].floating ? "left" : "top"], h = this.items.length - 1; h >= 0; h--) if (a.ui.contains(this.containers[e].element[0], this.items[h].item[0])) {
                    var i = this.items[h][this.containers[e].floating ? "left" : "top"];
                    Math.abs(i - g) < c && (c = Math.abs(i - g), f = this.items[h])
                }
                if (f || this.options.dropOnEmpty) this.currentContainer = this.containers[e], f ? this._rearrange(b, f, null, !0) : this._rearrange(b, null, this.containers[e].element, !0), this._trigger("change", b, this._uiHash()), this.containers[e]._trigger("change", b, this._uiHash(this)), this.options.placeholder.update(this.currentContainer, this.placeholder), this.containers[e]._trigger("over", b, this._uiHash(this)), this.containers[e].containerCache.over = 1
            }
        },
        _createHelper: function (b) {
            var c = this.options;
            return b = a.isFunction(c.helper) ? a(c.helper.apply(this.element[0], [b, this.currentItem])) : c.helper == "clone" ? this.currentItem.clone() : this.currentItem, b.parents("body").length || a(c.appendTo != "parent" ? c.appendTo : this.currentItem[0].parentNode)[0].appendChild(b[0]), b[0] == this.currentItem[0] && (this._storedCSS = {
                width: this.currentItem[0].style.width,
                height: this.currentItem[0].style.height,
                position: this.currentItem.css("position"),
                top: this.currentItem.css("top"),
                left: this.currentItem.css("left")
            }), (b[0].style.width == "" || c.forceHelperSize) && b.width(this.currentItem.width()), (b[0].style.height == "" || c.forceHelperSize) && b.height(this.currentItem.height()), b
        },
        _adjustOffsetFromHelper: function (b) {
            typeof b == "string" && (b = b.split(" ")), a.isArray(b) && (b = {
                left: +b[0],
                top: +b[1] || 0
            }), "left" in b && (this.offset.click.left = b.left + this.margins.left), "right" in b && (this.offset.click.left = this.helperProportions.width - b.right + this.margins.left), "top" in b && (this.offset.click.top = b.top + this.margins.top), "bottom" in b && (this.offset.click.top = this.helperProportions.height - b.bottom + this.margins.top)
        },
        _getParentOffset: function () {
            this.offsetParent = this.helper.offsetParent();
            var b = this.offsetParent.offset();
            this.cssPosition == "absolute" && this.scrollParent[0] != document && a.ui.contains(this.scrollParent[0], this.offsetParent[0]) && (b.left += this.scrollParent.scrollLeft(), b.top += this.scrollParent.scrollTop());
            if (this.offsetParent[0] == document.body || this.offsetParent[0].tagName && this.offsetParent[0].tagName.toLowerCase() == "html" && a.browser.msie) b = {
                top: 0,
                left: 0
            };
            return {
                top: b.top + (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
                left: b.left + (parseInt(this.offsetParent.css("borderLeftWidth"), 10) || 0)
            }
        },
        _getRelativeOffset: function () {
            if (this.cssPosition == "relative") {
                var a = this.currentItem.position();
                return {
                    top: a.top - (parseInt(this.helper.css("top"), 10) || 0) + this.scrollParent.scrollTop(),
                    left: a.left - (parseInt(this.helper.css("left"), 10) || 0) + this.scrollParent.scrollLeft()
                }
            }
            return {
                top: 0,
                left: 0
            }
        },
        _cacheMargins: function () {
            this.margins = {
                left: parseInt(this.currentItem.css("marginLeft"), 10) || 0,
                top: parseInt(this.currentItem.css("marginTop"), 10) || 0
            }
        },
        _cacheHelperProportions: function () {
            this.helperProportions = {
                width: this.helper.outerWidth(),
                height: this.helper.outerHeight()
            }
        },
        _setContainment: function () {
            var b = this.options;
            b.containment == "parent" && (b.containment = this.helper[0].parentNode);
            if (b.containment == "document" || b.containment == "window") this.containment = [0 - this.offset.relative.left - this.offset.parent.left, 0 - this.offset.relative.top - this.offset.parent.top, a(b.containment == "document" ? document : window).width() - this.helperProportions.width - this.margins.left, (a(b.containment == "document" ? document : window).height() || document.body.parentNode.scrollHeight) - this.helperProportions.height - this.margins.top];
            if (!/^(document|window|parent)$/.test(b.containment)) {
                var c = a(b.containment)[0];
                b = a(b.containment).offset();
                var e = a(c).css("overflow") != "hidden";
                this.containment = [b.left + (parseInt(a(c).css("borderLeftWidth"), 10) || 0) + (parseInt(a(c).css("paddingLeft"), 10) || 0) - this.margins.left, b.top + (parseInt(a(c).css("borderTopWidth"), 10) || 0) + (parseInt(a(c).css("paddingTop"), 10) || 0) - this.margins.top, b.left + (e ? Math.max(c.scrollWidth, c.offsetWidth) : c.offsetWidth) - (parseInt(a(c).css("borderLeftWidth"), 10) || 0) - (parseInt(a(c).css("paddingRight"), 10) || 0) - this.helperProportions.width - this.margins.left, b.top + (e ? Math.max(c.scrollHeight, c.offsetHeight) : c.offsetHeight) - (parseInt(a(c).css("borderTopWidth"), 10) || 0) - (parseInt(a(c).css("paddingBottom"), 10) || 0) - this.helperProportions.height - this.margins.top]
            }
        },
        _convertPositionTo: function (b, c) {
            c || (c = this.position), b = b == "absolute" ? 1 : -1;
            var e = this.cssPosition != "absolute" || this.scrollParent[0] != document && !! a.ui.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent : this.offsetParent,
                f = /(html|body)/i.test(e[0].tagName);
            return {
                top: c.top + this.offset.relative.top * b + this.offset.parent.top * b - (a.browser.safari && this.cssPosition == "fixed" ? 0 : (this.cssPosition == "fixed" ? -this.scrollParent.scrollTop() : f ? 0 : e.scrollTop()) * b),
                left: c.left + this.offset.relative.left * b + this.offset.parent.left * b - (a.browser.safari && this.cssPosition == "fixed" ? 0 : (this.cssPosition == "fixed" ? -this.scrollParent.scrollLeft() : f ? 0 : e.scrollLeft()) * b)
            }
        },
        _generatePosition: function (b) {
            var c = this.options,
                e = this.cssPosition != "absolute" || this.scrollParent[0] != document && !! a.ui.contains(this.scrollParent[0], this.offsetParent[0]) ? this.scrollParent : this.offsetParent,
                f = /(html|body)/i.test(e[0].tagName);
            this.cssPosition == "relative" && (this.scrollParent[0] == document || this.scrollParent[0] == this.offsetParent[0]) && (this.offset.relative = this._getRelativeOffset());
            var g = b.pageX,
                h = b.pageY;
            return this.originalPosition && (this.containment && (b.pageX - this.offset.click.left < this.containment[0] && (g = this.containment[0] + this.offset.click.left), b.pageY - this.offset.click.top < this.containment[1] && (h = this.containment[1] + this.offset.click.top), b.pageX - this.offset.click.left > this.containment[2] && (g = this.containment[2] + this.offset.click.left), b.pageY - this.offset.click.top > this.containment[3] && (h = this.containment[3] + this.offset.click.top)), c.grid && (h = this.originalPageY + Math.round((h - this.originalPageY) / c.grid[1]) * c.grid[1], h = this.containment ? h - this.offset.click.top < this.containment[1] || h - this.offset.click.top > this.containment[3] ? h - this.offset.click.top < this.containment[1] ? h + c.grid[1] : h - c.grid[1] : h : h, g = this.originalPageX + Math.round((g - this.originalPageX) / c.grid[0]) * c.grid[0], g = this.containment ? g - this.offset.click.left < this.containment[0] || g - this.offset.click.left > this.containment[2] ? g - this.offset.click.left < this.containment[0] ? g + c.grid[0] : g - c.grid[0] : g : g)), {
                top: h - this.offset.click.top - this.offset.relative.top - this.offset.parent.top + (a.browser.safari && this.cssPosition == "fixed" ? 0 : this.cssPosition == "fixed" ? -this.scrollParent.scrollTop() : f ? 0 : e.scrollTop()),
                left: g - this.offset.click.left - this.offset.relative.left - this.offset.parent.left + (a.browser.safari && this.cssPosition == "fixed" ? 0 : this.cssPosition == "fixed" ? -this.scrollParent.scrollLeft() : f ? 0 : e.scrollLeft())
            }
        },
        _rearrange: function (a, b, c, d) {
            c ? c[0].appendChild(this.placeholder[0]) : b.item[0].parentNode.insertBefore(this.placeholder[0], this.direction == "down" ? b.item[0] : b.item[0].nextSibling), this.counter = this.counter ? ++this.counter : 1;
            var e = this,
                f = this.counter;
            window.setTimeout(function () {
                f == e.counter && e.refreshPositions(!d)
            }, 0)
        },
        _clear: function (b, c) {
            this.reverting = !1;
            var e = [];
            !this._noFinalSort && this.currentItem.parent().length && this.placeholder.before(this.currentItem), this._noFinalSort = null;
            if (this.helper[0] == this.currentItem[0]) {
                for (var f in this._storedCSS) if (this._storedCSS[f] == "auto" || this._storedCSS[f] == "static") this._storedCSS[f] = "";
                this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper")
            } else this.currentItem.show();
            this.fromOutside && !c && e.push(function (a) {
                this._trigger("receive", a, this._uiHash(this.fromOutside))
            }), (this.fromOutside || this.domPosition.prev != this.currentItem.prev().not(".ui-sortable-helper")[0] || this.domPosition.parent != this.currentItem.parent()[0]) && !c && e.push(function (a) {
                this._trigger("update", a, this._uiHash())
            });
            if (!a.ui.contains(this.element[0], this.currentItem[0])) {
                c || e.push(function (a) {
                    this._trigger("remove", a, this._uiHash())
                });
                for (f = this.containers.length - 1; f >= 0; f--) a.ui.contains(this.containers[f].element[0], this.currentItem[0]) && !c && (e.push(function (a) {
                    return function (b) {
                        a._trigger("receive", b, this._uiHash(this))
                    }
                }.call(this, this.containers[f])), e.push(function (a) {
                    return function (b) {
                        a._trigger("update", b, this._uiHash(this))
                    }
                }.call(this, this.containers[f])))
            }
            for (f = this.containers.length - 1; f >= 0; f--) c || e.push(function (a) {
                return function (b) {
                    a._trigger("deactivate", b, this._uiHash(this))
                }
            }.call(this, this.containers[f])), this.containers[f].containerCache.over && (e.push(function (a) {
                return function (b) {
                    a._trigger("out", b, this._uiHash(this))
                }
            }.call(this, this.containers[f])), this.containers[f].containerCache.over = 0);
            this._storedCursor && a("body").css("cursor", this._storedCursor), this._storedOpacity && this.helper.css("opacity", this._storedOpacity), this._storedZIndex && this.helper.css("zIndex", this._storedZIndex == "auto" ? "" : this._storedZIndex), this.dragging = !1;
            if (this.cancelHelperRemoval) {
                if (!c) {
                    this._trigger("beforeStop", b, this._uiHash());
                    for (f = 0; f < e.length; f++) e[f].call(this, b);
                    this._trigger("stop", b, this._uiHash())
                }
                return !1
            }
            c || this._trigger("beforeStop", b, this._uiHash()), this.placeholder[0].parentNode.removeChild(this.placeholder[0]), this.helper[0] != this.currentItem[0] && this.helper.remove(), this.helper = null;
            if (!c) {
                for (f = 0; f < e.length; f++) e[f].call(this, b);
                this._trigger("stop", b, this._uiHash())
            }
            return this.fromOutside = !1, !0
        },
        _trigger: function () {
            a.Widget.prototype._trigger.apply(this, arguments) === !1 && this.cancel()
        },
        _uiHash: function (b) {
            var c = b || this;
            return {
                helper: c.helper,
                placeholder: c.placeholder || a([]),
                position: c.position,
                originalPosition: c.originalPosition,
                offset: c.positionAbs,
                item: c.currentItem,
                sender: b ? b.element : null
            }
        }
    }), a.extend(a.ui.sortable, {
        version: "1.8.16"
    })
}(jQuery), define("lib/jquery.sortable", function () {}),
function () {
    define("lib/amd/jQuery.sortable", ["order!lib/amd/jQuery", "order!lib/jquery.sortable"], function () {
        return jQuery
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/visualization/views/VisualizationProfileView", ["neo4j/webadmin/utils/ItemUrlResolver", "./visualizationProfile", "../models/VisualizationProfile", "../models/StyleRule", "./StyleRuleView", "ribcage/View", "lib/amd/jQuery", "lib/amd/jQuery.sortable"], function (b, d, e, f, g, h, i) {
        var j;
        return j = function () {
            function b() {
                this.render = a(this.render, this), this.addStyleRule = a(this.addStyleRule, this), this.cancel = a(this.cancel, this), this.save = a(this.save, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, h), b.prototype.events = {
                "click button.save": "save",
                "click button.cancel": "cancel",
                "click button.addStyleRule": "addStyleRule"
            }, b.prototype.initialize = function (a) {
                return this.profiles = a.dataBrowserSettings.getVisualizationProfiles(), this.settings = a.dataBrowserSettings, this.styleViews = []
            }, b.prototype.save = function () {
                var a, b, c, d, e;
                a = i("#profile-name", this.el).val();
                if (a.length === 0) {
                    alert("Please enter a name for this profile.");
                    return
                }
                e = this.styleRuleViews;
                for (c = 0, d = e.length; c < d; c++) {
                    b = e[c];
                    if (!b.validates()) {
                        alert("There are errors in one or more of your style rules, please fix those before saving.");
                        return
                    }
                }
                return this.profile.setName(a), this._updateRuleOrderFromUI(), this.isInCreateMode && (this.profiles.add(this.profile), this.settings.setCurrentVisualizationProfile(this.profile.id)), this.profile.save(), window.location = "#/data/"
            }, b.prototype.cancel = function () {
                return window.location = "#/data/"
            }, b.prototype.addStyleRule = function () {
                var a;
                return a = new f, a.setOrder(this.profile.styleRules.size()), this.profile.styleRules.addLast(a), this.addStyleRuleElement(a)
            }, b.prototype.addStyleRuleElement = function (a) {
                var b, c;
                return c = new g({
                    rule: a,
                    rules: this.profile.styleRules
                }), this.styleRuleViews.push(c), b = i(c.render().el), b.attr("id", "styleRule_" + a.getOrder()), this.styleRuleContainer.append(b)
            }, b.prototype.render = function () {
                var b;
                return i(this.el).html(d({
                    name: this.profile.getName(),
                    isInCreateMode: this.isInCreateMode
                })), this.styleRuleViews = [], this.styleRuleContainer = i(".styleRules", this.el), b = 0, this.profile.styleRules.each(a(function (a) {
                    return this.addStyleRuleElement(a, b++)
                }, this)), this.styleRuleContainer.sortable({
                    handle: ".form-sort-handle"
                }), this
            }, b.prototype.setProfileToManage = function (a) {
                return this.profile = a, this.setIsCreateMode(!1)
            }, b.prototype.setIsCreateMode = function (a) {
                this.isInCreateMode = a;
                if (this.isInCreateMode) return this.profile = new e({
                    name: "",
                    styleRules: [{}]
                })
            }, b.prototype.hasUnsavedChanges = function () {
                return this.profile.name !== i("#profile-name", this.el).val()
            }, b.prototype._updateRuleOrderFromUI = function () {
                var a, b, c, d, e, f;
                c = this.styleRuleContainer.children(), d = function () {
                    var a, d, e;
                    e = [];
                    for (a = 0, d = c.length; a < d; a++) b = c[a], e.push(Number(b.id.split("_")[1]));
                    return e
                }(), e = this.profile.styleRules;
                for (a = 0, f = d.length; 0 > f ? a > f : a < f; 0 > f ? a-- : a++) e.models[a].setOrder(d[a]);
                return e.sort()
            }, b
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/models/NodeProxy", ["./PropertyContainer"], function (a) {
        var c;
        return c = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a), c
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/models/NodeList", ["neo4j/webadmin/utils/ItemUrlResolver", "./NodeProxy", "ribcage/Model"], function (b, d, e) {
        var f;
        return f = function () {
            function b() {
                this.getRawNodes = a(this.getRawNodes, this), this.getNodes = a(this.getNodes, this), this.getPropertyKeys = a(this.getPropertyKeys, this), this.setRawNodes = a(this.setRawNodes, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, e), b.prototype.initialize = function (a) {
                return this.setRawNodes(a || [])
            }, b.prototype.setRawNodes = function (a) {
                var b, c, e, f, g, h, i, j, k;
                this.set({
                    rawNodes: a
                }), g = [], e = {};
                for (i = 0, j = a.length; i < j; i++) {
                    c = a[i], k = c.getProperties();
                    for (b in k) h = k[b], e[b] = !0;
                    g.push(new d(c))
                }
                return f = function () {
                    var a;
                    a = [];
                    for (b in e) h = e[b], a.push(b);
                    return a
                }(), this.set({
                    propertyKeys: f
                }), this.set({
                    nodes: g
                })
            }, b.prototype.getPropertyKeys = function () {
                return this.get("propertyKeys")
            }, b.prototype.getNodes = function () {
                return this.get("nodes")
            }, b.prototype.getRawNodes = function () {
                return this.get("rawNodes")
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/models/RelationshipProxy", ["./PropertyContainer"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.getStartId = a(this.getStartId, this), this.getEndId = a(this.getEndId, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.getEndId = function () {
                return this.urlResolver.extractRelationshipId(this.getItem().getEndNodeUrl())
            }, d.prototype.getStartId = function () {
                return this.urlResolver.extractRelationshipId(this.getItem().getStartNodeUrl())
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/models/RelationshipList", ["neo4j/webadmin/utils/ItemUrlResolver", "./RelationshipProxy", "ribcage/Model"], function (b, d, e) {
        var f;
        return f = function () {
            function b() {
                this.getRawRelationships = a(this.getRawRelationships, this), this.getRelationships = a(this.getRelationships, this), this.getPropertyKeys = a(this.getPropertyKeys, this), this.setRawRelationships = a(this.setRawRelationships, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, e), b.prototype.initialize = function (a) {
                return this.setRawRelationships(a || [])
            }, b.prototype.setRawRelationships = function (a) {
                var b, c, e, f, g, h, i, j, k;
                this.set({
                    rawRelationships: a
                }), g = [], c = {};
                for (i = 0, j = a.length; i < j; i++) {
                    f = a[i], k = f.getProperties();
                    for (b in k) h = k[b], c[b] = !0;
                    g.push(new d(f))
                }
                return e = function () {
                    var a;
                    a = [];
                    for (b in c) h = c[b], a.push(b);
                    return a
                }(), this.set({
                    propertyKeys: e
                }), this.set({
                    relationships: g
                })
            }, b.prototype.getPropertyKeys = function () {
                return this.get("propertyKeys")
            }, b.prototype.getRelationships = function () {
                return this.get("relationships")
            }, b.prototype.getRawRelationships = function () {
                return this.get("rawRelationships")
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/models/DataBrowserState", ["./NodeProxy", "./NodeList", "./RelationshipProxy", "./RelationshipList", "ribcage/Model"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function h() {
                this.setData = a(this.setData, this), this.setQuery = a(this.setQuery, this), this.dataIsSingleRelationship = a(this.dataIsSingleRelationship, this), this.dataIsSingleNode = a(this.dataIsSingleNode, this), this.getDataType = a(this.getDataType, this), this.getData = a(this.getData, this), this.getQuery = a(this.getQuery, this), this.initialize = a(this.initialize, this), h.__super__.constructor.apply(this, arguments)
            }
            return c(h, g), h.prototype.defaults = {
                type: null,
                data: null,
                query: null,
                queryOutOfSyncWithData: !0
            }, h.prototype.initialize = function (a) {
                return this.server = a.server
            }, h.prototype.getQuery = function () {
                return this.get("query")
            }, h.prototype.getData = function () {
                return this.get("data")
            }, h.prototype.getDataType = function () {
                return this.get("type")
            }, h.prototype.dataIsSingleNode = function () {
                return this.get("type") === "node"
            }, h.prototype.dataIsSingleRelationship = function () {
                return this.get("type") === "relationship"
            }, h.prototype.setQuery = function (a, b, c) {
                b == null && (b = !1), c == null && (c = {});
                if (this.get("query") !== a || c.force === !0) return this.set({
                    queryOutOfSyncWithData: !b
                }, c), this.set({
                    query: a
                }, c)
            }, h.prototype.setData = function (a, c, g) {
                c == null && (c = !0), g == null && (g = {}), this.set({
                    data: a,
                    queryOutOfSyncWithData: !c
                }, {
                    silent: !0
                });
                if (a instanceof neo4j.models.Node) return this.set({
                    type: "node",
                    data: new b(a)
                }, g);
                if (a instanceof neo4j.models.Relationship) return this.set({
                    type: "relationship",
                    data: new e(a)
                }, g);
                if (_(a).isArray() && a.length > 0) {
                    if (a.length === 1) return this.setData(a[0], c, g);
                    if (a[0] instanceof neo4j.models.Relationship) return this.set({
                        type: "relationshipList",
                        data: new f(a)
                    }, g);
                    if (a[0] instanceof neo4j.models.Node) return this.set({
                        type: "nodeList",
                        data: new d(a)
                    }, g)
                } else if (a instanceof neo4j.cypher.QueryResult && a.size() > 0) return this.set({
                    type: "cypher"
                }), this.trigger("change:data");
                return this.set({
                    type: "not-found",
                    data: null
                }, g)
            }, h
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/modules/databrowser/visualization/models/VisualizationProfiles", ["./VisualizationProfile", "ribcage/LocalCollection"], function (a, c) {
        var d;
        return d = function () {
            function d() {
                d.__super__.constructor.apply(this, arguments)
            }
            return b(d, c), d.prototype.model = a, d
        }()
    })
}.call(this),
function () {
    define("neo4j/webadmin/modules/databrowser/DataBrowserSettings", ["./visualization/models/VisualizationProfiles"], function (a) {
        var b;
        return b = function () {
            function b(a) {
                this.settings = a
            }
            return b.prototype.LABEL_PROPERTIES_KEY = "databrowser.labelProperties", b.prototype.LABEL_PROPERTIES_DEFAULT = ["name"], b.prototype.VIZ_PROFILES_KEY = "databrowser.visualization.profiles", b.prototype.VIZ_PROFILES_DEFAULT = [{
                id: 0,
                name: "Default profile",
                builtin: !0
            }], b.prototype.CURRENT_VIZ_PROFILE_KEY = "databrowser.visualization.currentProfile", b.prototype.getLabelProperties = function () {
                var a;
                return a = this.settings.get(this.LABEL_PROPERTIES_KEY), a && _(a).isArray() ? a : this.LABEL_PROPERTIES_DEFAULT
            }, b.prototype.setLabelProperties = function (a) {
                var b;
                return b = {}, b[this.LABEL_PROPERTIES_KEY] = a, this.settings.set(b), this.settings.save()
            }, b.prototype.labelPropertiesChanged = function (a) {
                return this.settings.bind("change:" + this.LABEL_PROPERTIES_KEY, a)
            }, b.prototype.getVisualizationProfiles = function () {
                var b;
                return b = this.settings.get(this.VIZ_PROFILES_KEY, a, this.VIZ_PROFILES_DEFAULT), b.size() === 0 && (this.settings.remove(this.VIZ_PROFILES_KEY), b = this.settings.get(this.VIZ_PROFILES_KEY, a, this.VIZ_PROFILES_DEFAULT)), b
            }, b.prototype.getCurrentVisualizationProfile = function () {
                var a, b;
                return a = this.settings.get(this.CURRENT_VIZ_PROFILE_KEY), b = this.getVisualizationProfiles(), a != null && b.get(a) ? b.get(a) : b.first()
            }, b.prototype.setCurrentVisualizationProfile = function (a) {
                return a.id != null && (a = a.id), this.settings.set(this.CURRENT_VIZ_PROFILE_KEY, a)
            }, b.prototype.onCurrentVisualizationProfileChange = function (a) {
                return this.settings.bind("change:" + this.CURRENT_VIZ_PROFILE_KEY, a)
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/VisualizedView", ["neo4j/webadmin/modules/databrowser/visualization/VisualGraph", "neo4j/webadmin/modules/databrowser/DataBrowserSettings", "neo4j/webadmin/utils/ItemUrlResolver", "./VisualizationSettingsDialog", "ribcage/View", "ribcage/security/HtmlEscaper", "./visualization", "ribcage/ui/Dropdown"], function (b, d, e, f, g, h, i, j) {
        var k, l;
        return k = function () {
            function b(c, d) {
                this.profiles = c, this.settings = d, this.deleteProfile = a(this.deleteProfile, this), b.__super__.constructor.call(this)
            }
            return c(b, j), b.prototype.getItems = function () {
                var b;
                return b = [], b.push(this.title("Profiles")), this.profiles.each(a(function (c) {
                    return b.push(this.actionable(this.renderProfileItem(c), a(function (a) {
                        return this.settings.setCurrentVisualizationProfile(c.id), this.render(), a.stopPropagation()
                    }, this)))
                }, this)), b.push(this.item("<a class='micro-button' href='#/data/visualization/settings/profile/'>New profile</a><div class='break'></div>")), b
            }, b.prototype.renderProfileItem = function (b) {
                var c, d, e, f, g, h, i;
                return e = this.settings.getCurrentVisualizationProfile().id, e === b.id ? d = "selected" : d = "", h = $("<span class='" + d + "'>" + b.getName() + "</span>"), b.isDefault() ? h : (g = $("<a class='micro-button' href='#/data/visualization/settings/profile/" + b.id + "/'>Edit</a>"), g.click(this.hide), f = $("<div class='bad-button micro-button'>Remove</div>"), f.click(a(function (a) {
                    return this.deleteProfile(b), this.render(), a.stopPropagation()
                }, this)), c = $("<div class='dropdown-controls'></div>"), c.append(g), c.append(f), i = $("<div></div>"), i.append(h), i.append(c), i)
            }, b.prototype.deleteProfile = function (a) {
                var b;
                if (confirm("Are you sure?")) return b = this.settings.getCurrentVisualizationProfile().id, a.id === b && this.settings.setCurrentVisualizationProfile(this.profiles.first()), this.profiles.remove(a), this.profiles.save()
            }, b
        }(), l = function () {
            function e() {
                this.attach = a(this.attach, this), this.detach = a(this.detach, this), this.remove = a(this.remove, this), this.clearVisualization = a(this.clearVisualization, this), this.reflowGraphLayout = a(this.reflowGraphLayout, this), this.getViz = a(this.getViz, this), this.render = a(this.render, this), e.__super__.constructor.apply(this, arguments)
            }
            return c(e, g), e.prototype.events = {
                "click #visualization-reflow": "reflowGraphLayout",
                "click #visualization-profiles-button": "showProfilesDropdown",
                "click #visualization-clear": "clearVisualization"
            }, e.prototype.initialize = function (b) {
                return this.server = b.server, this.appState = b.appState, this.dataModel = b.dataModel, this.settings = new d(this.appState.getSettings()), this.dataModel.bind("change:data", this.render), this.settings.onCurrentVisualizationProfileChange(a(function () {
                    return this.getViz().setProfile(this.settings.getCurrentVisualizationProfile())
                }, this))
            }, e.prototype.render = function () {
                this.vizEl != null && this.getViz().detach(), $(this.el).html(i()), this.vizEl = $("#visualization", this.el), this.getViz().attach(this.vizEl);
                switch (this.dataModel.get("type")) {
                    case "node":
                        this.visualizeFromNode(this.dataModel.getData().getItem());
                        break;
                    case "nodeList":
                        this.visualizeFromNodes(this.dataModel.getData().getRawNodes());
                        break;
                    case "relationship":
                        this.visualizeFromRelationships([this.dataModel.getData().getItem()]);
                        break;
                    case "relationshipList":
                        this.visualizeFromRelationships(this.dataModel.getData().getRawRelationships())
                }
                return this
            }, e.prototype.showProfilesDropdown = function () {
                var a;
                return (a = this._profilesDropdown) == null && (this._profilesDropdown = new k(this.settings.getVisualizationProfiles(), this.settings)), this._profilesDropdown.isVisible() ? this._profilesDropdown.hide() : this._profilesDropdown.renderFor($("#visualization-profiles-button"))
            }, e.prototype.visualizeFromNode = function (a) {
                return this.getViz().addNode(a)
            }, e.prototype.visualizeFromNodes = function (a) {
                return this.getViz().addNodes(a)
            }, e.prototype.visualizeFromRelationships = function (b) {
                var c, d, e, f, g, h, i;
                c = 10, f = {}, g = [];
                for (e = 0, i = b.length; 0 > i ? e > i : e < i; 0 > i ? e-- : e++) {
                    h = b[e];
                    if (e < c) f[h.getStartNodeUrl()] == null && (f[h.getStartNodeUrl()] = !0, g.push(h.getStartNode())), f[h.getEndNodeUrl()] == null && (f[h.getStartNodeUrl()] = !0, g.push(h.getEndNode()));
                    else {
                        alert("Only showing the first ten in the set, to avoid crashing the visualization. We're working on adding filtering here!");
                        break
                    }
                }
                return d = neo4j.Promise.join.apply(this, g), d.then(a(function (a) {
                    return this.getViz().addNodes(a)
                }, this))
            }, e.prototype.getViz = function () {
                var a, c, d, e;
                return d = $(document).width() - 40, a = $(document).height() - 160, c = this.settings.getCurrentVisualizationProfile(), (e = this.viz) == null && (this.viz = new b(this.server, c, d, a)), this.viz
            }, e.prototype.reflowGraphLayout = function () {
                if (this.viz != null) return this.viz.reflow()
            }, e.prototype.clearVisualization = function () {
                return this.viz.clear()
            }, e.prototype.remove = function () {
                return this.dataModel.unbind("change:data", this.render), this.getViz().stop(), e.__super__.remove.call(this)
            }, e.prototype.detach = function () {
                return this.dataModel.unbind("change:data", this.render), this.getViz().stop(), e.__super__.detach.call(this)
            }, e.prototype.attach = function (a) {
                e.__super__.attach.call(this, a);
                if (this.vizEl != null) return this.getViz().start(), this.dataModel.bind("change:data", this.render)
            }, e
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/views/DataBrowserView", ["neo4j/webadmin/utils/ItemUrlResolver", "./TabularView", "./VisualizedView", "./CreateRelationshipDialog", "ribcage/View", "./base", "lib/amd/jQuery"], function (b, d, e, f, g, h, i) {
        var j;
        return j = function () {
            function j() {
                this.remove = a(this.remove, this), this.switchToTabularView = a(this.switchToTabularView, this), this.switchToVisualizedView = a(this.switchToVisualizedView, this), this.switchView = a(this.switchView, this), this.consoleKeyPressed = a(this.consoleKeyPressed, this), this.hideCreateRelationshipDialog = a(this.hideCreateRelationshipDialog, this), this.createRelationship = a(this.createRelationship, this), this.createNode = a(this.createNode, this), this.search = a(this.search, this), this.queryChanged = a(this.queryChanged, this), this.renderDataView = a(this.renderDataView, this), this.render = a(this.render, this), j.__super__.constructor.apply(this, arguments)
            }
            return c(j, g), j.prototype.template = h, j.prototype.events = {
                "keypress #data-console": "consoleKeyPressed",
                "click #data-create-node": "createNode",
                "click #data-create-relationship": "createRelationship",
                "click #data-switch-view": "switchView",
                "click #data-execute-console": "search"
            }, j.prototype.initialize = function (a) {
                return this.dataModel = a.dataModel, this.appState = a.state, this.server = a.state.getServer(), this.urlResolver = new b(this.server), this.dataModel.bind("change:query", this.queryChanged), this.switchToTabularView()
            }, j.prototype.render = function () {
                return i(this.el).html(this.template({
                    query: this.dataModel.getQuery(),
                    viewType: this.viewType,
                    dataType: this.dataModel.getDataType()
                })), this.renderDataView()
            }, j.prototype.renderDataView = function () {
                return this.dataView.attach(i("#data-area", this.el).empty()), this.dataView.render(), this
            }, j.prototype.queryChanged = function () {
                return i("#data-console", this.el).val(this.dataModel.getQuery())
            }, j.prototype.search = function (a) {
                return this.dataModel.setQuery(i("#data-console", this.el).val(), !1, {
                    force: !0,
                    silent: !0
                }), this.dataModel.trigger("change:query")
            }, j.prototype.createNode = function () {
                return this.server.node({}).then(a(function (a) {
                    var b;
                    return b = this.urlResolver.extractNodeId(a.getSelf()), this.dataModel.setData(a, !0), this.dataModel.setQuery(b, !0)
                }, this))
            }, j.prototype.createRelationship = function () {
                var a;
                return this.createRelationshipDialog != null ? this.hideCreateRelationshipDialog() : (a = i("#data-create-relationship"), a.addClass("selected"), this.createRelationshipDialog = new f({
                    baseElement: a,
                    dataModel: this.dataModel,
                    server: this.server,
                    closeCallback: this.hideCreateRelationshipDialog
                }))
            }, j.prototype.hideCreateRelationshipDialog = function () {
                if (this.createRelationshipDialog != null) return this.createRelationshipDialog.remove(), delete this.createRelationshipDialog, i("#data-create-relationship").removeClass("selected")
            }, j.prototype.consoleKeyPressed = function (a) {
                if (a.which === 13) return a.stopPropagation(), this.search()
            }, j.prototype.switchView = function (a) {
                return this.viewType === "visualized" ? (a != null && i(a.target).removeClass("tabular"), this.switchToTabularView()) : (a != null && i(a.target).addClass("tabular"), this.switchToVisualizedView()), this.renderDataView()
            }, j.prototype.switchToVisualizedView = function () {
                var a;
                return this.dataView != null && this.dataView.detach(), (a = this.visualizedView) == null && (this.visualizedView = new e({
                    dataModel: this.dataModel,
                    appState: this.appState,
                    server: this.server
                })), this.viewType = "visualized", this.dataView = this.visualizedView
            }, j.prototype.switchToTabularView = function () {
                var a;
                return this.dataView != null && this.dataView.detach(), (a = this.tabularView) == null && (this.tabularView = new d({
                    dataModel: this.dataModel,
                    appState: this.appState,
                    server: this.server
                })), this.viewType = "tabular", this.dataView = this.tabularView
            }, j.prototype.unbind = function () {
                return this.dataModel.unbind("change:query", this.queryChanged)
            }, j.prototype.detach = function () {
                return this.unbind(), this.hideCreateRelationshipDialog(), this.dataView != null && this.dataView.detach(), j.__super__.detach.call(this)
            }, j.prototype.remove = function () {
                return this.unbind(), this.hideCreateRelationshipDialog(), this.dataView.remove()
            }, j
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/databrowser/DataBrowserRouter", ["./search/QueuedSearch", "./views/DataBrowserView", "./visualization/views/VisualizationSettingsView", "./visualization/views/VisualizationProfileView", "./models/DataBrowserState", "./DataBrowserSettings", "ribcage/Router"], function (b, d, e, f, g, h, i) {
        var j;
        return j = function () {
            function j() {
                this.getVisualizationProfileView = a(this.getVisualizationProfileView, this), this.getDataBrowserView = a(this.getDataBrowserView, this), this.showResult = a(this.showResult, this), this.queryChanged = a(this.queryChanged, this), this.switchDataView = a(this.switchDataView, this), this.focusOnSearchField = a(this.focusOnSearchField, this), this.editVisualizationProfile = a(this.editVisualizationProfile, this), this.createVisualizationProfile = a(this.createVisualizationProfile, this), this.visualizationSettings = a(this.visualizationSettings, this), this.search = a(this.search, this), this.base = a(this.base, this), this.init = a(this.init, this), j.__super__.constructor.apply(this, arguments)
            }
            return c(j, i), j.prototype.routes = {
                "/data/": "base",
                "/data/search/*query": "search",
                "/data/visualization/settings/": "visualizationSettings",
                "/data/visualization/settings/profile/": "createVisualizationProfile",
                "/data/visualization/settings/profile/:id/": "editVisualizationProfile"
            }, j.prototype.shortcuts = {
                s: "focusOnSearchField",
                v: "switchDataView"
            }, j.prototype.init = function (a) {
                return this.appState = a, this.server = a.get("server"), this.searcher = new b(this.server), this.dataModel = new g({
                    server: this.server
                }), this.dataModel.bind("change:query", this.queryChanged)
            }, j.prototype.base = function () {
                return this.queryChanged()
            }, j.prototype.search = function (a) {
                this.saveLocation(), a = decodeURIComponent(a);
                while (a.charAt(a.length - 1) === "/") a = a.substr(0, a.length - 1);
                return this.dataModel.setQuery(a), this.appState.set({
                    mainView: this.getDataBrowserView()
                })
            }, j.prototype.visualizationSettings = function () {
                var a;
                return this.saveLocation(), (a = this.visualizationSettingsView) == null && (this.visualizationSettingsView = new e({
                    dataBrowserSettings: this.getDataBrowserSettings()
                })), this.appState.set({
                    mainView: this.visualizationSettingsView
                })
            }, j.prototype.createVisualizationProfile = function () {
                var a;
                return this.saveLocation(), a = this.getVisualizationProfileView(), a.setIsCreateMode(!0), this.appState.set({
                    mainView: a
                })
            }, j.prototype.editVisualizationProfile = function (a) {
                var b, c, d;
                return this.saveLocation(), c = this.getDataBrowserSettings().getVisualizationProfiles(), b = c.get(a), d = this.getVisualizationProfileView(), d.setProfileToManage(b), this.appState.set({
                    mainView: d
                })
            }, j.prototype.focusOnSearchField = function (a) {
                return this.base(), setTimeout(function () {
                    return $("#data-console").focus()
                }, 1)
            }, j.prototype.switchDataView = function (a) {
                return this.getDataBrowserView().switchView()
            }, j.prototype.queryChanged = function () {
                var a, b;
                a = this.dataModel.get("query");
                if (a === null) return this.search("0");
                b = "#/data/search/" + encodeURIComponent(a) + "/", location.hash !== b && (location.hash = b);
                if (this.dataModel.get("queryOutOfSyncWithData")) return this.searcher.exec(this.dataModel.get("query")).then(this.showResult, this.showResult)
            }, j.prototype.showResult = function (a) {
                return this.dataModel.setData(a)
            }, j.prototype.getDataBrowserView = function () {
                var a;
                return (a = this.view) != null ? a : this.view = new d({
                    state: this.appState,
                    dataModel: this.dataModel
                })
            }, j.prototype.getVisualizationProfileView = function () {
                var a;
                return (a = this.visualizationProfileView) != null ? a : this.visualizationProfileView = new f({
                    dataBrowserSettings: this.getDataBrowserSettings()
                })
            }, j.prototype.getDataBrowserSettings = function () {
                var a;
                return (a = this.dataBrowserSettings) != null ? a : this.dataBrowserSettings = new h(this.appState.getSettings())
            }, j
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/models/Console", ["ribcage/Model"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.pushLines = a(this.pushLines, this), this.pushHistory = a(this.pushHistory, this), this.parseEvalResult = a(this.parseEvalResult, this), this.nextHistory = a(this.nextHistory, this), this.prevHistory = a(this.prevHistory, this), this.eval = a(this.eval, this), this.setStatement = a(this.setStatement, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.defaults = {
                lines: [],
                history: [],
                historyIndex: 0,
                showPrompt: !1,
                prompt: "",
                promptPrefix: ""
            }, d.prototype.initialize = function (a) {
                return this.server = a.server, this.lang = a.lang, this.setPromptPrefix("" + this.lang + "> "), this.setStatement("init()"), this.eval(!1, !1)
            }, d.prototype.setStatement = function (a, b) {
                return b == null && (b = {}), this.set({
                    prompt: a
                }, b)
            }, d.prototype.getPromptPrefix = function () {
                return this.get("promptPrefix")
            }, d.prototype.setPromptPrefix = function (a) {
                return this.set({
                    promptPrefix: a
                })
            }, d.prototype.eval = function (a, b, c) {
                var d;
                return a == null && (a = !0), b == null && (b = !0), c == null && (c = this.lang), d = this.get("prompt"), this.set({
                    showPrompt: !1,
                    prompt: ""
                }, {
                    silent: !0
                }), a && this.pushLines([d], this.getPromptPrefix()), b && d !== "" && this.pushHistory(d), this.executeStatement(d)
            }, d.prototype.executeStatement = function (a) {
                return this.server.manage.console.exec(a, this.lang, this.parseEvalResult)
            }, d.prototype.prevHistory = function () {
                var a, b, c;
                a = this.get("history"), b = this.get("historyIndex");
                if (a.length > b) return b++, c = a[a.length - b], this.set({
                    historyIndex: b,
                    prompt: c
                })
            }, d.prototype.nextHistory = function () {
                var a, b, c;
                return a = this.get("history"), b = this.get("historyIndex"), b > 1 ? (b--, c = a[a.length - b], this.set({
                    historyIndex: b,
                    prompt: c
                })) : b === 1 ? (b--, this.set({
                    historyIndex: b,
                    prompt: ""
                })) : this.set({
                    prompt: ""
                })
            }, d.prototype.parseEvalResult = function (a) {
                var b, c;
                return c = a[0], b = a[1], _(c).isString() && c.length > 0 ? (c.substr(-1) === "\n" && (c = c.substr(0, c.length - 1)), c = c.split("\n")) : c = [], b !== null && this.set({
                    promptPrefix: b
                }), this.set({
                    showPrompt: !0
                }, {
                    silent: !0
                }), this.pushLines(c)
            }, d.prototype.pushHistory = function (a) {
                var b;
                if (a.length > 0) {
                    b = this.get("history");
                    if (b.length === 0 || b[b.length - 1] !== a) return this.set({
                        history: b.concat([a]),
                        historyIndex: 0
                    }, {
                        silent: !0
                    })
                }
            }, d.prototype.pushLines = function (a, b) {
                var c;
                return b == null && (b = "==> "), a = function () {
                    var d, e, f;
                    f = [];
                    for (d = 0, e = a.length; d < e; d++) c = a[d], f.push(b + c);
                    return f
                }(), this.set({
                    lines: this.get("lines").concat(a)
                })
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/models/HttpConsole", ["./Console"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.callFailed = a(this.callFailed, this), this.callSucceeded = a(this.callSucceeded, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.statementRegex = /^((GET)|(PUT)|(POST)|(DELETE)) ([^ ]+)( (.+))?$/i, d.prototype.initialize = function (a) {
                return this.server = a.server, this.lang = a.lang, this.setPromptPrefix("" + this.lang + "> "), this.set({
                    showPrompt: !0
                }, {
                    silent: !0
                })
            }, d.prototype.executeStatement = function (a) {
                var b, c, d, e, f;
                if (!this.statementRegex.test(a)) return this.setResult(["Invalid statement."]);
                d = this.statementRegex.exec(a), f = [d[1], d[6], d[8]], c = f[0], e = f[1], b = f[2];
                if (!b) return this.server.web.ajax(c, e, this.callSucceeded, this.callFailed);
                try {
                    return this.server.web.ajax(c, e, JSON.parse(b), this.callSucceeded, this.callFailed)
                } catch (g) {
                    return this.setResult(["Invalid JSON data."])
                }
            }, d.prototype.setResult = function (a) {
                return this.set({
                    showPrompt: !0
                }, {
                    silent: !0
                }), this.pushLines(a)
            }, d.prototype.callSucceeded = function (a, b, c) {
                var d, e;
                return e = [c.status + " " + c.statusText], d = c.responseText.split("\n"), this.setResult(e.concat(d))
            }, d.prototype.callFailed = function (a) {
                return this.callSucceeded(null, null, arguments[0].req)
            }, d
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/console/views/shell", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><p class="pad">The Neo4j Shell, allows you to use the Cypher query language for working with your graph, as well as other powerful shell features.</p><p class="pad">For command reference, see <a href="http://docs.neo4j.org/chunked/${project.version}/shell-commands.html" target="_BLANK">the shell documentation</a>.</p><p class="pad">For Cypher syntax help, see <a href="http://docs.neo4j.org/chunked/${project.version}/cypher-query-lang.html" target="_BLANK">the Cypher documentation</a>.</p><div class="foldout"><h2><a href="#" class="foldout_trigger">Cheat sheet</a></h2><div class="foldout_content"><ul class="info_list"><li><h3>Get node with id 0</h3><p>START a = node(0)</p><p>RETURN a</p></li><li><h3>Get all nodes connects to node with id 0</h3><p>START a = node(0)</p><p>MATCH a-->b</p><p>RETURN b</p></li><li><h3>Get all nodes connected to node with id 0, and the relationships</h3><p>START a = node(0)</p><p>MATCH a-[r]->b</p><p>RETURN r, b</p></li><li><h3>Get all nodes related to node with id 0 with relationship type KNOWS</h3><p>START a = node(0)</p><p>MATCH a-[:KNOWS]->b</p><p>RETURN b</p></li><li><h3>Get all nodes from index "people", with key "name", matching value "Doctor"</h3><p>START a = node:people( name= "Doctor")</p><p>RETURN a</p></li><li><h3>Get all nodes connected to the doctor</h3><p>START doctor = node:people(name="Doctor")</p><p>MATCH doctor-[:ENEMY_OF]->enemy</p><p>WHERE enemy.name = "Cybermen"</p><p>RETURN doctor</p></li><li><h3>Get all friends of friends of the doctor</h3><p>START doctor = node:people( name="Doctor")</p><p>MATCH doctor-[:FRIEND]->friend-[:FRIEND]->friend_of_friend</p><p>RETURN friend_of_friend</p></li></ul></div></div><div class="foldout"><h2><a href="#" class="foldout_trigger">If the console hangs</a></h2><div class="foldout_content"><p class="pad">If the console hangs, you can reset it by clearing your browser cookies for webadmin.</p></div></div></div><div class="workarea with-sidebar" id="console-base"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/console/views/console", [], function () {
        return function (vars) {
            with(vars || {}) return '<div id="console-tabs"><ul class="button-bar grouped"><li><a href="#/console/shell" class="button ' + (current == "shell" ? "current" : "") + '">Neo4j Shell</a></li><li><a href="#/console/gremlin" class="button ' + (current == "gremlin" ? "current" : "") + '">Gremlin</a></li><li><a href="#/console/http" class="button ' + (current == "http" ? "current" : "") + '">HTTP</a></li></ul></div><div class="pad" id="console"><ul>' + function () {
                var a = [],
                    b, c;
                for (b in lines) lines.hasOwnProperty(b) && (c = lines[b], a.push("<li>" + htmlEscape(c, !0) + "</li>"));
                return a.join("")
            }.call(this) + "<li>" + function () {
                return showPrompt ? htmlEscape(promptPrefix) + '<input type="text" value="' + htmlEscape(prompt) + '" id="console-input" />' : ""
            }.call(this) + "</li>" + function () {
                return showMultilineHelp ? '<li class="console-multiline-help">(Hit return again to execute)</li>' : ""
            }.call(this) + "</ul></div>"
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/console/views/gremlin", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><p class="pad">Gremlin Console, allows you to use the <a href="http://gremlin.tinkerpop.com" target="_BLANK">Gremlin graph traversal language</a> for working with your graph. Gremlin is an expressive data flow language that can be used for graph query and manipulation, built on and adding to <a href="http://groovy.codehaus.org/Documentation" target="_BLANK">Groovy</a>.</p><p class="pad">Using Gremlin, you can perform powerful data manipulations. <a href="http://gremlin.tinkerpop.com" target="_BLANK">More information.</a></p><div class="foldout"><h2><a href="#" class="foldout_trigger">Cheat sheet</a></h2><div class="foldout_content"><ul class="info_list"><li><h3>Local graph</h3><p>&gt; g</p></li><li><h3>Set variable</h3><p>&gt; myVar = "My Value"</p></li><li><h3>Get node by id</h3><p>&gt; refNode = g.v(0)</p></li><li><h3>Set property on Node</h3><p>&gt; refNode.name = "Bob"</p></li><li><h3>List all node properties</h3><p>&gt; refNode.map()</p></li><li><h3>Define a properties map</h3><p>&gt; props = [:]</p></li><li><h3>Add a property to the map</h3><p>&gt; props["name"] = "Ariel"</p></li><li><h3>Create a node</h3><p>&gt; secondNode = g.addVertex(props)</p></li><li><h3>Another property map</h3><p>&gt; edgeProps = ["quality":"awesome"]</p></li><li><h3>Create relation</h3><p>&gt; myRelation = g.addEdge(refNode, secondNode, \'KNOWS\', edgeProps)</p></li><li><h3>Remove node or relation</h3><p>&gt; g.removeEdge(myRelation)</p><p>&gt; g.removeVertex(secondNode)</p></li><li><h3>List relations of node</h3></h3><p><ul><li>&gt; refNode.bothE</li><li>&gt; refNode.inE</li><li>&gt; refNode.outE</li><li>&gt; refNode.bothE{it.label=="relation type"}</li></ul></p></li><li><h3>List nodes of relation</h3><p><ul><li>&gt; myRelation.bothV</li><li>&gt; myRelation.inV</li><li>&gt; myRelation.outV</li></ul></p></li></ul></div></div><div class="foldout"><h2><a href="#" class="foldout_trigger">If the console hangs</a></h2><div class="foldout_content"><p class="pad">If the console hangs, you can reset it by clearing your browser cookies for webadmin.</p></div></div></div><div class="workarea with-sidebar" id="console-base"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (a) {
    jQuery.fn.putCursorAtEnd = function () {
        return this.each(function () {
            a(this).focus();
            if (this.setSelectionRange) {
                var b = a(this).val().length * 2;
                this.setSelectionRange(b, b)
            } else a(this).val(a(this).val());
            this.scrollTop = 999999
        })
    }
}(jQuery), define("lib/jquery.putCursorAtEnd", function () {}),
function () {
    define("lib/amd/jQuery.putCursorAtEnd", ["order!lib/amd/jQuery", "order!lib/jquery.putCursorAtEnd"], function () {
        return jQuery
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/views/ConsoleView", ["./gremlin", "./console", "ribcage/View", "lib/amd/jQuery", "lib/amd/jQuery.putCursorAtEnd"], function (b, d, e, f) {
        var g;
        return g = function () {
            function b() {
                this.remove = a(this.remove, this), this.scrollToBottomOfConsole = a(this.scrollToBottomOfConsole, this), this.renderConsole = a(this.renderConsole, this), this.wrapperClicked = a(this.wrapperClicked, this), this.consoleKeyUp = a(this.consoleKeyUp, this), this.initialize = a(this.initialize, this), b.__super__.constructor.apply(this, arguments)
            }
            return c(b, e), b.prototype.events = {
                "keyup #console-input": "consoleKeyUp",
                "click #console-base": "wrapperClicked"
            }, b.prototype.initialize = function (a) {
                return this.appState = a.appState, this.consoleState = a.consoleState, this.lang = a.lang, this.consoleState.bind("change", this.renderConsole)
            }, b.prototype.consoleKeyUp = function (a) {
                var b;
                b = f("#console-input").val(), this.consoleState.setStatement(b, {
                    silent: !0
                }), b.length > 0 ? f(".console-multiline-help", this.el).hide() : f(".console-multiline-help", this.el).show();
                if (a.keyCode === 13) return this.consoleState.eval();
                if (a.keyCode === 38) return this.consoleState.prevHistory();
                if (a.keyCode === 40) return this.consoleState.nextHistory()
            }, b.prototype.wrapperClicked = function (a) {
                return this.focusOnInputField()
            }, b.prototype.focusOnInputField = function () {
                return f("#console-input").focus(), f("#console-input").putCursorAtEnd()
            }, b.prototype.renderConsole = function () {
                return f("#console-base", this.el).html(d({
                    lines: this.consoleState.get("lines"),
                    prompt: this.consoleState.get("prompt"),
                    showPrompt: this.consoleState.get("showPrompt"),
                    showMultilineHelp: this.consoleState.get("showMultilineHelp"),
                    current: this.lang,
                    promptPrefix: this.consoleState.get("promptPrefix")
                })), this.delegateEvents(), this.scrollToBottomOfConsole(), this.focusOnInputField()
            }, b.prototype.scrollToBottomOfConsole = function () {
                var a;
                a = f("#console", this.el);
                if (a[0]) return a[0].scrollTop = a[0].scrollHeight
            }, b.prototype.remove = function () {
                return this.consoleState.unbind("change", this.renderConsole), b.__super__.remove.call(this)
            }, b
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/views/ShellConsoleView", ["./shell", "./console", "ribcage/View", "./ConsoleView", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function d() {
                this.render = a(this.render, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, f), d.prototype.render = function () {
                return g(this.el).html(b({
                    current: "shell"
                })), this.renderConsole(), this
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/views/GremlinConsoleView", ["./gremlin", "./console", "ribcage/View", "./ConsoleView", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function d() {
                this.render = a(this.render, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, f), d.prototype.render = function () {
                return g(this.el).html(b()), this.renderConsole(), this
            }, d
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/console/views/http", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><p class="pad">HTTP Console, for prototyping REST calls to the neo4j server.</p><p class="pad">Syntax:</p><pre class="pad">[HTTP VERB] [URI] [JSON DATA]</pre><pre class="pad">ex: GET /</pre><p class="pad">See the <a href="http://docs.neo4j.org/chunked/milestone/rest-api.html">Neo4j documentation</a> for details on the REST API.</p><div class="foldout"><h2><a href="#" class="foldout_trigger">Cheat sheet</a></h2><div class="foldout_content"><ul class="info_list"><li><h3>Get database description</h3><p>GET /db/data/</p></li><li><h3>Create a node</h3><p>POST /db/data/node {"name":"Steven"}</p></li></ul></div></div><div class="foldout"><h2><a href="#" class="foldout_trigger">If the console hangs</a></h2><div class="foldout_content"><p class="pad">If the console hangs, you can reset it by clearing your browser cookies for webadmin.</p></div></div></div><div class="workarea with-sidebar" id="console-base"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/views/HttpConsoleView", ["./http", "./console", "ribcage/View", "./ConsoleView", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function d() {
                this.render = a(this.render, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, f), d.prototype.render = function () {
                return g(this.el).html(b()), this.renderConsole(), this
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/console/ConsoleRouter", ["./models/Console", "./models/HttpConsole", "./views/ShellConsoleView", "./views/GremlinConsoleView", "./views/HttpConsoleView", "ribcage/Router"], function (b, d, e, f, g, h) {
        var i;
        return i = function () {
            function i() {
                this.getConsoleView = a(this.getConsoleView, this), this.console = a(this.console, this), this.init = a(this.init, this), i.__super__.constructor.apply(this, arguments)
            }
            return c(i, h), i.prototype.routes = {
                "/console/": "console",
                "/console/:type": "console"
            }, i.prototype.consoleType = "shell", i.prototype.init = function (a) {
                return this.appState = a, this.gremlinState = new b({
                    server: this.appState.get("server"),
                    lang: "gremlin"
                }), this.shellState = new b({
                    server: this.appState.get("server"),
                    lang: "shell"
                }), this.httpState = new d({
                    server: this.appState.get("server"),
                    lang: "http"
                }), this.views = {
                    gremlin: new f({
                        appState: this.appState,
                        consoleState: this.gremlinState,
                        lang: "gremlin"
                    }),
                    shell: new e({
                        appState: this.appState,
                        consoleState: this.shellState,
                        lang: "shell"
                    }),
                    http: new g({
                        appState: this.appState,
                        consoleState: this.httpState,
                        lang: "http"
                    })
                }
            }, i.prototype.console = function (a) {
                return a == null && (a = !1), this.saveLocation(), a === !1 && (a = this.consoleType), this.consoleType = a, this.appState.set({
                    mainView: this.getConsoleView(a)
                }), this.getConsoleView(a).focusOnInputField()
            }, i.prototype.getConsoleView = function (a) {
                return this.views[a]
            }, i
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/serverinfo/models/ServerInfo", ["ribcage/Model"], function (b) {
        var d;
        return d = function () {
            function d() {
                this.getBean = a(this.getBean, this), this.setBean = a(this.setBean, this), this.parseJmxBeans = a(this.parseJmxBeans, this), this.fetch = a(this.fetch, this), this.setCurrent = a(this.setCurrent, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.initialize = function (a) {
                return this.server = a.server
            }, d.prototype.setCurrent = function (a, b) {
                return this.current = {
                    domain: a,
                    beanName: b
                }, this.get("domains") != null && this.getBean(a, b) != null ? this.set({
                    current: this.getBean(a, b)
                }) : this.fetch()
            }, d.prototype.fetch = function () {
                return this.server.manage.jmx.query(["*:*"], this.parseJmxBeans)
            }, d.prototype.parseJmxBeans = function (a) {
                var b, c, d, e, f, g, h, i, j, k, l, m, n;
                for (j = 0, l = a.length; j < l; j++) {
                    c = a[j], h = [], n = c.properties;
                    for (g in n) i = n[g], g.slice(0, 4) === "name" && h.push(i);
                    c.name = h.join(" - "), c.name.length === 0 && (c.name = c.jmxName)
                }
                b = "org.neo4j", a = a.sort(function (a, c) {
                    var d, e, f;
                    return e = a.domain === b ? "000" + a.name : a.jmxName, f = c.domain === b ? "000" + c.name : c.jmxName, d = e.toLowerCase() > f.toLowerCase(), d === !0 ? 1 : d === !1 ? -1 : d
                }), f = [], e = null, d = [];
                for (k = 0, m = a.length; k < m; k++) c = a[k], e !== c.domain && (e = c.domain, d = [], f.push({
                    name: c.domain,
                    beans: d
                })), d.push(c), this.setBean(c), this.current != null && this.current.domain === e && this.current.beanName === c.name && this.set({
                    current: c
                }, {
                    silent: !0
                });
                return this.current == null && f.length > 0 && f[0].beans.length > 0 && this.set({
                    current: f[0].beans[0]
                }, {
                    silent: !0
                }), this.set({
                    domains: f
                })
            }, d.prototype.setBean = function (a) {
                var b;
                return b = {}, b["" + a.domain + ":" + a.name] = a, this.set(b, {
                    silent: !0
                })
            }, d.prototype.getBean = function (a, b) {
                return this.get("" + a + ":" + b)
            }, d
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/serverinfo/views/base", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><ol>' + function () {
                var a = [],
                    b, c;
                for (b in domains) domains.hasOwnProperty(b) && (c = domains[b], a.push("<lh>" + htmlEscape(c.name) + "</lh>" + function () {
                    var a = [],
                        b, d;
                    for (b in c.beans) c.beans.hasOwnProperty(b) && (d = c.beans[b], a.push('<li><a href="#/info/' + encodeURIComponent(d.domain) + "/" + encodeURIComponent(d.name) + '/">' + htmlEscape(d.name) + "</a></li>"));
                    return a.join("")
                }.call(this)));
                return a.join("")
            }.call(this) + '</ol></div><div class="workarea with-sidebar" id="info-bean"></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/serverinfo/views/bean", [], function () {
        return function (vars) {
            with(vars || {}) return function () {
                return typeof bean != "undefined" ? '<div class="pad"><h2>' + htmlEscape(bean.name) + '</h2><p class="small">' + htmlEscape(bean.description) + '</p></div><table cellspacing="0" class="data-table"><tbody>' + function () {
                    var a = [],
                        b, c;
                    for (b in attributes) attributes.hasOwnProperty(b) && (c = attributes[b], a.push('<tr><td style="padding-left:' + c.indent * 20 + 'px;"><h3>' + htmlEscape(c.name) + '</h3><p class="small">' + htmlEscape(c.description) + '</p></td><td class="value">' + htmlEscape(c.value) + "</td></tr>"));
                    return a.join("")
                }.call(this) + "</tbody></table>" : ""
            }.call(this) + function () {
                return typeof bean == "undefined" ? '<div class="pad"><h2>No information found</h2><p>Unable to locate any info for the specified JMX bean.</p></div>' : ""
            }.call(this)
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/serverinfo/views/ServerInfoView", ["./base", "./bean", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f) {
        var g;
        return g = function () {
            function g() {
                this.remove = a(this.remove, this), this.flattenAttributes = a(this.flattenAttributes, this), this.renderBean = a(this.renderBean, this), this.render = a(this.render, this), g.__super__.constructor.apply(this, arguments)
            }
            return c(g, e), g.prototype.initialize = function (a) {
                return this.serverInfo = a.serverInfo, this.baseTemplate = b, this.beanTemplate = d, this.serverInfo.bind("change:domains", this.render), this.serverInfo.bind("change:current", this.renderBean)
            }, g.prototype.render = function () {
                return f(this.el).html(this.baseTemplate({
                    domains: this.serverInfo.get("domains")
                })), this.renderBean(), this
            }, g.prototype.renderBean = function () {
                var a;
                return a = this.serverInfo.get("current"), f("#info-bean", this.el).empty().append(this.beanTemplate({
                    bean: a,
                    attributes: a != null ? this.flattenAttributes(a.attributes) : []
                })), this
            }, g.prototype.flattenAttributes = function (a, b, c) {
                var d, e, f, g, h;
                b == null && (b = []), c == null && (c = 1);
                for (g = 0, h = a.length; g < h; g++) d = a[g], e = d.name != null ? d.name : d.type != null ? d.type : "", f = {
                    name: e,
                    description: d.description,
                    indent: c
                }, b.push(f), d.value == null ? f.value = "" : _(d.value).isArray() && _(d.value[0]).isString() ? f.value = d.value.join(", ") : _(d.value).isArray() ? (f.value = "", this.flattenAttributes(d.value, b, c + 1)) : typeof d.value == "object" ? (f.value = "", this.flattenAttributes(d.value.value, b, c + 1)) : f.value = d.value;
                return b
            }, g.prototype.remove = function () {
                return this.serverInfo.unbind("change:domains", this.render), this.serverInfo.unbind("change:current", this.renderBean), g.__super__.remove.call(this)
            }, g
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/serverinfo/ServerInfoRouter", ["./models/ServerInfo", "./views/ServerInfoView", "ribcage/Router"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.getServerInfoView = a(this.getServerInfoView, this), this.bean = a(this.bean, this), this.base = a(this.base, this), this.init = a(this.init, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, e), f.prototype.routes = {
                "/info/": "base",
                "/info/:domain/:name/": "bean"
            }, f.prototype.init = function (a) {
                return this.appState = a, this.serverInfo = new b({
                    server: this.appState.get("server")
                }), this.server = this.appState.get("server")
            }, f.prototype.base = function () {
                return this.saveLocation(), this.appState.set({
                    mainView: this.getServerInfoView()
                }), this.serverInfo.fetch()
            }, f.prototype.bean = function (a, b) {
                return this.saveLocation(), this.appState.set({
                    mainView: this.getServerInfoView()
                }), this.serverInfo.setCurrent(decodeURIComponent(a), decodeURIComponent(b))
            }, f.prototype.getServerInfoView = function () {
                var a;
                return (a = this.view) != null ? a : this.view = new d({
                    appState: this.appState,
                    serverInfo: this.serverInfo
                })
            }, f
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/indexmanager/views/base", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="sidebar"><h1 class="pad">Index management</h1><p class="pad">This interface lets you list, create and remove indexes from your database.</p><p class="pad">Note that index creation here is provided for testing purposes, and is only capable of creating default indexes. To create indexes with more complex configurations, please use your Neo4j REST client of choice.</p><p class="pad">Note: You can query your indexes through the data browser, see "syntax help" below the search bar there.</p><div class="foldout"><h2><a href="#" class="foldout_trigger">More about indexes</a></h2><div class="foldout_content pad"><p>Neo4j has two types of indexes, node and relationship indexes. With node indexes you index and find nodes, and with relationship indexes you do the same for relationships.</p><p>Each index has a provider, which is the underlying implementation handling that index. The default provider is lucene, but you can create your own index provides if you like.</p><p>Neo4j indexes take key/value/object triplets ("object" being a node or a relationship), it will index the key/value pair, and associate this with the object provided. After you have indexed a set of key/value/object triplets, you can query the index and get back objects that where indexed with key/value pairs matching your query.</p><p>For instance, if you have "User" nodes in your database, and want to rapidly find them by username or email, you could create a node index named "Users", and for each user index username and email. With the default lucene configuration, you can then search the "Users" index with a query like: "username:bob OR email:bob@gmail.com".</p><p>You can use the data browser to query your indexes this way, the syntax for the above query is "node:index:Users:username:bob OR email:bob@gmail.com".</p></div></div></div><div class="workarea with-sidebar"><div class="span-half no-margin node-indexes-wrap"><div class="pad"><h2>Node indexes</h2></div><div class="headline-bar pad"><ul class="button-bar"><li><input type="text" value="New node index" id="create-node-index-name" /></li><li><button class="create-node-index button">Create</button></li></ul><div class="break"></div></div><table cellspacing="0" class="data-table" id="node-indexes"></table></div><div class="span-half last no-margin rel-indexes-wrap"><div class="pad"><h2>Relationship indexes</h2></div><div class="headline-bar pad"><ul class="button-bar"><li><input type="text" value="New relationship index" id="create-rel-index-name" /></li><li><button class="create-rel-index button">Create</button></li></ul><div class="break"></div></div><table cellspacing="0" class="data-table" id="rel-indexes"></table></div></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function (define) {
    define("neo4j/webadmin/modules/indexmanager/views/index", [], function () {
        return function (vars) {
            with(vars || {}) return function () {
                return index.configAvailable() ? "<td><h3>" + htmlEscape(index.name) + '</h3><p class="small">' + htmlEscape(index.provider) + '</p></td><td><p class="small">' + htmlEscape(JSON.stringify(index.getConfig()).replace(/,/gi, ", ")) + "</p></td>" : ""
            }.call(this) + function () {
                return index.configAvailable() ? "" : "<td><p>N/A</p></td><td><p>N/A</p></td>"
            }.call(this) + '<td><button class="delete-index micro-button bad-button">Delete</button></td>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/indexmanager/views/IndexView", ["./index", "ribcage/View", "lib/amd/jQuery"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.deleteIndex = a(this.deleteIndex, this), this.render = a(this.render, this), this.initialize = a(this.initialize, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, d), f.prototype.NODE_INDEX_TYPE = "nodeidx", f.prototype.REL_INDEX_TYPE = "relidx", f.prototype.template = b, f.prototype.tagName = "tr", f.prototype.events = {
                "click .delete-index": "deleteIndex"
            }, f.prototype.initialize = function (a) {
                return this.index = a.index, this.idxMgr = a.idxMgr, this.type = a.type
            }, f.prototype.render = function () {
                return e(this.el).html(b({
                    index: this.index
                })), this
            }, f.prototype.deleteIndex = function () {
                if (confirm("Are you sure?")) return this.type === this.NODE_INDEX_TYPE ? this.idxMgr.deleteNodeIndex({
                    name: this.index.name
                }) : this.idxMgr.deleteRelationshipIndex({
                    name: this.index.name
                })
            }, f
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/indexmanager/views/IndexManagerView", ["./base", "./index", "./IndexView", "ribcage/View", "lib/amd/jQuery"], function (b, d, e, f, g) {
        var h;
        return h = function () {
            function d() {
                this.createRelationshipIndex = a(this.createRelationshipIndex, this), this.createNodeIndex = a(this.createNodeIndex, this), this.renderIndexList = a(this.renderIndexList, this), this.render = a(this.render, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, f), d.prototype.template = b, d.prototype.events = {
                "click .create-node-index": "createNodeIndex",
                "click .create-rel-index": "createRelationshipIndex"
            }, d.prototype.initialize = function (a) {
                return this.appState = a.state, this.server = this.appState.getServer(), this.idxMgr = a.idxMgr, this.idxMgr.bind("change", this.renderIndexList)
            }, d.prototype.render = function () {
                return g(this.el).html(b()), this.renderIndexList(), this
            }, d.prototype.renderIndexList = function () {
                var a, b, c, d, f, h, i, j, k, l;
                b = g("#node-indexes", this.el).empty(), j = this.idxMgr.get("nodeIndexes");
                for (d = 0, h = j.length; d < h; d++) a = j[d], b.append((new e({
                    index: a,
                    idxMgr: this.idxMgr,
                    type: e.prototype.NODE_INDEX_TYPE
                })).render().el);
                c = g("#rel-indexes", this.el).empty(), k = this.idxMgr.get("relationshipIndexes"), l = [];
                for (f = 0, i = k.length; f < i; f++) a = k[f], l.push(c.append((new e({
                    index: a,
                    idxMgr: this.idxMgr,
                    type: e.prototype.REL_INDEX_TYPE
                })).render().el));
                return l
            }, d.prototype.createNodeIndex = function () {
                return this.idxMgr.createNodeIndex({
                    name: g("#create-node-index-name").val()
                })
            }, d.prototype.createRelationshipIndex = function () {
                return this.idxMgr.createRelationshipIndex({
                    name: g("#create-rel-index-name").val()
                })
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/indexmanager/models/IndexManager", ["ribcage/Model"], function (b) {
        var d;
        return d = function () {
            function d() {
                this._hasNodeIndex = a(this._hasNodeIndex, this), this._hasRelationshipIndex = a(this._hasRelationshipIndex, this), this.deleteRelationshipIndex = a(this.deleteRelationshipIndex, this), this.deleteNodeIndex = a(this.deleteNodeIndex, this), this.createRelationshipIndex = a(this.createRelationshipIndex, this), this.createNodeIndex = a(this.createNodeIndex, this), this.initialize = a(this.initialize, this), d.__super__.constructor.apply(this, arguments)
            }
            return c(d, b), d.prototype.defaults = {
                nodeIndexes: [],
                relationshipIndexes: []
            }, d.prototype.initialize = function (b) {
                return this.server = b.server, this.server.index.getAllNodeIndexes().then(a(function (a) {
                    return this.set({
                        nodeIndexes: a
                    })
                }, this)), this.server.index.getAllRelationshipIndexes().then(a(function (a) {
                    return this.set({
                        relationshipIndexes: a
                    })
                }, this))
            }, d.prototype.createNodeIndex = function (b) {
                var c;
                c = b.name;
                if (this._hasNodeIndex(c)) return;
                return this.server.index.createNodeIndex(c).then(a(function (a) {
                    return this.get("nodeIndexes").push(a), this.trigger("change")
                }, this))
            }, d.prototype.createRelationshipIndex = function (b) {
                var c;
                c = b.name;
                if (this._hasRelationshipIndex(c)) return;
                return this.server.index.createRelationshipIndex(c).then(a(function (a) {
                    return this.get("relationshipIndexes").push(a), this.trigger("change")
                }, this))
            }, d.prototype.deleteNodeIndex = function (b) {
                var c;
                return c = b.name, this.server.index.removeNodeIndex(c).then(a(function () {
                    return this.set({
                        nodeIndexes: this._removeIndexFromList(this.get("nodeIndexes"), c)
                    }), this.trigger("change")
                }, this))
            }, d.prototype.deleteRelationshipIndex = function (b) {
                var c;
                return c = b.name, this.server.index.removeRelationshipIndex(c).then(a(function () {
                    return this.set({
                        relationshipIndexes: this._removeIndexFromList(this.get("relationshipIndexes"), c)
                    }), this.trigger("change")
                }, this))
            }, d.prototype._hasRelationshipIndex = function (a) {
                var b, c, d, e;
                e = this.get("relationshipIndexes");
                for (c = 0, d = e.length; c < d; c++) {
                    b = e[c];
                    if (b.name === a) return !0
                }
                return !1
            }, d.prototype._hasNodeIndex = function (a) {
                var b, c, d, e;
                e = this.get("nodeIndexes");
                for (c = 0, d = e.length; c < d; c++) {
                    b = e[c];
                    if (b.name === a) return !0
                }
                return !1
            }, d.prototype._removeIndexFromList = function (a, b) {
                var c, d;
                for (c = d = a.length - 1; d > 0 ? c >= 0 : c <= 0; d > 0 ? c-- : c++) if (a[c].name === b) {
                    a.splice(c, 1);
                    break
                }
                return a
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/indexmanager/IndexManagerRouter", ["./views/IndexManagerView", "./models/IndexManager", "ribcage/Router"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.getIndexManagerView = a(this.getIndexManagerView, this), this.idxManager = a(this.idxManager, this), this.init = a(this.init, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, e), f.prototype.routes = {
                "/index/": "idxManager"
            }, f.prototype.init = function (a) {
                return this.appState = a, this.idxMgr = new d({
                    server: this.appState.get("server")
                })
            }, f.prototype.idxManager = function () {
                return this.saveLocation(), this.appState.set({
                    mainView: this.getIndexManagerView()
                })
            }, f.prototype.getIndexManagerView = function () {
                var a;
                return (a = this.view) != null ? a : this.view = new b({
                    state: this.appState,
                    idxMgr: this.idxMgr
                })
            }, f
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/baseui/base", [], function () {
        return function (vars) {
            with(vars || {}) return '<div id="header"><div id="header_submenu"><div id="global-loading-indicator"><div class="loading-spinner">Loading</div></div><ul class="horizontal_menu"><li><a href="http://docs.neo4j.org/chunked/${project.version}/" class="micro-button">Documentation</a></li></ul></div><img src="img/logo.png" id="logo" /><ul id="mainmenu">' + function () {
                var a = [],
                    b, c;
                for (b in mainmenu) mainmenu.hasOwnProperty(b) && (c = mainmenu[b], a.push('<li class="title-button">' + function () {
                    return c.current ? '<a href="' + htmlEscape(c.url) + '" class="current"><span class="subtitle">' + htmlEscape(c.subtitle) + "</span><span>" + htmlEscape(c.label) + "</span></a>" : ""
                }.call(this) + function () {
                    return c.current ? "" : '<a href="' + htmlEscape(c.url) + '"><span class="subtitle">' + htmlEscape(c.subtitle) + "</span><span>" + htmlEscape(c.label) + "</span></a>"
                }.call(this) + "</li>"));
                return a.join("")
            }.call(this) + '</ul></div><div id="contents"></div><div id="footer"><p class="copyright">Copyright (c) 2002-2012 <a href="http://neotechnology.com">Neo Technology</a>. This is free software, available under the  <a href="http://www.gnu.org/licenses/gpl.html">GNU General Public License</a> version 3 or greater.</p></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    }, b = Object.prototype.hasOwnProperty,
        c = function (a, c) {
            function e() {
                this.constructor = a
            }
            for (var d in c) b.call(c, d) && (a[d] = c[d]);
            return e.prototype = c.prototype, a.prototype = new e, a.__super__ = c.prototype, a
        };
    define("neo4j/webadmin/modules/baseui/BaseUI", ["./base", "ribcage/View", "lib/amd/jQuery"], function (b, d, e) {
        var f;
        return f = function () {
            function f() {
                this.remove = a(this.remove, this), this.mainViewChanged = a(this.mainViewChanged, this), this.init = a(this.init, this), f.__super__.constructor.apply(this, arguments)
            }
            return c(f, d), f.prototype.template = b, f.prototype.init = function (a) {
                return this.appState = a, e("body").append(this.el), this.appState.bind("change:mainView", this.mainViewChanged)
            }, f.prototype.mainViewChanged = function (a) {
                return this.mainView != null && this.mainView.detach(), this.mainView = a.attributes.mainView, this.render()
            }, f.prototype.render = function () {
                return e(this.el).html(this.template({
                    mainmenu: [{
                        label: "Dashboard",
                        subtitle: "Overview",
                        url: "#",
                        current: location.hash === ""
                    }, {
                        label: "Data browser",
                        subtitle: "Explore and edit",
                        url: "#/data/",
                        current: location.hash.indexOf("#/data/") === 0
                    }, {
                        label: "Console",
                        subtitle: "Power tool",
                        url: "#/console/",
                        current: location.hash.indexOf("#/console/") === 0
                    }, {
                        label: "Server info",
                        subtitle: "Details",
                        url: "#/info/",
                        current: location.hash === "#/info/"
                    }, {
                        label: "Index manager",
                        subtitle: "Indexing overview",
                        url: "#/index/",
                        current: location.hash === "#/index/"
                    }]
                })), this.mainView != null && (this.mainView.attach(e("#contents")), this.mainView.render()), this
            }, f.prototype.remove = function () {
                return this.appState.unbind("change:mainView", this.mainViewChanged), this.mainView != null && this.mainView.remove(), f.__super__.remove.call(this)
            }, f
        }()
    })
}.call(this),
function () {
    define("neo4j/webadmin/modules/moreinfo/MoreInfo", ["lib/amd/jQuery"], function (a) {
        var b;
        return b = function () {
            function b() {}
            return b.prototype.init = function () {
                return a("a.foldout_trigger").live("click", function (b) {
                    return b.preventDefault(), a(b.target).closest(".foldout").toggleClass("visible")
                })
            }, b
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/splash/splash", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="overlay"><div id="splash"><img src="img/logo.png" id="splash-logo" /><h1 style="background: url(\'${neo4j.codename.icon}\') no-repeat scroll center top transparent">${project.version} ${neo4j.codename}</h1></div></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    define("ribcage/storage/CookieStorage", [], function () {
        var a;
        return a = function () {
            function a() {}
            return a.prototype.get = function (a) {
                var b, c, d, e, f;
                d = "" + a + "=", c = document.cookie.split(";");
                for (e = 0, f = c.length; e < f; e++) {
                    b = c[e];
                    while (b.charAt(0) === " ") b = b.substring(1, b.length);
                    if (b.indexOf(d) === 0) return b.substring(d.length, b.length)
                }
                return null
            }, a.prototype.set = function (a, b, c) {
                var d, e;
                return c == null && (c = 365), c ? (d = new Date, d.setTime(d.getTime() + c * 24 * 60 * 60 * 1e3), e = "; expires=" + d.toGMTString()) : e = "", document.cookie = "" + a + "=" + b + e + "; path=/"
            }, a.prototype.remove = function (a) {
                return this.set(a, "", -1)
            }, a
        }()
    })
}.call(this),
function () {
    define("neo4j/webadmin/modules/splash/SplashScreen", ["./splash", "ribcage/storage/CookieStorage", "lib/amd/jQuery"], function (a, b, c) {
        var d;
        return d = function () {
            function d() {
                this.cookies = new b
            }
            return d.prototype.init = function () {
                if (!this.hasBeenShownForThisSession()) return this.show()
            }, d.prototype.hasBeenShownForThisSession = function () {
                return this.cookies.get("splashShown1.6") !== null
            }, d.prototype.show = function () {
                var b, d;
                return this.cookies.set("splashShown1.6", "1"), d = c(a()), c("body").append(d), b = function () {
                    return d.fadeOut(600)
                }, setTimeout(b, 1500)
            }, d
        }()
    })
}.call(this),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/loading/GlobalLoadingIndicator", ["lib/amd/jQuery"], function (b) {
        var c;
        return c = function () {
            function c(b) {
                this.target = b != null ? b : "#global-loading-indicator", this.hide = a(this.hide, this), this.show = a(this.show, this), this.onAjaxComplete = a(this.onAjaxComplete, this), this.onAjaxSend = a(this.onAjaxSend, this), this.runningRequests = 0
            }
            return c.prototype.init = function () {
                return b(window).ajaxSend(this.onAjaxSend), b(window).ajaxComplete(this.onAjaxComplete)
            }, c.prototype.onAjaxSend = function () {
                this.runningRequests++;
                if (this.runningRequests === 1) return this.timeout = setTimeout(this.show, 1e3)
            }, c.prototype.onAjaxComplete = function () {
                this.runningRequests--;
                if (this.runningRequests <= 0) return this.runningRequests = 0, clearTimeout(this.timeout), this.hide()
            }, c.prototype.show = function () {
                return b(this.target).show()
            }, c.prototype.hide = function () {
                return b(this.target).hide()
            }, c
        }()
    })
}.call(this),
function (define) {
    define("neo4j/webadmin/modules/connectionmonitor/connection_lost", [], function () {
        return function (vars) {
            with(vars || {}) return '<div class="overlay"><div class="info-splash"><img src="img/logo.png" id="splash-logo" /><h1>Server connection lost, trying to reconnect..</h1></div></div>'
        }
    })
}(typeof define == "function" ? define : function (a) {
    module.exports = a.apply(this, deps.map(require))
}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("neo4j/webadmin/modules/connectionmonitor/ConnectionMonitor", ["./connection_lost", "lib/amd/jQuery"], function (b, c) {
        var d, e;
        return d = "web.connection_lost", e = function () {
            function e() {
                this.connectionLost = a(this.connectionLost, this)
            }
            return e.prototype.visible = !1, e.prototype.init = function (a) {
                return this.db = a.getServer(), this.db.bind(d, this.connectionLost), this.db.heartbeat.addListener(function () {})
            }, e.prototype.connectionLost = function () {
                var d, e;
                if (!this.visible) return this.visible = !0, d = c(b()), c("body").append(d), e = a(function () {
                    return this.visible = !1, d.fadeOut(200, function () {
                        return d.remove()
                    })
                }, this), this.db.heartbeat.waitForPulse(e)
            }, e
        }()
    })
}.call(this), has = function (a) {
    function g(b) {
        return typeof f[b] == "function" && (f[b] = f[b](a, d, e)), f[b]
    }
    function h(b, c, g) {
        f[b] = g ? c(a, d, e) : c
    }
    function i(a, b) {
        var d = !1,
            e = a.charAt(0).toUpperCase() + a.slice(1),
            f = c.length,
            g = b.style;
        if (typeof g[a] == "string") d = !0;
        else while (f--) if (typeof g[c[f] + e] == "string") {
            d = !0;
            break
        }
        return d
    }
    function j(a) {
        if (a) while (a.lastChild) a.removeChild(a.lastChild);
        return a
    }
    function k(a, c) {
        var d = typeof a[c];
        return d == "object" ? !! a[c] : !b[d]
    }
    var b = {
        "boolean": 1,
        number: 1,
        string: 1,
        "undefined": 1
    }, c = ["Webkit", "Moz", "O", "ms", "Khtml"],
        d = k(a, "document") && a.document,
        e = d && k(d, "createElement") && d.createElement("DiV"),
        f = {};
    g.add = h, g.clearElement = j, g.cssprop = i, g.isHostType = k, g._tests = f, g.add("dom", function (a, b, c) {
        return b && c && k(a, "location") && k(b, "documentElement") && k(b, "getElementById") && k(b, "getElementsByName") && k(b, "getElementsByTagName") && k(b, "createComment") && k(b, "createElement") && k(b, "createTextNode") && k(c, "appendChild") && k(c, "insertBefore") && k(c, "removeChild") && k(c, "getAttribute") && k(c, "setAttribute") && k(c, "removeAttribute") && k(c, "style") && typeof c.style.cssText == "string"
    });
    try {
        document.execCommand("BackgroundImageCache", !1, !0)
    } catch (l) {}
    return g
}(this),
function (a, b, c, d) {
    var e = "string",
        f = "function";
    b("native-console", function (a) {
        return "console" in a
    }), b("native-xhr", function (b) {
        return a.isHostType(b, "XMLHttpRequest")
    }), b("native-cors-xhr", function (b) {
        return a("native-xhr") && "withCredentials" in new XMLHttpRequest
    }), b("native-xhr-uploadevents", function (b) {
        return a("native-xhr") && "upload" in new XMLHttpRequest
    }), b("activex", function (b) {
        return a.isHostType(b, "ActiveXObject")
    }), b("activex-enabled", function (b) {
        var c = null;
        if (a("activex")) try {
            c = !! (new ActiveXObject("htmlfile"))
        } catch (d) {
            c = !1
        }
        return c
    }), b("native-navigator", function (a) {
        return "navigator" in a
    }), b("native-geolocation", function (b) {
        return a("native-navigator") && "geolocation" in b.navigator
    }), b("native-crosswindowmessaging", function (a) {
        return "postMessage" in a
    }), b("native-orientation", function (a) {
        return "ondeviceorientation" in a
    }), b("native-worker", function (a) {
        return "Worker" in a
    }), b("native-sharedworker", function (a) {
        return "SharedWorker" in a
    }), b("native-eventsource", function (a) {
        return "EventSource" in a
    }), b("eval-global-scope", function (a) {
        var b = "__eval" + Number(new Date),
            c = !1;
        try {
            a.eval("var " + b + "=true")
        } catch (e) {}
        c = a[b] === !0;
        if (c) try {
            delete a[b]
        } catch (e) {
            a[b] = d
        }
        return c
    }), b("native-sql-db", function (a) {
        var b = "hasjstestdb",
            c = "openDatabase" in a;
        if (c) try {
            c = !! openDatabase(b, "1.0", b, 2e4)
        } catch (d) {
            c = !1
        }
        return c
    }), b("native-indexeddb", function (a) {
        return "indexedDB" in a
    }), b("native-localstorage", function (a) {
        var b = !1;
        try {
            b = "localStorage" in a && "setItem" in localStorage
        } catch (c) {}
        return b
    }), b("native-sessionstorage", function (a) {
        var b = !1;
        try {
            b = "sessionStorage" in a && "setItem" in sessionStorage
        } catch (c) {}
        return b
    }), b("native-history-state", function (a) {
        return "history" in a && "pushState" in history
    }), b("native-websockets", function (a) {
        return "WebSocket" in a
    })
}(has, has.add, has.cssprop), define("lib/has", function () {}),
function () {
    var a = function (a, b) {
        return function () {
            return a.apply(b, arguments)
        }
    };
    define("ribcage/storage/LocalModelStore", ["lib/amd/Backbone", "lib/has"], function (b) {
        var c, d, e;
        return e = function () {
            function a() {}
            return a.prototype.store = function (a, b) {
                return localStorage.setItem(a, JSON.stringify(b))
            }, a.prototype.fetch = function (a, b) {
                var c;
                return c = localStorage.getItem(a), c !== null ? JSON.parse(c) : b
            }, a.prototype.remove = function (a) {
                return localStorage.removeItem(a)
            }, a
        }(), c = function () {
            function a() {
                this.storage = {}
            }
            return a.prototype.store = function (a, b) {
                return this.storage[a] = b
            }, a.prototype.fetch = function (a, b) {
                return this.storage[a] != null ? this.storage[a] : this.defaults
            }, a.prototype.remove = function (a) {
                return delete this.storage[a]
            }, a
        }(), d = function () {
            function d() {
                _(this).extend(b.Events), has("native-localstorage") ? this.storingStrategy = new e : this.storingStrategy = new c, this._cache = {}
            }
            return d.prototype.storagePrefix = "", d.prototype.get = function (b, c, d) {
                var e, f, g;
                return c == null && (c = null), d == null && (d = null), this._cache[b] == null && (e = a(function () {
                    return this.storingStrategy.fetch(b, d)
                }, this), g = a(function (a) {
                    return this.set(b, a)
                }, this), c != null ? (f = new c(e()), f.setSaveMethod != null && f.setSaveMethod(g), f.setFetchMethod != null && f.setFetchMethod(e)) : f = e(), this._cache[b] = f), this._cache[b]
            }, d.prototype.set = function (a, b) {
                return this._cache[a] = b, b !== null && b.toJSON && (b = b.toJSON()), this.storagePrefix.length > 0 && (a = "" + this.storagePrefix + "::" + a), this.storingStrategy.store(a, b), this.trigger("change"), this.trigger("change:" + a)
            }, d.prototype.remove = function (a) {
                return this.storingStrategy.remove(a)
            }, d
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/Settings", ["ribcage/storage/LocalModelStore"], function (a) {
        var c;
        return c = function () {
            function c() {
                c.__super__.constructor.apply(this, arguments)
            }
            return b(c, a), c
        }()
    })
}.call(this),
function () {
    var a = Object.prototype.hasOwnProperty,
        b = function (b, c) {
            function e() {
                this.constructor = b
            }
            for (var d in c) a.call(c, d) && (b[d] = c[d]);
            return e.prototype = c.prototype, b.prototype = new e, b.__super__ = c.prototype, b
        };
    define("neo4j/webadmin/ApplicationState", ["./Settings", "ribcage/Model"], function (a, c) {
        var d;
        return d = function () {
            function d() {
                d.__super__.constructor.apply(this, arguments)
            }
            return b(d, c), d.prototype.getServer = function () {
                return this.get("server")
            }, d.prototype.getSettings = function () {
                var b;
                return (b = this.settings) != null ? b : this.settings = new a
            }, d
        }()
    })
}.call(this),
function () {
    define("lib/amd/neo4js", ["order!lib/amd/jQuery", "order!lib/neo4js"], function () {
        return neo4j
    })
}.call(this),
function () {
    define("neo4j/webadmin/Bootstrapper", ["neo4j/webadmin/ApplicationState", "ribcage/security/HtmlEscaper", "lib/amd/Backbone", "lib/amd/neo4js"], function (a, b, c, d) {
        var e;
        return e = function () {
            function e() {}
            return e.prototype.bootstrap = function (e) {
                var f, g;
                return g = new b, window.htmlEscape = g.escape, jQuery.ajaxSetup({
                    timeout: 216e5
                }), f = new a, f.set({
                    server: new d.GraphDatabase(location.protocol + "//" + location.host)
                }), jQuery(function () {
                    var a, b, d;
                    for (b = 0, d = e.length; b < d; b++) a = e[b], a.init(f);
                    return c.history.start()
                })
            }, e
        }()
    })
}.call(this),
function () {
    require(["lib/jquery", "lib/neo4js", "lib/backbone", "neo4j/webadmin/modules/dashboard/DashboardRouter", "neo4j/webadmin/modules/databrowser/DataBrowserRouter", "neo4j/webadmin/modules/console/ConsoleRouter", "neo4j/webadmin/modules/serverinfo/ServerInfoRouter", "neo4j/webadmin/modules/indexmanager/IndexManagerRouter", "neo4j/webadmin/modules/baseui/BaseUI", "neo4j/webadmin/modules/moreinfo/MoreInfo", "neo4j/webadmin/modules/splash/SplashScreen", "neo4j/webadmin/modules/loading/GlobalLoadingIndicator", "neo4j/webadmin/modules/connectionmonitor/ConnectionMonitor", "neo4j/webadmin/Bootstrapper"], function (a, b, c, d, e, f, g, h, i, j, k, l, m, n) {
        var o, p;
        return p = [new i, new d, new e, new f, new h, new g, new m, new k, new l, new j], o = new n, o.bootstrap(p)
    })
}.call(this), define("webadmin", function () {})