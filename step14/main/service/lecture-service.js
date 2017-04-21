"use strict"

module.exports = {
  setLectureDao(dao) {
    this.lectureDao = dao
  },

  setMemberDao(dao) {
    this.memberDao = dao
  },

  setManagerDao(dao) {
    this.managerDao = dao
  },

  list(pageNo, success, error) {
    var obj = this
    this.lectureDao.selectList(pageNo, function(lectures) {
      obj.lectureDao.countAll(function(result) {
        success(lectures, result[0].cnt)
      })
    }, error)
  }, // list

  detail(no, success, error) {
    this.lectureDao.selectOne(no, success, error)
  }, // detail

  insert(lecture, success, error) {
    var obj = this
    this.memberDao.insert(lecture, function(result) {
      lecture.no = result.insertId
      obj.lectureDao.insert(lecture, success, error)
    }, error)
  }, // insert

  update(lecture, success, error) {
    var obj = this
    this.memberDao.update(lecture, function(result) {
      obj.lectureDao.update(lecture, success, error)
    }, error)
  }, // update

  delete(no, success, error) {
    var obj = this
    this.lectureDao.delete(no, function(result) {
      obj.memberDao.delete(no, success, error)
    }, error)
  }// delete
} // module
