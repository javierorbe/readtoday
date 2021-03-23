module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'body-leading-blank': [2, 'always'],
    'body-max-line-length': [2, 'always', 72],
    'header-max-length': [2, 'always', 50],
    'subject-case': [
      2,
      'never',
      ['lower-case', 'upper-case', 'snake-case'],
    ],
    'type-enum': [
      2,
      'always',
      [
        'feat',
        'fix',
        'docs',
        'style',
        'refactor',
        'test',
        'chore',
      ],
    ],
  },
};
