// Tree 구조의 JSON으로 자료형을 만든다.
function makeTree(jsonRawData) {
    var treeMenu = [], arraysMap = {}, node, nodeMap;

    // First map the nodes of the array to an object -> create a hash table.
    for(var i = 0, len = jsonRawData.length; i < len; i++) {
        node = jsonRawData[i];
        arraysMap[node.id] = node;
        if (node.hasChild) {
            arraysMap[node.id]['nodes'] = [];
        }
    }

    for (var id in arraysMap) {
        if (arraysMap.hasOwnProperty(id)) {
            nodeMap = arraysMap[id];

            // Root가 아닐 경우 자식 노드로 추가함
            if (nodeMap.parentId) {
                arraysMap[nodeMap['parentId']]['nodes'].push(nodeMap);
            } else {  // Root element 일 경우 push
                treeMenu.push(nodeMap);
            }
        }
    }
    return treeMenu;
}