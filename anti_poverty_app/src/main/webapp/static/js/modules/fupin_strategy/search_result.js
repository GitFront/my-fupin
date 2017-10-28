$(function () {
  var
    CLICK_EV = 'click',

    ACTION_KEYS = {
      OVERVIEW_FAMILY: 'overview_family',
      HELPER: 'helper',
      PLAN: 'plan',
      FILE_COUNTRY: 'file_country'
    },

    extend = $.extend,
    ajax = function (opts) {
      $.ajax(extend({
        dataType: 'json'
      }, opts));
    },
    poorDetail = $.poorDetail,

    $searchResult = $('#searchResult');

  init();

  function init() {
    var params = getSearchObject(),
      id = params.id,
      type = params.type;

    $('.search-types').find('[value=' + type + ']').prop('checked', true);

    renderList(id, type);
    $searchResult
      .on(CLICK_EV, 'li', function () {
        var $elem = $(this),
          key = $elem.attr('data-key'),
          id = $elem.attr('data-id');

        switch (key) {
          case ACTION_KEYS.OVERVIEW_FAMILY:
            poorDetail.showFamily(id, poorDetail.TABS_FAMILY.FILE);
            break;
          case ACTION_KEYS.HELPER:
            poorDetail.showFamily(id, poorDetail.TABS_FAMILY.HELP_INFO);
            break;
          case ACTION_KEYS.PLAN:
            poorDetail.showFamily(id, poorDetail.TABS_FAMILY.HELP_INFO);
            break;
          case ACTION_KEYS.FILE_COUNTRY:
            poorDetail.showCountry(id);
            break;
        }
      });

    function renderList(id, type) {
      ajax({
        //TODO 由于是demo，先不分目录了
        url: ctx+'/data/search/search/' + id + '.json',
        success: function (data) {
          var code = data.code,
            d = data.data;
          if (code == 0 && d) {
            $('#searchField').val(d.search_word);
            $searchResult.html(template('tplSearchResult', {
              result: d.result,
              SEARCH_TYPES: SEARCH_TYPES,
              search_type: type
            }));
          }
        }
      });
    }
  }

  function getSearchObject() {
    var search = location.search.substring(1);
    return search ? JSON.parse('{"' + search.replace(/&/g, '","').replace(/=/g, '":"') + '"}',
      function (key, value) {
        return key === "" ? value : decodeURIComponent(value)
      }) : {};
  }
});