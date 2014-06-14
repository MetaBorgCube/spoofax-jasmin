function toJasminBaseType(str) {
    var str0 = "";
    if(str[0] === "(") {
        str0 = "(";
        str = str.slice(1);
    }
    var arr = str.split("[");
    var typeTable = {
        "boolean": "Z",
        "byte": "B",
        "char": "C",
        "double": "D",
        "float": "F",
        "int": "I",
        "long": "J",
        "short": "S",
        "void": "V"
    };
    if(arr[0] in typeTable) {
        return str0 + "[".repeat(arr.length - 1) + typeTable[arr[0]];
    } else {
        return str0 + "[".repeat(arr.length - 1) + arr[0];
    }
}
function toJasminReference() {
    return "L" + $(this).attr("href").match(/java(\/[^\/.]+)+/)[0] + ";";
}

var pre = $("pre").eq(0).clone();
pre.children("a").replaceWith(toJasminReference);
pre = $.makeArray(pre.contents().map(function(i) {
    if(i === 0) {
        return ".class " + this.data.replace(/\s?class\s?|\s?interface\s?/, "") + " ";
    }
    if(i === 1) {
        return $(location).attr("href").match(/java(\/[^\/.]+)+/)[0] + "\n";
    }
    if(i === 2) {
        return ".super " + this.data.replace(/\s?extends\s?/, "");
    }
    if(i === 3) {
        return $(this).text() + "\n\n";
    }
}))
if(pre.length === 2) {
    pre.push(".super java/lang/Object\n\n");
}
pre.join("") + $.makeArray($("table.overviewSummary").map(function() {
    var strs = $(this).children("tbody");
    var text = strs.children("tr").first().children("th").last().text();
    var kind = text.startsWith("Constructor") ? "constructor" : text.startsWith("Field") ? "field" : text.startsWith("Method") ? "method" : "";
    strs = strs.children("tr.rowColor, tr.altColor")
        .each(function() {
            $(this).data("kind",kind);
        })
        .map(function() {
            var tr = $(this);
            var kind = tr.data("kind");
            var modifiers = [];
            var ret_ty;
            var name;
            var params;
            if(kind === "constructor") {
                params = tr.children("td.colOne").children("code").clone();
                name = "<init>";
                ret_ty = "V";
            }
            if(kind === "field" || kind === "method") {
                ret_ty = tr.children("td.colFirst").children("code").clone();
                ret_ty.children("a").replaceWith(toJasminReference);
                ret_ty = ret_ty.text().split(" ");
                if(ret_ty.length > 1) {
                    modifiers = ret_ty.slice(0,ret_ty.length-1);
                }
                ret_ty = toJasminBaseType(ret_ty[ret_ty.length-1]);
                ret_ty = ret_ty.replace(/<.*>/, "");
                if(!modifiers.find(function(e) { return e === "protected" })) {
                    modifiers.push("public");
                }
                name = tr.children("td.colLast").children("code").children("strong").text();
            }
            if(kind === "field") {
                modifiers.push(name);
                modifiers.push(ret_ty);
                return modifiers.join(" ");
            }
            if(kind === "method") {
                params = tr.children("td.colLast").children("code").clone();
            }
            params.children("a").replaceWith(toJasminReference);
            params.children("strong").eq(0).remove();
            params = params.contents().text().replace(/\s+/g, " ").split(" ");
            
            if(params.length > 1) {
                for(var i = 0; i < params.length; i+=2) {
                    params[i/2] = toJasminBaseType(params[i]);
                }
                params[params.length/2] = ")";
                params = params.slice(0, params.length/2 + 1);
            }
            if(!/\.\.\./.test(params.join(""))) { // exclude varargs, too much trouble to add support for those.
            	return modifiers.join(" ") + " " + name + params.join("") + ret_ty;
            }
        });
    return $.makeArray(strs.map(function() {
        return "."+kind+" "+this+"\n.end "+kind;
    })).join("\n\n");
})).join("\n\n");
