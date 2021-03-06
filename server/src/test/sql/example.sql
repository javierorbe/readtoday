INSERT INTO category (id, name)
VALUES ('2e0e7268-d947-4d04-8861-3c8da21c189f', 'Technology'),
       ('582af5f1-77ed-44ee-bae8-1e6d2380f7fe', 'News'),
       ('ff9ed2e2-2f50-4d47-95bf-155b18c1a1d9', 'Science'),
       ('4e76d6d5-377f-45e0-87c6-a812a562dc9f', 'Entertainment'),
       ('72acde6d-ca6e-4777-9439-77695118ffbf', 'Sports'),
       ('5488c1a3-359b-4566-b527-5d362500bf59', 'Politics');

INSERT INTO channel (id, title, rss_url, description, img_url)
VALUES ('ae0a350b-d7f7-4c91-b33a-846672ade8e5',
        'Hacker News', 'https://hnrss.org/frontpage', 'Hacker News RSS',
        'https://pbs.twimg.com/profile_images/469397708986269696/iUrYEOpJ_400x400.png'),
       ('6bbfca60-6ee6-4b1f-90dc-106c65a56d4d',
        'Techcrunch', 'https://feeds.feedburner.com/TechCrunch/', 'Techcrunch RSS',
        'https://techcrunch.com/wp-content/uploads/2015/02/cropped-cropped-favicon-gradient.png?w=180'),
       ('365ae3a9-63ff-40ad-bcca-2e63b0bb46e1',
        'NYT Arts', 'https://rss.nytimes.com/services/xml/rss/nyt/Arts.xml',
        'New York Times Arts RSS',
        'https://www.nytimes.com/vi-assets/static-assets/ios-ipad-144x144-28865b72953380a40aa43318108876cb.png'),
       ('1d66f399-f1ba-4f4f-8048-e5bb6495f1c9', 'NYT Sports',
        'https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml', 'New York Times Sports RSS',
        'https://www.nytimes.com/vi-assets/static-assets/ios-ipad-144x144-28865b72953380a40aa43318108876cb.png'),
       ('79f90b4e-fa02-483a-bb2b-fff84838f5b5', 'Space & Cosmos',
        'https://rss.nytimes.com/services/xml/rss/nyt/Space.xml', 'New York Times Science RSS',
        'https://www.nytimes.com/vi-assets/static-assets/ios-ipad-144x144-28865b72953380a40aa43318108876cb.png'),
       ('6262fa80-952e-4b9e-aba7-7efb3c54e404', 'NYT Technology',
        'https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml', 'New York Times Technology RSS',
        'https://www.nytimes.com/vi-assets/static-assets/ios-ipad-144x144-28865b72953380a40aa43318108876cb.png'),
       ('cae93fb8-b367-4807-9ecc-00b26f6fe87b', 'FOX Politics',
        'http://feeds.foxnews.com/foxnews/politics', 'FOX Politics RSS',
        'https://static.foxnews.com/static/orion/styles/img/fox-news/favicons/apple-touch-icon-180x180.png'),
       ('0cec82c5-394e-4cd9-a987-8b093b05064c', 'FOX SciTech',
        ' http://feeds.foxnews.com/foxnews/scitech', 'FOX SciTech RSS',
        'https://static.foxnews.com/static/orion/styles/img/fox-news/favicons/apple-touch-icon-180x180.png'),
       ('3a0b4158-c50a-4561-8239-0e9d876e994b', 'FOX Entertainment',
        'http://feeds.foxnews.com/foxnews/entertainment', 'FOX Entertainment RSS',
        'https://static.foxnews.com/static/orion/styles/img/fox-news/favicons/apple-touch-icon-180x180.png'),
       ('827e8737-a34d-458c-b28d-e581013bf962', 'BBC Politics',
        'http://feeds.bbci.co.uk/news/politics/rss.xml', 'BBC Politics RSS',
        'https://m.files.bbci.co.uk/modules/bbc-morph-news-waf-page-meta/5.1.0/apple-touch-icon.png'),
       ('c2087ed6-3c2d-4207-8bc1-2128cf59630b', 'BBC Health',
        'http://feeds.bbci.co.uk/news/health/rss.xml', 'BBC Health RSS',
        'https://m.files.bbci.co.uk/modules/bbc-morph-news-waf-page-meta/5.1.0/apple-touch-icon.png'),
       ('032edf28-d031-4c27-8a85-7b8efdf8af9d', 'DailyMail Sports',
        'https://www.dailymail.co.uk/sport/index.rss', 'The Daily Mail Sports RSS',
        'https://i.dailymail.co.uk/i/social/img_mol-logo_50x50.png'),
       ('42054e6e-6750-4cd3-8371-0eb81a5cee4d', 'DailyMail Travel',
        'https://www.dailymail.co.uk/travel/index.rss', 'The Daily Mail Travel RSS',
        'https://i.dailymail.co.uk/i/social/img_mol-logo_50x50.png');

INSERT INTO channel_categories (channel_id, category_id)
VALUES ('ae0a350b-d7f7-4c91-b33a-846672ade8e5', '2e0e7268-d947-4d04-8861-3c8da21c189f'),
       ('ae0a350b-d7f7-4c91-b33a-846672ade8e5', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('6bbfca60-6ee6-4b1f-90dc-106c65a56d4d', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('365ae3a9-63ff-40ad-bcca-2e63b0bb46e1', '4e76d6d5-377f-45e0-87c6-a812a562dc9f'),
       ('1d66f399-f1ba-4f4f-8048-e5bb6495f1c9', '72acde6d-ca6e-4777-9439-77695118ffbf'),
       ('1d66f399-f1ba-4f4f-8048-e5bb6495f1c9', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('79f90b4e-fa02-483a-bb2b-fff84838f5b5', 'ff9ed2e2-2f50-4d47-95bf-155b18c1a1d9'),
       ('6262fa80-952e-4b9e-aba7-7efb3c54e404', '2e0e7268-d947-4d04-8861-3c8da21c189f'),
       ('cae93fb8-b367-4807-9ecc-00b26f6fe87b', '5488c1a3-359b-4566-b527-5d362500bf59'),
       ('cae93fb8-b367-4807-9ecc-00b26f6fe87b', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('0cec82c5-394e-4cd9-a987-8b093b05064c', '2e0e7268-d947-4d04-8861-3c8da21c189f'),
       ('0cec82c5-394e-4cd9-a987-8b093b05064c', 'ff9ed2e2-2f50-4d47-95bf-155b18c1a1d9'),
       ('0cec82c5-394e-4cd9-a987-8b093b05064c', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('3a0b4158-c50a-4561-8239-0e9d876e994b', '4e76d6d5-377f-45e0-87c6-a812a562dc9f'),
       ('827e8737-a34d-458c-b28d-e581013bf962', '5488c1a3-359b-4566-b527-5d362500bf59'),
       ('827e8737-a34d-458c-b28d-e581013bf962', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('c2087ed6-3c2d-4207-8bc1-2128cf59630b', '4e76d6d5-377f-45e0-87c6-a812a562dc9f'),
       ('032edf28-d031-4c27-8a85-7b8efdf8af9d', '72acde6d-ca6e-4777-9439-77695118ffbf'),
       ('42054e6e-6750-4cd3-8371-0eb81a5cee4d', '4e76d6d5-377f-45e0-87c6-a812a562dc9f');
