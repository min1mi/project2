"use strict"

module.exports = {
  setConnection(conn) {
    this.connection = conn // 변수를 알아서 만들어준다.
  },

  selectList(pageNo, successFn, errorFn) { // 성공했을 때 호출될 함수, 실패했을 때 호출될 함수
    // alert(currPageNo + 1) // 0번째 태그의 콘텐츠만 가져옴, 콘텐츠 = 시작태그와 끝태그 사이에 있는 값
    this.connection.query(
      'select m.mno, m.name, m.tel, m.email, t.hmpg \
      from tcher t inner join memb m on t.tno = m.mno \
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
      'select count(*) cnt from tcher',
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
      'select m.mno, m.name, m.tel, m.email, t.hmpg , t.fcbk, t.twit \
      from tcher t inner join memb m on t.tno = m.mno \
      where t.tno = ?',
      [no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results[0]) // select가 한개라서 0번으로 호출
        }
      }) // connection.query
  }, // selectOneStudent

  insert(teacher, successFn, errorFn) {
    this.connection.query(
      'insert into tcher(tno, hmpg, fcbk, twit) values(?, ?, ?, ?)',
      [teacher.no, teacher.hmpg, teacher.fcbk, teacher.twit],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, //insertStudent

  update(teacher, successFn, errorFn) {
    this.connection.query(
      'update tcher set hmpg=?, fcbk=?, twit=? where tno=?',
      [teacher.hmpg, teacher.fcbk, teacher.twit, teacher.no],
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
      'delete from tcher where tno=?',
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
