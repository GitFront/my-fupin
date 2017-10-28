<script type="text/x-template" id="tplAppHeader">
	<div class="c-app-header">
		<div class="banner">
			<div class="btn-back" @click="goBack()" v-if="showBack">
				<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAkCAYAAACJ8xqgAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpkZTE3ZmU2MS1iNmFiLWM5NDAtYTc2OC0wYmIwZTcxYTk3MGUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6Rjk2M0U4ODFDNUM0MTFFNjkzQjRCNkEzM0VCRjZFNDEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6Rjk2M0U4ODBDNUM0MTFFNjkzQjRCNkEzM0VCRjZFNDEiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDozMzBlMzZiZi1lNjFkLTRmNTctYjliYS1jZmU3MzVmYzhhN2QiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6ZGUxN2ZlNjEtYjZhYi1jOTQwLWE3NjgtMGJiMGU3MWE5NzBlIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+AOJlfgAAANVJREFUeNq01s0NwjAMBeCksEEnoXduzMEGiEVgBgbpDjWLsAEoOCgRCJXUP49IT2oun5Qmsh1TSsG5es6pfB/XAGzkbMr+2gGxB2cK+ciG9Jwpvdeds3/9PiRmAZuYFlzENKAIk4JiTAKqsCVQjbVAE/YLNGNzoAv7Bt3YJwjBKgjDKnhBYTldQK9/HLmihLoUKDr3sAn1sCFoqzgQqji4UEmBJVSBNaGaJkWoJqVCLY2eUI1ehHqGJUINSy1066mHN84uD5llv+IMETQSnzmRc3gKMAAuh46syR7gtQAAAABJRU5ErkJggg==">
			</div>
			<h1>{{title}}</h1>
		</div>
		<div class="spacer"></div>
	</div>
</script>
<script type="text/x-template" id="tplAppDialog">
	<div class="c-app-dialog" style="display:none;" v-show="isShowDialog">
		<div class="dialog-overlay" @click="closeDialog"></div>
		<div class="dialog-content-box">
			<div class="dialog-content dialog-alert" v-html="dialogContent"></div>
		</div>
	</div>
</script>