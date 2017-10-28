var gulp = require('gulp'),
	gulpSass = require('gulp-sass'),
	gulpPlumber = require('gulp-plumber'),
	gulpNotify = require('gulp-notify'),
	gulpLivereload = require('gulp-livereload'),
	gulpAutoprefixer = require('gulp-autoprefixer');

var TASK_CSS = 'css',
	TASK_DEV = 'dev';

function plumber() {
	return gulpPlumber({
		errorHandler: gulpNotify.onError('Error: <%= error.message %>')
	});
}

gulp.task(TASK_CSS, function () {
	gulp.src('../scss/*.scss')
		.pipe(plumber())
		.pipe(gulpSass())
		.pipe(gulpAutoprefixer({
			browsers: ['last 30 versions', 'Android >= 4.0', 'iOS >= 6.0'],
			cascade: true,
			remove: true
		}))
		.pipe(gulp.dest('../css/'))
		.pipe(gulpLivereload())
});

gulp.task(TASK_DEV, function(){
	gulpLivereload.listen();

	gulp.watch('../scss/**/*.scss', [TASK_CSS]);
});

gulp.task('default', [TASK_CSS, TASK_DEV]);