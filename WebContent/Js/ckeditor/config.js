/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function(config) {

    config.skin = 'v2';
    //config.uiColor = '#F7F8F9'
    config.uiColor = '#000';
    config.enterMode = CKEDITOR.ENTER_BR;
    config.shiftEnterMode = CKEDITOR.ENTER_P;
    // Define changes to default configuration here. For example:
    config.language = 'zh-cn';
    // config.uiColor = '#AADC6E';
    config.toolbar = 'MyToolbar';
    config.toolbarStartupExpanded = true;
    config.height = 120;
    config.dialog_backgroundCoverOpacity = 0.5;
    config.toolbar_Full =
[
    ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Print', 'SpellChecker', 'Scayt'],
    ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'],
    '/',
    ['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript'],
    ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote'],
    ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
    ['Link', 'Unlink', 'Anchor'],
    ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak'],
    '/',
    ['Styles', 'Format', 'Font', 'FontSize'],
['TextColor', 'BGColor']
];

    config.toolbar_MyToolbar =
    [
        ['Source','Cut', 'Copy', 'Paste','Bold', 'Italic', 'Strike','Font', 'FontSize','TextColor']
    ];
    config.resize_maxHeight = 350;
    config.resize_maxWidth = 750;
};
