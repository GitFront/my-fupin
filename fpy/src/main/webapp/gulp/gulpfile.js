//导入工具包 require('node_modules里对应模块')
var gulp = require('gulp'), //本地安装gulp所用到的地方
    //sass = require('gulp-sass'),
    livereload = require('gulp-livereload'),
    cssmin = require('gulp-cssmin');
var plumber = require('gulp-plumber');
var notify = require('gulp-notify');
var autoprefixer = require('gulp-autoprefixer');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
// gulp-sass gulp-livereload gulp-plumber gulp-notify gulp-autoprefixer

//定义一个testSass任务（自定义任务名称）
gulp.task('testSass', function () {
    gulp.src('../scss/*.scss') //该任务针对的文件
        .pipe(plumber({errorHandler: notify.onError('Error: <%= error.message %>')}))
        .pipe(sass()) //该任务调用的模块
        .pipe(gulp.dest('gulpCss')); //
        //.pipe(livereload());
});


//监控css
gulp.task('watchSass', function () {
	livereload.listen();
    gulp.watch('../scss/**/*.scss', ['testSass']);
    gulp.watch('gulpCss/**/*.css', ['fixCss3']);
   
});

gulp.task('fixCss3', function () {
    gulp.src('gulpCss/*.css')
        .pipe(autoprefixer({
            browsers: ['last 30 versions', 'Android >= 4.0','iOS >= 6.0'],
            cascade: true, //是否美化属性值 默认：true 像这样：
            //-webkit-transform: rotate(45deg);
            //        transform: rotate(45deg);
            remove:true //是否去掉不必要的前缀 默认：true 
        }))
        .pipe(cssmin())
        .pipe(gulp.dest('../static/themes/css/fupin/'))
        .pipe(livereload());
       
});
//监控ejs模板


gulp.task('watchView', function() {
    livereload.listen();
    //gulp.watch('../webinf/modules/fupin_h5/**/*.*',function(file){
    //    livereload.changed(file.path);
    //});
    //gulp.watch('../static/js/**/*.*',function(file){
    //    livereload.changed(file.path);
    //});
});
gulp.task('default',['testSass',
    'watchSass',
    'watchView'
]); //定义默认任务 elseTask为其他任务，该示例没有定义elseTask任务

// 压缩JS
gulp.task('js:min', function() {
	return gulp.src([
	    '../static/js/**/*.js',
		'!../static/**/*.min.js'
    ])
		.pipe(uglify())
		.pipe(rename({
			suffix: '.min'
		}))
		.pipe(gulp.dest('../static/js'));
});
 
//gulp.task(name[, deps], fn) 定义任务  name：任务名称 deps：依赖任务名称 fn：回调函数
//gulp.src(globs[, options]) 执行任务处理的文件  globs：处理的文件路径(字符串或者字符串数组) 
//gulp.dest(path[, options]) 处理完后文件生成路径