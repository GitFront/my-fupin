/**
 * Created by fengxiqiu on 2016/9/25.
 */
$(function(){
   /*对外方法 */
    $.poorDetail={
        //点击地图村档案详情弹窗
        showCountry:function(country_name){
            $('.pop-win-wrap').show();
            $('.file-detail').hide();
            $('#poorCountryDetail').show();
            renderPoorCountry(country_name);
        },
        showFamily:function(family_id){
            $('.pop-win-wrap').show();
            $('.file-detail').hide();
            $('#poorFamilyDetail').show();
            renderPoorFamily(family_id);
        }
    };
    var countryAlbumData=null;//记录村相册数据
    var familyAlbumData=null;//记录户相册数据
    $(document)
        //弹窗隐藏
        .on('click', '.js-pop-close', function(){
            $('.pop-win-wrap').hide();
        })
        //村，户切换
        .on('click', '.js-pop-tab', function(){
            var _this=$(this);
            var _dataTab=_this.attr('data-tab');
            //切换tab
            _this.addClass('active')
                .siblings().removeClass('active');
            //切换内容块
            $('.'+_dataTab).show()
                .siblings('div').hide();
        })
        //村相册
        .on('click', '.js-country-album-tab', function(){
            var _this=$(this);
            var _type=_this.attr('data-img');
            _this.addClass('active')
                .siblings().removeClass('active');
            renderAlbum(countryAlbumData[_type], $('#poorCountryDetail'));
        })
        //户相册
        .on('click', '.js-family-album-tab', function(){
            var _this=$(this);
            var _type=_this.attr('data-img');
            _this.addClass('active')
                .siblings().removeClass('active');
            renderAlbum(familyAlbumData[_type], $('#poorFamilyDetail'));
        })
        //通过家庭档案查看图片
        .on('click', '.js-check-family-pic', function(){
            var _this=$(this);
            var _type=_this.attr('data-img');
            $('.js-pop-tab[data-tab=js-family-album-box]').trigger('click');
            $('.js-family-album-tab[data-img='+_type+']').trigger('click');
        });
    /**
     * 渲染某条村的
     * @param country_name 拼音全拼
     */
    function renderPoorCountry(country_name){
        var _url='../data/country/'+country_name+'.json';
        $.ajax({
            url:_url,
            dataType:'json',
            success:function(data){
                var code = data.code,
                    d = data.data;
                if(code == 0 && d){
                    var $poorCountryDetail=$('#poorCountryDetail');
                    //档案
                    var _poorFileInfoHtml=template('poorFileInfoTpl', d.responsible_person);
                    $poorCountryDetail.find('.poor-file-info').html(_poorFileInfoHtml);
                    //短板分析
                    var _countryAnalyzeHtml=template('countryAnalyzeTpl', d.shortage);
                    $poorCountryDetail.find('.js-country-analyze').html(_countryAnalyzeHtml);
                    //帮扶信息
                    var _countryHelpInfoHtml=template('countryHelpInfoTpl', d.info);
                    $poorCountryDetail.find('.js-country-help-info').html(_countryHelpInfoHtml);
                    //默认学校图片
                    countryAlbumData=d.album;
                    renderAlbum(countryAlbumData.school, $poorCountryDetail);
                }
            }
        })
    }

    /**
     * 渲染村相册
     */
    function renderAlbum(albums, $dom){
        var _imgsHtml=template('albumTpl', {"albums":albums});
        var $albumList=$dom.find('.album-list');
        var $allAlbum=$albumList.parent('.all-album');
        var _albumLen=albums.length;
        if(_albumLen>2){
            $allAlbum.css({
                'overflow-x':'scroll'
            });
            $albumList.css('width',400*_albumLen+'px')
        } else {
            $albumList.css('width','auto');
            $allAlbum.css({
                'overflow':'hidden'
            });
        }
        $albumList.html(_imgsHtml);
    }
    /**
     * 渲染某户的
     * @param family_id 拼音全拼
     */
    function renderPoorFamily(family_id){
        var _url='../data/family/'+family_id+'.json';
        $.ajax({
            url:_url,
            dataType:'json',
            success:function(data){
                var code = data.code,
                    d = data.data;
                if(code == 0 && d){
                    var $poorFamilyDetail=$('#poorFamilyDetail');
                    //贫困户档案
                    var _poorFileFamilyInfoHtml=template('poorFileFamilyInfoTpl', d.summary);
                    $poorFamilyDetail.find('.poor-file-info').html(_poorFileFamilyInfoHtml);
                    //家庭档案
                    var _familyFileHtml=template('familyFileTpl', d.family_info);
                    $poorFamilyDetail.find('.js-family-file').html(_familyFileHtml);
                    //帮扶信息
                    var _familyHelpInfoHtml=template('familyHelpInfoTpl', d.help_info);
                    $poorFamilyDetail.find('.js-family-help-info').html(_familyHelpInfoHtml);
                    //默认家庭图片
                    familyAlbumData=d.album;
                    renderAlbum(familyAlbumData.family, $poorFamilyDetail);
                }
            }
        })
    }
});