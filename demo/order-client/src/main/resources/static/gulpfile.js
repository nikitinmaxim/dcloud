var gulp = require('gulp');
var smartgrid = require('smart-grid');
var gcmq = require('gulp-group-css-media-queries');

var settings = {
    "filename": "_grid.style",
    "outputStyle": "grid",
    "columns": 12,
    "offset": "10px",
    "container": {
        "maxWidth": "1200px",
        "fields": "10px"
    },
    "breakPoints": {
        "lg": {
            "width": "1200px",
            "fields": "10px"
        },
        "md": {
            "width": "992px",
            "fields": "10px"
        },
        "sm": {
            "width": "768px",
            "fields": "10px"
        },
        "xs": {
            "width": "576px",
            "fields": "10px"
        }
    },
    "mixinNames": {
        "container": "container",
        "row": "row-flex",
        "rowFloat": "row-float",
        "column": "col",
        "columnFloat": "col-float",
        "columnPadding": "col-padding",
        "offset": "offset"
    },
    "properties": [
        "justify-content",
        "align-items",
        "align-content",
        "align-self",
        "order",
        "flex",
        "flex-grow",
        "flex-shrink",
        "flex-basis",
        "flex-direction",
        "flex-wrap",
        "flex-flow",
        "float"
    ],
    "tab": "    "
};

gulp.task('smartgrid', function() {
    smartgrid('src/grid', settings);
});

gulp.task('gcmq', function() {
    gulp.src('dist/css/**/*.css')
        .pipe(gcmq())
        .pipe(gulp.dest('dist/css'));
});