if (!process.env.token){
  console.log('Error: specify token');
  process.exit(1);
}

var botkit = require('botkit');
var os = require('os');

var controller = botkit.slackbot({
  debug:false
});

var bot = controller.spawn({
    token: process.env.token
}).startRTM();

controller.hears(['(.*) theater(.*)', '(.*)movie(.*)'], 'ambient', function(bot, message){
  bot.reply(message, 'Adam Sandler shits in your eyes, ears, and throat! This summer...');
})

controller.hears(['shoo'], 'direct_message,direct_mention,mention', function(bot, message){
  bot.startConversation(message, function(err, convo){
    convo.ask('Are you sure you want me to shutdown?', [
        {
            pattern: bot.utterances.yes,
            callback: function(response, convo) {
                convo.say('Bye!');
                convo.next();
                setTimeout(function() {
                    process.exit();
                }, 3000);
            }
        },
        {
            pattern: bot.utterances.no,
            default: true,
            callback: function(response, convo) {
                convo.say('*Phew!*');
                convo.next();
            }
        }
    ]);
  })
})
