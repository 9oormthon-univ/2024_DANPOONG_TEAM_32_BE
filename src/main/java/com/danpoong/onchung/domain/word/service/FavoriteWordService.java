package com.danpoong.onchung.domain.word.service;

//@Service
//@RequiredArgsConstructor
//public class FavoriteWordService {
//    private final FavoriteWordRepository favoriteWordRepository;
//
//    public void addFavoriteWord(Long userId, Long wordId) {
//        FavoriteWord favoriteWord = new FavoriteWord(userId, wordId);
//        favoriteWordRepository.save(favoriteWord);
//    }
//
//    public List<FavoriteWord> getFavoriteWords(Long userId) {
//        return favoriteWordRepository.findByUserId(userId);
//    }
//
//    @Transactional
//    public void removeFavoriteWord(Long userId, Long wordId) {
//        favoriteWordRepository.deleteByUserIdAndWordId(userId, wordId);
//    }
//}
