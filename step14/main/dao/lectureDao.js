"use strict"

module.exports = {
  setConnection(conn) {
    this.connection = conn // 변수를 알아서 만들어준다.
  },

  selectList(pageNo, successFn, errorFn) { // 성공했을 때 호출될 함수, 실패했을 때 호출될 함수
    // alert(currPageNo + 1) // 0번째 태그의 콘텐츠만 가져옴, 콘텐츠 = 시작태그와 끝태그 사이에 있는 값
    this.connection.query(
      'select m.mno, m.name, m.tel, m.email, s.work \
      from stud s inner join memb m on s.sno = m.mno \
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
      'select count(*) cnt from stud',
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
      'select m.mno, m.name, m.tel, m.email, s.work , s.schl_nm\
      from stud s inner join memb m on s.sno = m.mno \
      where s.sno = ?',
      [no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results[0]) // select가 한개라서 0번으로 호출
        }
      }) // connection.query
  }, // selectOneStudent

  insert(student, successFn, errorFn) {
    this.connection.query(
      'insert into stud(work, schl_nm, sno) values(?, ?, ?)',
      [student.working, student.school_nm, student.no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, //insertStudent

  update(student, successFn, errorFn) {
    this.connection.query(
      'update stud set work=?, schl_nm=? where sno=?',
      [student.working, student.school_nm, student.no],
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
      'delete from stud where sno=?',
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
