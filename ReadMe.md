# Grand Sign Shops

### TODOs

#### For M.V.P. _(minimum viable product)_

- Add store interaction functionality
  - If interacting, ensure only correct item type is in the container
  - If buying, ensure shop has item and player has necessary amount
  - If selling, ensure shop has space and shop owner has amount
- Actually save shops to disk (currently just in memory, so lost on restart)
- Need to add ability for players to remove shops
- Need to add ability for admin players to override and remove shops
- Ensure metadata is accounted for correctly
- Add functionality to specify amount of items exchanged per trade

#### Stretch Goals

- Ability to get price stats on existing shops by item (min, max, mean, mode, std)

### Known Bugs

- Creating a shop updates the text only on the server-side, players need to
   re-log to see it.