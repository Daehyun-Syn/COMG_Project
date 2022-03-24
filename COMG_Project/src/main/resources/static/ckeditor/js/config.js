CKEDITOR.editorConfig = function( config ) {
	config.toolbarGroups = [
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.removeButtons = 'Underline,Subscript,Superscript,Cut,Undo,Copy,Paste,Redo,PasteText,PasteFromWord,Scayt,Link,Unlink,Anchor,HorizontalRule,Table,Image,SpecialChar,base64image,Source,Maximize,Bold,Italic,Strike,RemoveFormat,NumberedList,BulletedList,Indent,Outdent,Blockquote,Styles,Format,About,Youtube';
	config.filebrowserUploadUrl      = '/upload?type=Files';
	config.filebrowserImageUploadUrl = '/upload?type=Images';
	config.filebrowserUploadMethod='form'; //파일 오류났을때 alert띄워줌
	config.readOnly = true;
};
