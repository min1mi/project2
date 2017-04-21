"use strict"

module.exports = {
  setConnection(conn) {
    this.connection = conn // 변수를 알아서 만들어준다.
  },

  selectList(pageNo, successFn, errorFn) { // 성공했을 때 호출될 함수, 실패했을 때 호출될 함수
    // alert(currPageNo + 1) // 0번째 태그의 콘텐츠만 가져옴, 콘텐츠 = 시작태그와 끝태그 사이에 있는 값
    this.connection.query(
      'select m.mno, m.name, m.tel, m.email, r.posi \
      from mgr r inner join memb m on r.mrno = m.mno \
      order by m.mno asc\
      limit ?, ?',
      [(pageNo -1) * 3, 3],
      function (error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, // listStudent

  countAll(successFn, errorFn) {
    this.connection.query(
      'select count(*) cnt from mgr',
      function (error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, // countAllStudent

  selectOne(no, successFn, errorFn){
    this.connection.query(
      'select m.mno, m.name, m.tel, m.email, r.posi, r.fax, r.path \
      from mgr r inner join memb m on r.mrno = m.mno \
      where r.mrno = ?',
      [no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results[0]) // select가 한개라서 0번으로 호출
        }
      }) // connection.query
  }, // selectOneStudent

  insert(manager, successFn, errorFn) {
    this.connection.query(
      'insert into mgr(mrno, posi, fax, path) values(?, ?, ?, ?)',
      [manager.no, manager.posi, manager.fax, manager.path],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, //insertStudent

  update(manager, successFn, errorFn) {
    this.connection.query(
      'update mgr set posi=?, fax=?, path=? where mrno=?',
      [manager.posi, manager.fax, manager.path, manager.no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, // updateStudent

  delete(no, successFn, errorFn) {
    this.connection.query(
      'delete from mgr where mrno=?',
      [no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  } // deleteStudent
} // module











//
