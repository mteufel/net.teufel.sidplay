<template>
<div id="outer">
    <br />
    <br />
    <div id="inner">
        <Table :columns="tableColumns" :data="tableData" style="width: 800px;"></Table>
    </div>
</div>
</template>

<script>

import

export default {
  name: 'ResultsComponent',
  props: {
    msg: String,
  },
  data() {
    return {
      tableColumns: [{ title: 'Title', key: 'title' }, { title: 'Author', key: 'author' }, { title: 'Release', key: 'release', sortable: true }],
      tableData: [{
        sidId: 48563, title: 'Bubble Bobble cover', author: 'Tommi Lehtimäki (deizi)', release: '2004 deizi',
      }, {
        sidId: 37489, title: 'BubbleBobble', author: 'Roel Heerspink (Cerror)', release: '2003 TostySquad',
      }, {
        sidId: 34077, title: 'Puzzle Bobble', author: 'Borgar Thorsteinsson (Oedipus)', release: '2001 Oedipus',
      }, {
        sidId: 37986, title: 'Bubble Bobble', author: 'Peter Clarke', release: '1987 Firebird',
      }],
    };
  },
};
</script>

<style scoped>
#outer {
    width: 100%;
    text-align: center;
}

#inner {
    display: inline-block;
}
</style>
