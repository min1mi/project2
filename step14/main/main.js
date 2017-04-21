/* 주제: MySQL DBMS에 직접 접속하기 VIIII
  => DAO, Service를 nodeJS의 모듈로 만들기
 */
const {app, BrowserWindow} = require('electron')
const path = require('path')
const url = require('url')

const datasource = require('./util/datasource')
const connection = datasource.getConnection()

const studentDao = require('./dao/studentDao')
studentDao.setConnection(connection)

const memberDao = require('./dao/memberDao')
memberDao.setConnection(connection)

const teacherDao = require('./dao/teacherDao')
teacherDao.setConnection(connection)

const managerDao = require('./dao/managerDao')
managerDao.setConnection(connection)

const lectureDao = require('./dao/lectureDao')
lectureDao.setConnection(connection)

const studentService = require('./service/student-service') // UI에서 필요한 것
studentService.setMemberDao(memberDao)
studentService.setStudentDao(studentDao)

const teacherService = require('./service/teacher-service') // UI에서 필요한 것
teacherService.setMemberDao(memberDao)
teacherService.setTeacherDao(teacherDao)

const managerService = require('./service/manager-service') // UI에서 필요한 것
managerService.setMemberDao(memberDao)
managerService.setManagerDao(managerDao)

const lectureService = require('./service/lecture-service') // UI에서 필요한 것
managerService.setMemberDao(memberDao)
lectureService.setLectureDao(lectureDao)

// global 빌트인 객체에 값을 보관하면, renderer 프로세스(웹 화면쪽)에서 꺼내 쓸 수 있다.
global.studentService = studentService
global.teacherService = teacherService
global.managerService = managerService
global.lectureService = lectureService

let win

app.on('ready', createWindow)

function createWindow() {
  win = new BrowserWindow({width: 1000, height: 800})
  win.loadURL(url.format({
    protocol: 'file:',
    pathname: path.join(__dirname, '../renderer/index.html'),
    slashes: true
  }))
  // win.webContents.openDevTools() // 웹브라우저의 개발도구창을 띄운다.

}




//
