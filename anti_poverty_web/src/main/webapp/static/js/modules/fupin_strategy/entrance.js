$(function () {
	var QR_SIZE = 93,

		$qrAndroid = $('#qrAndroidApp'),
		androidURL = $qrAndroid.attr('data-src');

	var qrcodeAndroid = new QRCode('qrAndroidApp', {
		text: androidURL,
		width: QR_SIZE,
		height: QR_SIZE,
		colorDark : "#000000",
		colorLight : "#ffffff",
		correctLevel : QRCode.CorrectLevel.H
	});
});