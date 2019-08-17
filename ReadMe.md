# Grand Sign Shops

### Remaining Goals

- Add store interaction functionality
  - If interacting, ensure only correct item type is in the container
  - Calculate price based off of capacity
  - If buying, ensure shop has item and player has necessary amount
  - If selling, ensure shop has space and shop owner has amount
- Actually save shops to disk (currently just in memory, so lost on restart)
- Need to add ability for players to remove shops
- Need to add ability for admin players to override and remove shops

### Known Bugs

- Creating a shop updates the text only on the server-side, players need to
   re-log to see it.