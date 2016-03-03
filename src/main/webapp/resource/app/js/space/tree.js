// Tree 구조의 JSON으로 자료형을 만든다.
function makeTree(arr) {
    var tree = [], mappedArr = {}, arrElem, mappedElem;

    // First map the nodes of the array to an object -> create a hash table.
    for(var i = 0, len = arr.length; i < len; i++) {
        arrElem = arr[i];
        mappedArr[arrElem.id] = arrElem;
        if (arrElem.hasChild) {
            mappedArr[arrElem.id]['nodes'] = [];
        }
    }

    for (var id in mappedArr) {
        if (mappedArr.hasOwnProperty(id)) {
            mappedElem = mappedArr[id];

            // If the element is not at the root level, add it to its parent array of children.
            if (mappedElem.parentId) {
                mappedArr[mappedElem['parentId']]['nodes'].push(mappedElem);
            }
            // If the element is at the root level, add it to first level elements array.
            else {
                tree.push(mappedElem);
            }
        }
    }
    return tree;
}