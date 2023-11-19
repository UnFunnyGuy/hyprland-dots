/* To avoid CSS expressions while still supporting IE 7 and IE 6, use this script */
/* The script tag referencing this file must be placed before the ending body tag. */

/* Use conditional comments in order to target IE 7 and older:
	<!--[if lt IE 8]><!-->
	<script src="ie7/ie7.js"></script>
	<!--<![endif]-->
*/

(function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'Madness\'">' + entity + '</span>' + html;
	}
	var icons = {
		'mad-clock1': '&#xea976;',
		'mad-spotify': '&#xea975;',
		'mad-linux': '&#xea974;',
		'mad-globe': '&#xea973;',
		'mad-gitlab': '&#xea972;',
		'mad-github2': '&#xea969;',
		'mad-dev2': '&#xea971;',
		'mad-android': '&#xea970;',
		'mad-github': '&#xea968;',
		'mad-electricity': '&#xea967;',
		'mad-box-time': '&#xea966;',
		'mad-box-tick': '&#xea965;',
		'mad-moon': '&#xea964;',
		'mad-wifi': '&#xea900;',
		'mad-speaker-3': '&#xea901;',
		'mad-smart-home': '&#xea902;',
		'mad-music-square': '&#xea903;',
		'mad-message-programming': '&#xea904;',
		'mad-home-wifi': '&#xea905;',
		'mad-home': '&#xea906;',
		'mad-folder-cloud': '&#xea907;',
		'mad-folder-2': '&#xea908;',
		'mad-emoji-happy': '&#xea909;',
		'mad-electricity1': '&#xea90a;',
		'mad-document-code-2': '&#xea90b;',
		'mad-danger': '&#xea90c;',
		'mad-code': '&#xea90d;',
		'mad-cloud-add': '&#xea90e;',
		'mad-audio-square': '&#xea90f;',
		'mad-arrange-square-2': '&#xea910;',
		'mad-volume-slash': '&#xea911;',
		'mad-volume-high': '&#xea912;',
		'mad-flash-slash': '&#xea913;',
		'mad-cpu': '&#xea914;',
		'mad-chrome': '&#xea915;',
		'mad-Arrow---Right-2': '&#xea916;',
		'mad-Arrow---Left-2': '&#xea917;',
		'mad-toggle-on-circle': '&#xea918;',
		'mad-toggle-on': '&#xea919;',
		'mad-toggle-off-circle': '&#xea91a;',
		'mad-toggle-off': '&#xea91b;',
		'mad-shield-tick': '&#xea91c;',
		'mad-setting-3': '&#xea91d;',
		'mad-search-normal': '&#xea91e;',
		'mad-ram-2': '&#xea91f;',
		'mad-main-component': '&#xea920;',
		'mad-hashtag-up': '&#xea921;',
		'mad-driver': '&#xea922;',
		'mad-category': '&#xea923;',
		'mad-bucket-square': '&#xea924;',
		'mad-airpods': '&#xea925;',
		'mad-volume-low': '&#xea926;',
		'mad-video': '&#xea927;',
		'mad-timer': '&#xea928;',
		'mad-task-square': '&#xea929;',
		'mad-sun': '&#xea92a;',
		'mad-stop-circle': '&#xea92b;',
		'mad-speaker': '&#xea92c;',
		'mad-sms-star': '&#xea92d;',
		'mad-sms-notification': '&#xea92e;',
		'mad-sms': '&#xea92f;',
		'mad-record-circle': '&#xea930;',
		'mad-receive-square': '&#xea931;',
		'mad-ram': '&#xea932;',
		'mad-profile-tick': '&#xea933;',
		'mad-printer': '&#xea934;',
		'mad-previous': '&#xea935;',
		'mad-play-circle': '&#xea936;',
		'mad-pause-circle': '&#xea937;',
		'mad-notification': '&#xea938;',
		'mad-note-square1': '&#xea939;',
		'mad-note-favorite': '&#xea93a;',
		'mad-next': '&#xea93b;',
		'mad-mouse': '&#xea93c;',
		'mad-moon1': '&#xea93d;',
		'mad-monitor': '&#xea93e;',
		'mad-microphone-2': '&#xea93f;',
		'mad-microphone': '&#xea940;',
		'mad-location': '&#xea941;',
		'mad-key': '&#xea942;',
		'mad-instagram': '&#xea943;',
		'mad-home-2': '&#xea944;',
		'mad-hierarchy-square': '&#xea945;',
		'mad-headphone': '&#xea946;',
		'mad-forward': '&#xea947;',
		'mad-flash': '&#xea948;',
		'mad-driver-refresh': '&#xea949;',
		'mad-cpu-setting': '&#xea94a;',
		'mad-cpu-charge': '&#xea94b;',
		'mad-code1': '&#xea94c;',
		'mad-cloud-sunny': '&#xea94d;',
		'mad-cloud-notif': '&#xea94e;',
		'mad-cloud': '&#xea94f;',
		'mad-clock': '&#xea950;',
		'mad-chart-2': '&#xea951;',
		'mad-category1': '&#xea952;',
		'mad-calendar-2': '&#xea953;',
		'mad-box': '&#xea954;',
		'mad-bluetooth-2': '&#xea955;',
		'mad-battery': '&#xea956;',
		'mad-battery-charging': '&#xea957;',
		'mad-backward': '&#xea958;',
		'mad-arrow-left': '&#xea959;',
		'mad-activity': '&#xea95a;',
		'mad-tick-circle': '&#xea95b;',
		'mad-arrow-right': '&#xea95c;',
		'mad-battery-10': '&#xea95d;',
		'mad-battery-50': '&#xea95e;',
		'mad-battery-full': '&#xea95f;',
		'mad-battery-disable': '&#xea960;',
		'mad-grid-2': '&#xea961;',
		'mad-scroll': '&#xea962;',
		'mad-send': '&#xea963;',
		'0': 0
		},
		els = document.getElementsByTagName('*'),
		i, c, el;
	for (i = 0; ; i += 1) {
		el = els[i];
		if(!el) {
			break;
		}
		c = el.className;
		c = c.match(/mad-[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
}());
