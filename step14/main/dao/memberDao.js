// MEMB 테이블 데이터 처리
"use strict"

module.exports = {
  setConnection(conn) {
    this.connection = conn
  },

  insert : function(member, successFn, errorFn) {
    this.connection.query(
      'insert into memb(name, tel, email, pwd) values(?,?,?,password(?))',
      [member.name, member.tel, member.email, member.password],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, //insertMember

  update : function(member, successFn, errorFn) {
    this.connection.query(
      'update memb set name=?, tel=?, email=? where mno=?',
      [member.name, member.tel, member.email, member.no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  }, // updateMember

  delete : function(no, successFn, errorFn) {
    this.connection.query(
      'delete from memb where mno=?',
      [no],
      function(error, results) {
        if (error) {
          errorFn(error)
        } else {
          successFn(results)
        }
    }) // connection.query
  } // deleteMember
} // module












//
