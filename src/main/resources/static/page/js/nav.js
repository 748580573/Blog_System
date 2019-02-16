var nav = new Vue({
    el: "#nav",
    data: {
        titles: [
            {name: "home", sub: [{name: "开心宝典"}, {name: "快乐成长法"}, {name: "素质教育"}]},
            {name: "product", sub: []},
            {name: "servicess", sub: [{name: "开心宝典"}, {name: "快乐成长法"}, {name: "素质教育"}]},
            {name: "contact", sub: []}
        ]
    }
});