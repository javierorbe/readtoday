INSERT INTO category (id, name)
VALUES ('2e0e7268-d947-4d04-8861-3c8da21c189f', 'Technology'),
       ('582af5f1-77ed-44ee-bae8-1e6d2380f7fe', 'News'),
       ('ff9ed2e2-2f50-4d47-95bf-155b18c1a1d9', 'Science');

INSERT INTO channel (id, title, rss_url, description, img_url)
VALUES ('ae0a350b-d7f7-4c91-b33a-846672ade8e5',
        'Hacker News', 'https://hnrss.org/frontpage', 'Hacker News RSS',
        'https://pbs.twimg.com/profile_images/469397708986269696/iUrYEOpJ_400x400.png'),
       ('6bbfca60-6ee6-4b1f-90dc-106c65a56d4d',
        'Techcrunch', 'https://feeds.feedburner.com/TechCrunch/', 'Techcrunch RSS',
        'https://s.yimg.com/oa/build/images/favicons/techcrunch.png');

INSERT INTO channel_categories (channel_id, category_id)
VALUES ('ae0a350b-d7f7-4c91-b33a-846672ade8e5', '2e0e7268-d947-4d04-8861-3c8da21c189f'),
       ('ae0a350b-d7f7-4c91-b33a-846672ade8e5', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe'),
       ('6bbfca60-6ee6-4b1f-90dc-106c65a56d4d', '582af5f1-77ed-44ee-bae8-1e6d2380f7fe');
