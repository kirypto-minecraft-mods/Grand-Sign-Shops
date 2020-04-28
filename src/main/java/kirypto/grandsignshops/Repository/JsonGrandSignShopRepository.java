// package kirypto.grandsignshops.Repository;
//
// import org.apache.commons.lang3.NotImplementedException;
//
// import java.io.File;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Optional;
//
// import kirypto.grandsignshops.domain.BlockLocation;
// import kirypto.grandsignshops.domain.GrandSignShop;
//
// public class JsonGrandSignShopRepository implements GrandSignShopRepository {
//     private final File grandSignShopsRootFolder;
//     private final Map<BlockLocation, GrandSignShop> signLocationToGrandSignShopLocation;
//     private final Map<BlockLocation, BlockLocation> allGrandSignShopLocationsToCorrespondingSignLocation;
//
//     public JsonGrandSignShopRepository(File grandSignShopsRootFolder) {
//         this.grandSignShopsRootFolder = grandSignShopsRootFolder;
//
//         signLocationToGrandSignShopLocation = new HashMap<>();
//         allGrandSignShopLocationsToCorrespondingSignLocation = new HashMap<>();
//     }
//
//     @Override
//     public void create(GrandSignShop grandSignShop) {
//         BlockLocation signLocation = grandSignShop.getSignLocation();
//         signLocationToGrandSignShopLocation.put(signLocation, grandSignShop);
//         allGrandSignShopLocationsToCorrespondingSignLocation.put(grandSignShop.getChestLocation(), signLocation);
//         for (BlockLocation additionalProtectedLocation : grandSignShop.getAdditionalProtectedLocations()) {
//             allGrandSignShopLocationsToCorrespondingSignLocation.put(additionalProtectedLocation, signLocation);
//         }
//     }
//
//     @Override
//     public Optional<GrandSignShop> retrieve(BlockLocation blockLocation) {
//         if (signLocationToGrandSignShopLocation.containsKey(blockLocation)) {
//             return Optional.of(signLocationToGrandSignShopLocation.get(blockLocation));
//         }
//         if (allGrandSignShopLocationsToCorrespondingSignLocation.containsKey(blockLocation)) {
//             BlockLocation signLocation = allGrandSignShopLocationsToCorrespondingSignLocation.get(blockLocation);
//             return Optional.of(signLocationToGrandSignShopLocation.get(signLocation));
//         }
//         return Optional.empty();
//     }
//
//     @Override
//     public void delete(BlockLocation blockLocation) {
//         throw new NotImplementedException("");
//     }
// }
